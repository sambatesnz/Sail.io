package seng302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.Controllers.RaceViewController;

import java.io.IOException;

/**
 * Runs the javafx program
 */
public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("RaceView.fxml"));
            RaceViewController raceViewController = new RaceViewController(primaryStage);
            Parent root = loader.load();
            Scene rootScene = new Scene(root);
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(800);
            primaryStage.setScene((rootScene));
            primaryStage.setTitle("RaceView");
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}