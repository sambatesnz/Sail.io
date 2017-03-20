package seng302.controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import seng302.objects.BoatAnimation;

/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {

    @FXML private Canvas mainCanvas;
    final long startNanoTime = System.nanoTime();

    public void initialize(){

        BoatAnimation animation = new BoatAnimation(mainCanvas);
        animation.start();

    }
}
