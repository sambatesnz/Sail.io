package seng302;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import seng302.Model.*;
import seng302.controller.MainController;

/**
 * The main class, start everything up and runs it
 */
public class App extends Application {

    private static Stage primaryStage; // **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        App.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return App.primaryStage;
    }

    /**
     * Loads and starts the GUI main window
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        setPrimaryStage(primaryStage);

        primaryStage.setTitle("SailFast - Sprint 2 - Team 4");
        //primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));

        // Gets the screen resolution of the primary screen.
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

        // Sets the primaryStage size as that of the primary screen.
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.show();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainWindow.fxml"));
        Parent root = loader.load(); // throws IOException

        MainController mainWindowController = loader.getController();
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));

    }

    public static void main(String[] args) throws IOException {

        Course myCourse =  new Course("Americas Cup");

        try {
            launch(args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
