package seng302.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng302.Client.Client;
import seng302.Race.Race;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class StartController {

    private Button connectBtn;
    private Stage primaryStage;
    @FXML private TextField ipField;

    public StartController() {
//        this.primaryStage = mainStage;

        Coordinate.setWindowWidthX(800);
        Coordinate.setWindowHeightY(600);
//
//        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
//        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));
    }

    @FXML
    public void initialize(){
        ipField.setText("localhost:4941");
    }


    /**
     * Called when the user selects the start race button.
     *
     * Changes from the start page to the raceview.
     */
    @FXML
    public void connect() throws IOException, InterruptedException {
        System.out.println("connect");
        ClientController clientController = new ClientController(getIp(), getPort());
        boolean ready = clientController.startClient();
        if (ready){
            Race race = clientController.getRace();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));

            LobbyController lobbyController = new LobbyController(race);
            lobbyController.setPrimaryStage(primaryStage);
            loader.setController(lobbyController);
            Parent root = loader.load();

            Scene rootScene = new Scene(root);
            primaryStage.setScene(rootScene);
            KeyBindingUtility.setKeyBindings(rootScene, race);
            lobbyController.initialiseTable();
        }
        else{
            System.out.println("no connecterino");
        }

    }




    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Called when the user clicks the practice race start button.
     * Runs a server locally, which the client application automatically connects with using a message click.
     */
    public void practiceRace() {

    }

    private String getIp() {
        String ipInput = ipField.getText();
        String[] splitInput = ipInput.split(":");
        return  ("http://" + splitInput[0]);
    }

    private int getPort() {
        String ipInput = ipField.getText();
        String[] splitInput = ipInput.split(":");
        return (Integer.parseInt(splitInput[1]));
    }
}
