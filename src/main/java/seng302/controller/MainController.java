package seng302.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
        ArrayList<Color> boatColors = new ArrayList<>(asList(Color.CHOCOLATE, Color.GREEN, Color.CYAN, Color.GOLD, Color.DARKGREY, Color.PURPLE));

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();

        new AnimationTimer()
        {
            double x = 0;
            double y = 0;
            double canvasWidth = gc.getCanvas().getWidth();
            double canvasHeight = gc.getCanvas().getHeight();

            public void handle(long currentNanoTime)
            {

                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                // background image clears canvas
                x += 1;
                y += 2;

                if (x > canvasWidth){
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
}
