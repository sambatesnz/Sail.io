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
    private ConnectionIdentifier connectionIdentifier;
    private final ServerSocket listener;
    private Server2 server;

    public ConnectionManager(ConnectionIdentifier connectionIdentifier, int port, Server2 server) throws IOException {
        this.connectionIdentifier = connectionIdentifier;
        this.listener = new ServerSocket(port);
        this.server = server;
        this.start();
    }

    public void run() {
        System.out.println("Listening on " + listener);
        while (true) {
            Socket socket = null;
            try {
                socket = listener.accept();
                System.out.println("Connection from " + socket);
                int socketID = connectionIdentifier.addSocket(socket);
                new ClientConnexion(server, socket, socketID);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
