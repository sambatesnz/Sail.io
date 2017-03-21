package seng302.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seng302.App;
import seng302.Model.RaceAnimationTimer;

import java.util.ArrayList;
import static java.util.Arrays.asList;

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


        /**
         * Tried to set the rect color here.
         */
//        GraphicsContext gc= mainCanvas.getGraphicsContext2D();
//        gc.setFill(Color.AQUA);
//        gc.fillRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());
//
//        System.out.println(mainCanvas.widthProperty());


        /**
         * Using dots to explore the size of it all.
         */
        ArrayList<Color> boatColors = new ArrayList<>(asList(Color.CHOCOLATE, Color.GREEN, Color.CYAN, Color.GOLD, Color.DARKGREY, Color.PURPLE));

        GraphicsContext gc= mainCanvas.getGraphicsContext2D();

        new AnimationTimer() {
            double x = 0;
            double y = 0;

            double canvasWidth = gc.getCanvas().getWidth();
            double canvasHeight = gc.getCanvas().getHeight();

            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

                x += 1;
                y += 2;

                if (x > canvasWidth || y > canvasHeight) {
                    System.out.printf("canvasWidth: %d, canvasHeight: %d.", x, y);
                    x = 0;
                    y = 0;
                }
                gc.setFill(boatColors.get(0));
                gc.fillOval(x, y, 15, 15);
                gc.setFill(boatColors.get(1));
                gc.fillOval(y, x, 15, 15);


            }
        }.start();

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
