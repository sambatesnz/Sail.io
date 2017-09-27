package seng302.Controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.Client.Client;
import seng302.RaceObjects.Race;
import seng302.UserInput.PracticeMessage;
import seng302.UserInput.PracticeMessageMeaning;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Staging screen which creates a connection with the server
 */
public class PracticeClientController {
    private Race race;
    private String ipAddr;
    private int port;
    private Client client;
    private Stage primaryStage;
    private Scene startScene;

    public PracticeClientController(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
        race = new Race();
    }

    /**
     * This constructor creates a practice race
     * @param ip ip address
     * @param port port number
     * @param primaryStage stage
     * @param startScene scene
     */
    public PracticeClientController(String ip, int port, Stage primaryStage, Scene startScene) {
        this.ipAddr = ip;
        this.port = port;
        this.primaryStage = primaryStage;
        this.startScene = startScene;
        race = new Race();
    }

    private void practiceRace() {
        final int MINUTE_AND_TWENTY_SECONDS = 80000;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    client.sendPracticeMessage(new PracticeMessage(PracticeMessageMeaning.START));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    /**
     * Starts a race and tries to establish connection to a server
     * returns true if the client connects to the server, false otherwise.
     * @throws InterruptedException if client connection fails
     */
    public void startClient() throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            client = new Client(race, ipAddr, port);
            client.connect();
            practiceRace();
            client.processStreams();
        });
        serverThread.start();
    }

    public Race getRace() {
        return race;
    }
}
