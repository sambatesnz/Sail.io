package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.MockRace;
import seng302.Server.ConnectionManager;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server2 {
    private Hashtable outputStreams;
    private IServerData mockRace;
    private ConnectionManager connectionManager;

    public Server2(int port) throws IOException {
        this.mockRace = new MockRace();
        outputStreams = new Hashtable();
        this.connectionManager = new ConnectionManager(outputStreams, port, this);
        startEventLoop();
        //this.mockRace.beginGeneratingData();    //move this nephew
    }

    private void startEventLoop() {
        boolean hasStarted = false;

        int numConnections = 0;

        while (true) {

            if (numConnections < outputStreams.size()){
                System.out.println("new connection detected, resending the XML Packets.");
                this.mockRace.addXMLPackets();
                numConnections = outputStreams.size();
            }
            if (outputStreams.size() > 0 && !hasStarted) {
                System.out.println("Race will begin generating data now.");
                this.mockRace.beginGeneratingData();
                hasStarted = true;
            }
            if (outputStreams.size() > 0 && hasStarted) {
                sendToAll();
            }
        }
    }

    Enumeration getOutputStreams() {
        return outputStreams.elements();
    }

    void sendToAll() {
        synchronized (outputStreams) {
            byte[] bytes = this.mockRace.getData();
            for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) {

                DataOutputStream dout = (DataOutputStream) e.nextElement();
                try {
                    boolean hasPackets = bytes.length > 0;
                    if (hasPackets) {
                        dout.write(bytes);
                        System.out.println(Arrays.toString(bytes));
                    }
                } catch (IOException ie) {
                    System.out.println(ie);
                }
            }
        }
    }

    void removeConnection(Socket s) {
        synchronized (outputStreams) {
            System.out.println("Removing connection to " + s);
            outputStreams.remove(s);
            try {
                s.close();
            } catch (IOException ie) {
                System.out.println("Error closing " + s);
                ie.printStackTrace();
            }
        }
    }

    static public void main(String args[]) throws Exception {
        int port = Integer.parseInt("4941");
        new Server2(port);
    }

    public void handlePacket(byte[] data) {
        //TODO: parse packet, and if necessary change the race.
    }
}