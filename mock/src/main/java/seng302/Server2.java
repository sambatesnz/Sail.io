package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.MockRace;
import seng302.Server.ConnectionManager;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server2 {
    private ServerSocket ss;
    private Hashtable outputStreams = new Hashtable();
    private IServerData mockRace;
    private ConnectionManager connectionManager;

    public Server2(int port) throws IOException {
        this.mockRace = new MockRace();
//        this.connectionManager = new ConnectionManager();  //TODO
        this.mockRace.beginGeneratingData();    //move this nephew
        listen(port);
    }

    private void listen(int port) throws IOException {
        ss = new ServerSocket(port);
        System.out.println("Listening on " + ss);
        while (true) {
            Socket s = ss.accept();
            System.out.println("Connection from " + s);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            outputStreams.put(s, dout);
            new ClientConnexion(this, s);
            this.mockRace.addXMLPackets();
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
                    dout.write(bytes);
                    if (hasPackets) {

                        System.out.println(Arrays.toString(bytes));
                    }

//                    dout.writeUTF( message );
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