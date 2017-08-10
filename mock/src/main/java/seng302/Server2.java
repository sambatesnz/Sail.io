package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.MockRace;
import seng302.Server.ConnectionManager;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Server2 {
    private ConnectionIdentifier connectionIdentifier;
    private IServerData mockRace;
    private ConnectionManager connectionManager;
    private Queue<byte[]> receivedPackets;
    private RaceHandler raceHandler;

    public Server2(int port) throws Exception {
        this.mockRace = new MockRace();
        connectionIdentifier = new ConnectionIdentifier();
        receivedPackets = new LinkedBlockingQueue<>();
        this.connectionManager = new ConnectionManager(connectionIdentifier, port, this);
        raceHandler = new RaceHandler(this.mockRace);
        startEventLoop();
    }

    private void startEventLoop() throws Exception {
        boolean hasStarted = false;

        int numConnections = 0;

        while (true) {

            if (numConnections < connectionIdentifier.connectionAmount()){
                System.out.println("new connection detected, resending the XML Packets.");
                this.mockRace.addXMLPackets();
                numConnections = connectionIdentifier.connectionAmount();
            }
            if (connectionIdentifier.connectionAmount() > 0 && !hasStarted) {
                System.out.println("Race will begin generating data now.");
                this.mockRace.beginGeneratingData();
                hasStarted = true;
            }
            if (connectionIdentifier.connectionAmount() > 0 && hasStarted) {
                sendToAll();
            }
            updateMock();

            if (connectionIdentifier.hasConnections()){
                connectionIdentifier.getSocket(connectionIdentifier.getConnections().get(0));
            }
        }
    }

    private void updateMock() {
        if (!receivedPackets.isEmpty()) {
            System.out.println("Packet Received - now need to parse it!");
            byte[] packets = receivedPackets.remove();
            raceHandler.updateRace(packets);
            //PacketParserUtils.getMessageType(packets)

            // TODO: Gotta Parse/Pass this information to some kind of parser, which then communicates with the MockRace.

        }
    }


    private void sendToAll() throws IOException {
        byte[] bytes = this.mockRace.getDataForAll();
        connectionIdentifier.sendToAll(bytes);
    }



    static public void main(String args[]) throws Exception {
        int port = Integer.parseInt("4941");
        new Server2(port);
    }

    public void addPacketToQueue(byte[] data) {
        receivedPackets.add(data);
    }

    public void removeConnection(Socket s) {
        connectionIdentifier.removeConnection(s);
    }
}