package seng302.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import seng302.utility.BoatAnimation;

import java.util.ArrayList;

import static java.util.Arrays.asList;

/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {

    @FXML private Canvas mainCanvas;
    final long startNanoTime = System.nanoTime();
    double x = 0;
    double y = 0;


    public void initialize(){

        BoatAnimation animation = new BoatAnimation(mainCanvas);
        animation.start();

    }
}
