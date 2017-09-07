package seng302.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Race;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sba136 on 28/08/17.
 */
public class FinishingController {
    @FXML
    private TableView<Boat> finishersTable;
    @FXML
    private TableColumn<Boat, String> boatColumn;
    @FXML
    private TableColumn<Boat, String> positionColumn;
    @FXML
    private TableColumn<Boat, String> finishTimeColumn;

    private Stage primaryStage;

    private Race race;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }
    @FXML
    public void initialize(){

    }
    public void initialiseTable(){
        positionColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("boatName")
        );

        boatColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("boatName")
        );

        finishTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Boat, String>("boatName")
        );

        race.setFinishedBoats(Arrays.asList(new Boat("steve", "steve", 2, "steve")));
        finishersTable.setItems(race.getFinishedBoats());
    }
    @FXML
    public void exitToMenu() throws IOException {
        race.setConnectedToServer(0);
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
