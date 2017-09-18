package seng302.Controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng302.RaceObjects.Race;
import seng302.RaceObjects.Boat;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

public class LobbyController {

    @FXML private Button forceStartBtn;
    private Stage primaryStage;
    @FXML private TableView<Boat> contestantTable;
    @FXML private TableColumn<Boat, String> teamColumn;
    @FXML private TableColumn<Boat, String> clientColumn;
    private Race race;
    private boolean raceStarted;
    private String ipAddr;
    @FXML private Text timeToStart;
    @FXML private Text lobbyDescriptionText;
    private int port;

    public LobbyController(Race race) {
        this.race = race;
        this.raceStarted = false;
    }

    public void initialiseTable(){
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("boatName")
        );

        clientColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("country")
        );

        contestantTable.setItems(race.boatsObs);
        forceStartBtn.setVisible(false);  //Temporarily removed Force Start button for demonstration
    }

    @FXML
    public void initialiseTime(){
        timeToStart.textProperty().bind(race.timeToStartProperty());

        timeToStart.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(String.format(" %02d:%02d:%02d", 0, 0, 10)) || (race.getClientSourceId() == 0 && race.isRaceReady() && !raceStarted)) {
                Platform.runLater(
                    () -> {
                        try {
                            forceStart();
                            raceStarted = true;
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }
                );
            }

            // 99:99:99 means raceStatus is raceStartTimeNotSet so hide countdown
            if (newValue.equals(String.format(" %02d:%02d:%02d", 99, 99, 99))) {
                timeToStart.setVisible(false);
                lobbyDescriptionText.textProperty().setValue("Waiting for more players to join.");
            } else {
                timeToStart.setVisible(true);
                lobbyDescriptionText.textProperty().setValue("Time to race start:");
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void forceStart() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));

        IRaceController raceController = null;
        switch (race.getRaceMode()) {
            case RACE:
                raceController = new RaceController(race);
                break;
            case PRACTICE:
                raceController = new RaceController(race);
                break;
            case AGAR:
                break;
        }

        if (raceController != null) {
            raceController.setPrimaryStage(primaryStage);
            loader.setController(raceController);
            Parent root = loader.load();
            Scene rootScene = new Scene(root);
            primaryStage.setScene(rootScene);
            KeyBindingUtility.setKeyBindings(rootScene, race);
        }


    }
}
