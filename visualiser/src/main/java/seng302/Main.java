package seng302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.Controllers.RaceViewController;
import seng302.Controllers.StartController;
import seng302.UserInput.KeyBindingUtility;

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
//            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("RaceView.fxml"));
//            RaceViewController raceViewController = new RaceViewController(primaryStage);
//
//            Parent root = loader.load();
//            Scene rootScene = new Scene(root);
//            KeyBindingUtility.setKeyBindings(rootScene);
//            primaryStage.setMinHeight(600);
//            primaryStage.setMinWidth(800);
//            primaryStage.setMaximized(true);
//            primaryStage.setScene((rootScene));
//            primaryStage.setTitle("RaceView");
//            primaryStage.show();

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("StartingPage.fxml"));
            StartController startController = new StartController(primaryStage);

            Parent root = loader.load();
            Scene rootScene = new Scene(root);
            KeyBindingUtility.setKeyBindings(rootScene);
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(800);
            primaryStage.setMaximized(true);
            primaryStage.setScene((rootScene));
            primaryStage.setTitle("RaceVision by Full Mast");
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}