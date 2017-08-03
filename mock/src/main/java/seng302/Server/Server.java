package seng302.Server;

import seng302.DataGeneration.IServerData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Server to n client implementation from this website:
 * http://cs.lmu.edu/~ray/notes/javanetexamples/
 */
public class Server {

    public Server(int port, IServerData mockData) throws IOException {
        ServerSocket listener = new ServerSocket(port);

        List<Socket> connections = Collections.synchronizedList(new ArrayList<Socket>());

        new ConnectionManager(connections, listener);


        while (true) {
            if (connections.size() > 0){
//                System.out.println("Kevin!");
                System.out.println(connections.size());
            }

        }
    }
}
