package seng302.Controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.annotations.Ignore;
import seng302.Client.Client;
import seng302.Race.Race;
import seng302.RaceObjects.Boat;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;
import java.util.ArrayList;

public class LobbyController {

    @FXML
    private Button forceStartBtn;
    private Stage primaryStage;
    @FXML
    private TableView<Boat> contestantTable;
    @FXML
    private TableColumn<Boat, String> teamColumn;
    @FXML
    private TableColumn<Boat, String> clientColumn;
    private Race race;
    private String ipAddr;
    @FXML
    private Text timeToStart;
    private int port;

    public LobbyController(Race race) {
        this.race = race;
    }

    public void initialiseTable(){
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("boatName")
        );

        clientColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("country")
        );


        contestantTable.setItems(race.boatsObs);
    }

    @FXML
    public void initialiseTime(){
        timeToStart.textProperty().bind(race.timeToStartProperty());

        timeToStart.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(String.format(" %02d:%02d:%02d", 0, 1, 0))) {
                Platform.runLater(
                    () -> {
                        try {
                            forceStart();
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }
                );
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void forceStart() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));
        RaceController raceController = new RaceController(race);
        loader.setController(raceController);
        Parent root = loader.load();

        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);

        KeyBindingUtility.setKeyBindings(rootScene, race);
    }
}
