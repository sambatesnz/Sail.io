package seng302.utility;

//public class App
//{
//    public static void main( String[] args )
//    {
//
//        // Creates the Regatta, and (in this case) makes the Regatta an AC35 instance.
//        Regatta americasCup = new Regatta();
//        americasCup.isAC35();
//
//        // Doing the important stuff.
//        Race americasCupRace = new Race();
//        americasCupRace.addRacingBoats(6, americasCup.getCompetitors());
//        americasCupRace.addEvents(americasCup.getEventList());
//        americasCupRace.setRacePlaybackDuration();
//        americasCupRace.displayStarters();
//        americasCupRace.reportEventPositions();
//        americasCupRace.displayFinishers();
//    }
//}
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import seng302.controller.MainController;

/**
 * The main class, start everything up and runs it
 */
public class App extends Application {

    /**
     * Loads and starts the GUI main window
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainWindow.fxml"));
        Parent root = loader.load(); // throws IOException

        MainController mainWindowController = loader.getController();

        primaryStage.setTitle("SailFast - Sprint 2 - Team 4");
        //primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
