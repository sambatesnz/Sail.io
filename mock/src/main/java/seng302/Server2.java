package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;
import seng302.Server.ConnectionListener;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Server2 {
    private ConnectionStore connectionStore;
    private IServerData mockRace;
    private ConnectionListener connectionListener;
    private Queue<byte[]> receivedPackets;
    private RaceHandler raceHandler;

    public Server2(int port) throws Exception {
        this.mockRace = new RaceManager();
        startup(port);
    }

    public Server2(int port, IServerData race) throws Exception {
        this.mockRace = race;
        startup(port);
    }

    private void startup(int port) throws Exception {
        connectionStore = new ConnectionStore();
        receivedPackets = new LinkedBlockingQueue<>();
        this.connectionListener = new ConnectionListener(connectionStore, port, this);
        raceHandler = new RaceHandler(this.mockRace);
        startEventLoop();
    }

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

            if (connectionStore.hasConnections()){
                connectionStore.getSocket(connectionStore.getConnections().get(0));
            }
        }
    }

    private void handleReceivedMessages() {
        if (!receivedPackets.isEmpty()) {
            System.out.println("Packet Received - now need to parse it!");
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
}