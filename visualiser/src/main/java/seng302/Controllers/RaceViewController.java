package seng302.Controllers;

import javafx.stage.Stage;

/**
 * Handles all start of application code.
 */
public class RaceViewController {

    /**
     * Constructor, sets up the listeners for the window width and height.
     * @param mainStage the main stage of the application.
     */
    public RaceViewController(Stage mainStage) {
        Coordinate.setWindowWidthX(800);
        Coordinate.setWindowHeightY(600);

        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));
    }
}
