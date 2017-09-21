package seng302.Controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.BoatInterface;
import seng302.RaceObjects.Race;
import seng302.RaceObjects.ViewScreenType;

import java.io.IOException;

/**
 * Created by sba136 on 28/08/17.
 */
public class FinishingController {
    @FXML
    private TableView<BoatInterface> finishersTable;
    @FXML
    private TableColumn<BoatInterface, String> boatColumn;
    @FXML
    private TableColumn<BoatInterface, String> positionColumn;
    @FXML
    private TableColumn<BoatInterface, String> finishTimeColumn;
    @FXML
    private Button exitToMenuBtn;

    private Stage primaryStage;

    private Race race;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }
    @FXML
    public void initialize(){
        exitToMenuBtn.setVisible(false);
    }
    public void initialiseTable(){
        positionColumn.setCellValueFactory(
                new PropertyValueFactory<BoatInterface, String>("placement")
        );

        boatColumn.setCellValueFactory(
                new PropertyValueFactory<BoatInterface, String>("boatName")
        );

        finishTimeColumn.setCellValueFactory(
                new PropertyValueFactory<BoatInterface, String>("finishTimeString")
        );

        finishersTable.setItems(race.getBoatsForScoreBoard());
    }
    @FXML
    public void exitToMenu() throws IOException {
        race.setConnectedToServer(0);
        race.setViewScreen(ViewScreenType.MENU.getViewScreenType());
        System.out.println("oo");
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
