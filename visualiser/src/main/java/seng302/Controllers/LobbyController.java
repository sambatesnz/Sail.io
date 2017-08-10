package seng302.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng302.Client.Client;
import seng302.Race.Race;
import seng302.RaceObjects.Boat;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

public class LobbyController {

    private Button startRaceBtn;
    private Stage primaryStage;
    @FXML
    private TableView contestantTable;
    @FXML
    private TableColumn teamColumn;
    @FXML
    private TableColumn clientColumn;
    private Race race;
    private String ipAddr;
    private int port;

    public LobbyController(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }

    @FXML
    public void initialize(){

        race = new Race();

        teamColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("boatName")
        );

        clientColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("country")
        );

        race.getBoats()

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
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));
        System.out.println(primaryStage);
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
        RaceController raceController = new RaceController(race);
        loader.setController(raceController);
        Parent root = loader.load();

        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);

        KeyBindingUtility.setKeyBindings(rootScene);

    }
}
