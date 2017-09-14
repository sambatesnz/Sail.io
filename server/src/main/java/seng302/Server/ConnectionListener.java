package seng302.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listens for attempted connections to the server
 */
public class ConnectionListener extends Thread {
    private ConnectionStore connectionStore;
    private final ServerSocket listener;
    private Server server;
    private boolean listening = true;

    public ConnectionListener(ConnectionStore connectionStore, int port, Server server) throws IOException {
        this.connectionStore = connectionStore;
        this.listener = new ServerSocket(port);
        System.out.println("starting server ...");
        this.server = server;
        this.start();
    }


    public void run() {
        System.out.println("Listening on " + listener);
        while (!listener.isClosed()) {
            Socket socket = null;
            try {
                socket = listener.accept();
                if (!listening) {
                    socket.close();
                    continue;
                }
                System.out.println("Connection from " + socket);
                int socketID = connectionStore.addSocket(socket);
                new ClientConnectoin(server, socket, socketID);
            } catch (IOException e) {
                System.out.println("The socket is closed, as the server is restarting.");
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeListener() throws IOException {
        this.listener.close();
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }
}
