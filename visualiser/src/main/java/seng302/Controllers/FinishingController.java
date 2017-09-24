package seng302.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Callback;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Race;
import seng302.RaceObjects.ViewScreenType;

import java.io.IOException;

/**
 * Created by sba136 on 28/08/17.
 */
public class FinishingController {
    @FXML
    private JFXTreeTableView<GenericBoat> finishersTable;
    @FXML
    private JFXTreeTableColumn<GenericBoat, String> boatColumn;
    @FXML
    private JFXTreeTableColumn<GenericBoat, String> positionColumn;
    @FXML
    private JFXTreeTableColumn<GenericBoat, String> finishTimeColumn;
    @FXML
    private JFXButton exitToMenuBtn;

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
        positionColumn.setCellValueFactory(p -> {
            String position = String.valueOf(p.getValue().getValue().getPosition());
            return new ReadOnlyObjectWrapper<>(position);
        });

        boatColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<GenericBoat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<GenericBoat, String> param) {
                return param.getValue().getValue().getBoatName();
            }
        });

        finishTimeColumn.setCellValueFactory(p -> {
            String finishTime = String.valueOf(p.getValue().getValue().getTimeToFinish());
            return new ReadOnlyObjectWrapper<>(finishTime);
        });

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
