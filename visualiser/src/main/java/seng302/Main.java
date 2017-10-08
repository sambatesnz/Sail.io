package seng302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import seng302.Controllers.Coordinate;
import seng302.Controllers.StartController;

import java.io.IOException;

/**
 * Runs the javafx program
 */
public class Main extends Application {

    public static void main(String[] args) throws IOException {
        Application.launch(Main.class, (String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/StartingPage.fxml"));

            Parent root = loader.load();
            Scene rootScene = new Scene(root);

            StartController startController = loader.getController();

            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(800);
            primaryStage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("Logo/SailIOIcon.png"))));
            primaryStage.setScene(rootScene);
            primaryStage.setFullScreen(true);
            primaryStage.setTitle("Sail.io");
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.show();

            Coordinate.setWindowWidthX(800);
            Coordinate.setWindowHeightY(600);

            primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowWidthX((newValue).doubleValue()));
            primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> Coordinate.setWindowHeightY(newValue.doubleValue()));

            startController.setPrimaryStage(primaryStage);
            startController.setStartScene(rootScene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}