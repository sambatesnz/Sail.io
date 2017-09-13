package seng302.Controllers;

import seng302.Client.Client;
import seng302.RaceObjects.Race;


/**
 * Staging screen which creates a connection with the server
 */
public class ClientController {
    private Race race;
    private String ipAddr;
    private int port;
    private Client client;

    public ClientController(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }

    /**
     * Starts a race and tries to establish connection to a server
     * returns true if the client connects to the server, false otherwise.
     * @throws InterruptedException if client connection fails
     */
    public void startClient() throws InterruptedException {
        System.out.println("making a new race......");
        race = new Race();
        Thread serverThread = new Thread(() -> {
            client = new Client(race, ipAddr, port);
            client.connect();
            client.processStreams();
        });
        serverThread.start();
    }

    public Race getRace() {
        return race;
    }
}
