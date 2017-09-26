package seng302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seng302.Controllers.Coordinate;
import seng302.Controllers.StartController;
import seng302.UserInput.KeyBindingUtility;

import java.io.IOException;
import java.io.InputStream;

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
            primaryStage.setMaximized(false);
            primaryStage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("Logo/SailIOIcon.png"))));
            primaryStage.setScene(rootScene);
            primaryStage.setTitle("RaceView");
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