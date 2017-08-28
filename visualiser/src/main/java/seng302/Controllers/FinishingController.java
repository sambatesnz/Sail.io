package seng302.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * Created by sba136 on 28/08/17.
 */
public class FinishingController {
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }

    @FXML
    public void exitToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/StartingPage.fxml"));

        Parent root = loader.load();
        Scene rootScene = new Scene(root);

        StartController startController = loader.getController();

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(false);
        primaryStage.setScene(rootScene);
        primaryStage.setTitle("RaceView");
        primaryStage.show();

        startController.setPrimaryStage(primaryStage);
        startController.setStartScene(rootScene);
    }
}
