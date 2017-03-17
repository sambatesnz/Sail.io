package seng302;

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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import seng302.controller.MainController;
import seng302.objects.AppConfig;
import seng302.objects.CourseCreator;

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

        // Gets the screen resolution of the primary screen.
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

        // Sets the primaryStage size as that of the primary screen.
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.show();
    }

    public static void main(String[] args) {

        AppConfig appconfig = new AppConfig();
        CourseCreator courseCreator = new CourseCreator(appconfig.getProperty(AppConfig.COURSE_FILE_LOCATION));


//        CourseCreator courseCreator = new CourseCreator(filname);
//        ArrayList<CompoundMark> =  courseCreator.comp


//        try {
//            launch(args);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }
}
