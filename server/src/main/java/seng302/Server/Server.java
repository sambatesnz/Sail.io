package seng302.Server;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;
import seng302.PacketGeneration.RaceStatus;
import seng302.RaceHandler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Server for the applicaiton
 */
public class Server {
    private ConnectionStore connectionStore;
    private IServerData mockRace;
    private ConnectionListener connectionListener;
    private Queue<byte[]> receivedPackets;
    private RaceHandler raceHandler;
    private int port;

    public Server(int port) throws Exception {
        this.mockRace = new RaceManager();
        this.port = port;
        startup();
    }

    public Server(int port, IServerData race) throws Exception {
        this.mockRace = race;
        this.port = port;
        startup();
    }


    /**
     * Resets the race when the server RaceStatus becomes FINISHED.
     * @throws Exception
     */
    private void resetRace() throws Exception {
        System.out.println("GIVING USERS 10s TO LOOK AT RESULTS.");
        Thread.sleep(10000);            // Once the race finishes, pause.
        this.mockRace = new RaceManager();
        System.out.println("Resetting the server's race.");
        setServerComponents();
        startEventLoop();
    }


    /**
     * Starts the server on a specified port
     * @throws Exception
     */
    private void startup() throws Exception {
        setServerComponents();
        startEventLoop();
    }

    /**
     * Initialises all server components for a fresh instance of the race.
     * @throws IOException
     */
    private void setServerComponents() throws IOException {
        connectionStore = new ConnectionStore(mockRace);
        receivedPackets = new LinkedBlockingQueue<>();
        connectionListener = new ConnectionListener(connectionStore, port, this);
        raceHandler = new RaceHandler(this.mockRace, connectionListener);
    }

    /**
     * Starts the busy wait loop of the server
     * @throws Exception
     */
    private void startEventLoop() throws Exception {
        boolean hasStarted = false;
        int numConnections = 0;

//        //--- THIS IS FOR TESTING WHETHER OR NOT THE OTHER CODE WORKS - REMOVE BEFORE MERGE
//
//        Thread serverThread = new Thread(() -> {
//            mockRace.getRace().setRaceStatus(RaceStatus.FINISHED);
//        });
//        serverThread.sleep(40000);
//        serverThread.start();
//
//        //---

        while (!mockRace.finished()) {
            if (numConnections < connectionStore.connectionAmount()){
                System.out.println("new connection detected, resending the XML Packets.");
                this.mockRace.addXMLPackets();
                numConnections = connectionStore.connectionAmount();
            }
            if (connectionStore.connectionAmount() > 0 && !hasStarted) {
                System.out.println("Race will begin generating data now.");
                this.mockRace.beginGeneratingData();
                hasStarted = true;
            }
            if (connectionStore.connectionAmount() > 0 && hasStarted) {
                sendToAll();
            }
            handleReceivedMessages();
            sendSingleMessages();
            Thread.sleep(1);
        }
        connectionStore.purgeConnections();
        resetRace();
    }

    private void handleReceivedMessages() {
        if (!receivedPackets.isEmpty()) {
            byte[] packets = receivedPackets.remove();
            raceHandler.updateRace(packets);
        }
    }

    private void sendSingleMessages() throws IOException {
        if (mockRace.singleMessageReady()){
            connectionStore.sendToOne(mockRace.getDataForOne());
        }
    }

    private void sendToAll() throws IOException {
        byte[] bytes = this.mockRace.getDataForAll();
        connectionStore.sendToAll(bytes);
    }

    public void addPacketToQueue(byte[] data) {
        receivedPackets.add(data);
    }

    public void removeConnection(Socket s) {
        connectionStore.removeConnection(s);
    }

    public int connectionSize() {
        return connectionStore.connectionAmount();
    }
}