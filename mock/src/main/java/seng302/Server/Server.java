package seng302.Server;

import seng302.DataGeneration.IServerData;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server to n client implementation from this website:
 * http://cs.lmu.edu/~ray/notes/javanetexamples/
 */
public class Server {

    public Server(int port, IServerData mockData) throws IOException {
        ServerSocket listener = new ServerSocket(port);
        try {
            while(true) {
                new ClientConnector(listener.accept(), mockData).start();
            }
        } finally {
            listener.close();
        }
    }
}
