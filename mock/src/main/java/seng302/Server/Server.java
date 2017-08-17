package seng302.Server;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;
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

    public Server(int port) throws Exception {
        this.mockRace = new RaceManager();
        startup(port);
    }

    public Server(int port, IServerData race) throws Exception {
        this.mockRace = race;
        startup(port);
    }


    /**
     * Starts the server on a specified port
     * @param port port
     * @throws Exception
     */
    private void startup(int port) throws Exception {
        connectionStore = new ConnectionStore(mockRace);
        receivedPackets = new LinkedBlockingQueue<>();
        connectionListener = new ConnectionListener(connectionStore, port, this);
        raceHandler = new RaceHandler(this.mockRace, connectionListener);
        startEventLoop();
    }

    /**
     * Starts the busy wait loop of the server
     * @throws Exception
     */
    private void startEventLoop() throws Exception {
        boolean hasStarted = false;

        int numConnections = 0;

        while (true) {
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