package seng302.Controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Race;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

public class LobbyController {

    @FXML private Button forceStartBtn;
    private Stage primaryStage;
    @FXML private JFXTreeTableView<GenericBoat> contestantTable;
    @FXML private JFXTreeTableColumn<GenericBoat, String> teamColumn;
    @FXML private JFXTreeTableColumn<GenericBoat, String> clientColumn;
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
        teamColumn = new JFXTreeTableColumn<>("Team Name");
        clientColumn = new JFXTreeTableColumn<>("Country");

        teamColumn.setCellValueFactory(
                new Callback<TreeTableColumn.CellDataFeatures<GenericBoat, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GenericBoat, String> param) {
                        return param.getValue().getValue().getBoatName();
                    }
                }
        );

        clientColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GenericBoat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GenericBoat, String> param) {
                return param.getValue().getValue().getCountry();
            }
        });

        TreeItem<GenericBoat> tableRoot = new RecursiveTreeItem<GenericBoat>(race.boatsObs, RecursiveTreeObject::getChildren);
        contestantTable.setRoot(tableRoot);
        contestantTable.getColumns().setAll(teamColumn, clientColumn);
        contestantTable.setShowRoot(false);
        contestantTable.setFocusTraversable(false);
        forceStartBtn.setFocusTraversable(true);
        //contestantTable.setItems(race.boatsObs);
        forceStartBtn.setVisible(false);  //Temporarily removed Force Start button for demonstration
    }

    @FXML
    public void initialiseTime() {
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
            try {

                // 99:99:99 means raceStatus is raceStartTimeNotSet so hide countdown
                Platform.runLater(() -> {
                    if (newValue.equals(String.format(" %02d:%02d:%02d", 99, 99, 99))) {
                        timeToStart.setVisible(false);
                        lobbyDescriptionText.textProperty().setValue("Waiting for more players to join.");
                    } else {
                        timeToStart.setVisible(true);
                        lobbyDescriptionText.textProperty().setValue("Time to race start:");
                    }
                });

            } catch (Exception e) {
                System.out.println("tried to set time, but I null-pointered.");
                e.printStackTrace();
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void forceStart() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/RaceView.fxml"));

        if (primaryStage.getWidth() == 1200) {
            primaryStage.setWidth(1199);
            primaryStage.setHeight(800);
        } else {
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
        }
        primaryStage.setWidth(1200);
        IRaceController raceController = null;
        switch (race.getRaceMode()) {
            case RACE:
                raceController = new RaceController(race);
                break;
            case PRACTICE:
                raceController = new RaceController(race);
                break;
            case AGAR:
                raceController = new AgarRaceController(race);
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
