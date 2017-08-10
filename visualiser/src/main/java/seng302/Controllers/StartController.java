package seng302.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;

/**
 * Created by msi52 on 7/08/17.
 */
public class StartController {

    private Button startRaceBtn;
    private Stage primaryStage;

    public StartController() {
//        this.primaryStage = mainStage;

        Coordinate.setWindowWidthX(800);
        Coordinate.setWindowHeightY(600);
//
//        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
//        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));
    }

    @FXML
    public void initialize(){
//        System.out.println(primaryStage);
    }


    /**
     * Called when the user selects the start race button.
     *
     * Changes from the start page to the raceview.
     */
    public void startRace() throws IOException {
//        Stage stage = startRaceBtn.getScene().getWindow();


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("RaceView.fxml"));
        System.out.println(primaryStage);
        RaceViewController raceViewController = new RaceViewController(primaryStage);

        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);
        KeyBindingUtility.setKeyBindings(rootScene);

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        System.out.println(this.primaryStage);
    }

    /**
     * Called when the user clicks the practice race start button.
     * Runs a server locally, which the client application automatically connects with using a message click.
     */
    public void practiceRace() {

    }

}
