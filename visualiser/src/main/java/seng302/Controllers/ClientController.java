package seng302.Controllers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.Client.Client;
import seng302.Client.Messages.Message;
import seng302.Race.Race;
import seng302.UserInput.PracticeMessage;
import seng302.UserInput.PracticeMessageMeaning;
import seng302.RaceObjects.Race;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Staging screen which creates a connection with the server
 */
public class ClientController {
    private Race race;
    private String ipAddr;
    private int port;
    private Client client;
    private boolean practiceRace = false;
    private Stage primaryStage;
    private Scene startScene;

    public ClientController(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }

    /**
     * This constructor creates a practice race
     * @param ip ip address
     * @param port port number
     * @param primaryStage stage
     * @param startScene scene
     */
    public ClientController(String ip, int port, Stage primaryStage, Scene startScene) {
        this.ipAddr = ip;
        this.port = port;
        this.practiceRace = true;
        this.primaryStage = primaryStage;
        this.startScene = startScene;
    }

    private void checkPracticeRace() {
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

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    client.sendPracticeMessage(new PracticeMessage(PracticeMessageMeaning.END, race.getBoats().get(0).getSourceId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.runLater(
                        () -> {
                            Coordinate.setTrackingBoat(false);
                            race.finishRace();
                            race.setRaceReady(false);
                            Message.resetData();
                            client.disconnect();
                            primaryStage.setScene(startScene);
                        }
                );
            }
        }, MINUTE_AND_TWENTY_SECONDS);
    }

    /**Starts a race and tries to establish connection to a server
     * returns true if the client connects to the server, false otherwise.
     * @throws InterruptedException if client connection fails
     */
    public void startClient() throws InterruptedException {
        race = new Race();
        Thread serverThread = new Thread(() -> {
            client = new Client(race, ipAddr, port);
            client.connect();
            if (practiceRace) {
                checkPracticeRace();
            }
            client.processStreams();
        });
        serverThread.start();
    }

    public Race getRace() {
        return race;
    }
}
