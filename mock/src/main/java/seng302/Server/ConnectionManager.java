package seng302.Server;

import seng302.ClientConnexion;
import seng302.ConnectionIdentifier;
import seng302.Server2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

public class ConnectionManager extends Thread {
    private Hashtable outputStreams;
    private ConnectionIdentifier connectionIdentifier;
    private final ServerSocket listener;
    private Server2 server;

    public ConnectionManager(Hashtable outputStreams, ConnectionIdentifier connectionIdentifier, int port, Server2 server) throws IOException {
        this.connectionIdentifier = connectionIdentifier;
        this.outputStreams = outputStreams;
        this.listener = new ServerSocket(port);
        this.server = server;
        this.start();
    }

    public void run() {
        System.out.println("Listening on " + listener);
        while (true) {
            Socket s = null;
            try {
                s = listener.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection from " + s);
            DataOutputStream dout = null;
            try {
                dout = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStreams.put(s, dout);
            connectionIdentifier.addSocket(s);
            new ClientConnexion(server, s);
        }
    }

}
