package seng302.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

public class ConnectionManager extends Thread {


    private final List<Socket> connections;
    private final ServerSocket listener;

    public ConnectionManager(List<Socket> connections, ServerSocket listener) {
        this.connections = connections;
        this.listener = listener;
        this.start();
    }

    public void run() {
        System.out.println("running!");
        while (true){
            try {
                connections.add(this.listener.accept());
                System.out.println(connections.size());

            } catch (IOException e) {
                System.out.println("hey bro");
            }
        }


    }

}
