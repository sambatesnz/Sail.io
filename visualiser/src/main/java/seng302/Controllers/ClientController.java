package seng302.Controllers;

import seng302.Client.Client;
import seng302.Race.Race;

import java.io.IOException;

public class ClientController {
    private Race race;
    private String ipAddr;
    private int port;
    private Client client;
    private boolean practiceRace = false;

    public ClientController(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }

    public ClientController(String ip, int port, boolean practiceRace) {
        this.ipAddr = ip;
        this.port = port;
        this.practiceRace = practiceRace;
    }

    /**
     * Starts a race and tries to establish connection to a server
     */
    public void startClient() throws InterruptedException {

//        try {
            race = new Race();
            Thread serverThread = new Thread(() -> {
                client = new Client(race, ipAddr, port);
                client.connect();
                if (practiceRace) {
                    try {
                        client.sendPracticeMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                client.processStreams();
            });
            serverThread.start();
//        } catch (Exception e){
//            System.out.println("WAT");
//        }
        System.out.println("race ready = "+ race.isConnectedToServer());

        long startTime = System.currentTimeMillis();
    }

    public Race getRace() {
        return race;
    }
}
