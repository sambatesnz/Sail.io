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
        Coordinate.setWindowX(800);
        Coordinate.setWindowY(600);

        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowX((newValue).doubleValue()));
        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowY(newValue.doubleValue()));
    }
}
