package seng302.Controllers;

import seng302.Client.Client;
import seng302.RaceObjects.Race;

public class ClientController {
    private Race race;
    private String ipAddr;
    private int port;

    public ClientController(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }


    /**Starts a race and tries to establish connection to a server
     * returns true if the client connects to the server, false otherwise.
     * @throws InterruptedException if client connection fails
     */
    public void startClient() throws InterruptedException {

        try {
            race = new Race();
            Thread serverThread = new Thread(() -> {
                Client client = new Client(race, ipAddr, port);
                client.connect();
                client.processStreams();
            });
            serverThread.start();
        }catch (Exception e){
            System.out.println("WAT");
        }
        System.out.println("race ready = "+ race.isConnectedToServer());

        long startTime = System.currentTimeMillis();
//        while(!race.isConnectedToServer()&&(System.currentTimeMillis()-startTime)<3000)
//        {
//            System.out.println(race.isConnectedToServer());
//            Thread.sleep(10);
//        }
    }

    public Race getRace() {
        return race;
    }
}
