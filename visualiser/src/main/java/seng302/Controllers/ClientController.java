package seng302.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.Client.Client;
import seng302.Client.Messages.Message;
import seng302.Race.Race;
import seng302.UserInput.PracticeMessage;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.util.Timer;
import java.util.TimerTask;

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

    public ClientController(String ip, int port, boolean practiceRace, Stage primaryStage, Scene startScene) {
        this.ipAddr = ip;
        this.port = port;
        this.practiceRace = practiceRace;
        this.primaryStage = primaryStage;
        this.startScene = startScene;
    }

    private void checkPracticeRace() {
        try {
            client.sendPracticeMessage(new PracticeMessage(PracticeMessage.START));
            Timer raceTimer = new Timer();
            raceTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        client.sendPracticeMessage(new PracticeMessage(PracticeMessage.END));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(
                            () -> {
                                race.setRaceReady(false);
                                Message.resetData();
                                client.disconnect();
                                primaryStage.setScene(startScene);
                            }
                    );

                }
            }, 1000 * 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a race and tries to establish connection to a server
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

        System.out.println("race ready = "+ race.isConnectedToServer());

        long startTime = System.currentTimeMillis();
    }

    public Race getRace() {
        return race;
    }
}
