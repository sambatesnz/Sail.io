package seng302.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seng302.App;
import seng302.Model.RaceAnimationTimer;

/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {

    @FXML private Canvas mainCanvas;
    @FXML private Pane boatPane;
    @FXML private Pane coursePane;


    public void initialize(){

        RaceAnimationTimer animation = new RaceAnimationTimer();
        animation.start();



        Stage primaryStage = App.getPrimaryStage();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

        //The solution is to bind the dimensions of the canvas to the canvas of the primary stage
        mainCanvas.widthProperty().bind(primaryStage.widthProperty());
        mainCanvas.heightProperty().bind(primaryStage.heightProperty());


    }

//    InvalidationListener listener = new InvalidationListener(){
//        @Override
//        public void invalidated(Observable o) {
//            redraw();
//        }
//
//        private void redraw() {
//            System.out.println("FUCK YEAH");
//            //g.setFill(Color.BLACK);
//            //g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        }
//
//
//    };
//
//    mainCanvas.widthProperty().addListener(listener);
//    mainCanvas.heightProperty().addListener(listener);
}
