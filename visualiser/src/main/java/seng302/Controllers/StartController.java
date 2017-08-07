package seng302.Controllers;

import javafx.stage.Stage;

/**
 * Created by msi52 on 7/08/17.
 */
public class StartController {

    private Stage primaryStage;

    public StartController(Stage mainStage) {
        this.primaryStage = mainStage;

        Coordinate.setWindowWidthX(800);
        Coordinate.setWindowHeightY(600);

        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));
    }

    /**
     * Called when the user selects the start race button.
     *
     * Changes from the start page to the raceview.
     */
    public void startRaceClick() {

    }

    public void practiceRaceClick() {

    }

}
