package seng302.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import seng302.Client.Client;
import seng302.Race.Race;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

public class LobbyController {

    private Button startRaceBtn;
    private Stage primaryStage;
    private Race race;
    private String ipAddr;
    private int port;

    public LobbyController(String ip, int port) {
//        this.primaryStage = mainStage;
        this.ipAddr = ip;
        this.port = port;

        //Coordinate.setWindowWidthX(800);
        //Coordinate.setWindowHeightY(600);
//
//        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
//        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));
    }

    @FXML
    public void initialize(){

        race = new Race();

        Thread serverThread = new Thread(() -> {
            Client client = new Client(race, ipAddr, port);
            client.connect();
            client.processStreams();
        });
        serverThread.start();
    }


    /**
     * Called when the user selects the start race button.
     *
     * Changes from the start page to the raceview.
     */
    public void startRace() throws IOException {
//        Stage stage = startRaceBtn.getScene().getWindow();


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));
        System.out.println(primaryStage);
        RaceViewController raceViewController = new RaceViewController(primaryStage);

        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);
        KeyBindingUtility.setKeyBindings(rootScene);

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        System.out.println(this.primaryStage);
    }
    @FXML
    public void forceStart() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));

        //-??-//
        RaceViewController raceViewController = new RaceViewController(primaryStage);
        //SEEMS LIKE WE JUST NEED THIS TO RUN STATEMENTS IN THE CONSTRUCTOR. CAN PROBABLY REMOVE.

        RaceController raceController = new RaceController(race);
        loader.setController(raceController);
        Parent root = loader.load();

        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);

        KeyBindingUtility.setKeyBindings(rootScene);

    }
}
