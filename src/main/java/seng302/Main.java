package seng302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.Controllers.RaceViewController;
import seng302.Server.GeneratedData;
import seng302.Server.StreamServer;

import java.io.IOException;

/**
 * Runs the javafx program
 */
public class Main extends Application {

    public static void main(String[] args) throws IOException {

        //Client

//        StreamClient client = new StreamClient();
//        client.connect();
//        client.retrieveData();

        //Server

//        StreamServer server = new StreamServer(9090);
//        GeneratedData genData = new GeneratedData();
//        genData.runServerTimers();
//        server.start(genData);

        //App

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
            primaryStage.setMaximized(true);
            primaryStage.setScene((rootScene));
            primaryStage.setTitle("RaceView");
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}