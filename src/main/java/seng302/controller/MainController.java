package seng302.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import seng302.objects.RaceAnimationTimer;

/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {

    @FXML private Canvas mainCanvas;
    final long startNanoTime = System.nanoTime();

    public void initialize(){

        RaceAnimationTimer animation = new RaceAnimationTimer(mainCanvas);
        animation.start();

    }
}
