package seng302.Model;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.util.Arrays.asList;

/**
 * Created by tjg73 on 17/03/17.
 */
public class RaceAnimationTimer extends AnimationTimer {

    private double x = 0;
    private double y = 0;
    private double canvasWidth;
    private double canvasHeight;
    private Canvas currentCanvas;
    private GraphicsContext gc;
    private ArrayList<Color> boatColors = new ArrayList<>(asList(Color.CHOCOLATE, Color.GREEN, Color.CYAN, Color.GOLD, Color.DARKGREY, Color.PURPLE));

    public RaceAnimationTimer(Canvas canvas){
        currentCanvas = canvas;
        gc = currentCanvas.getGraphicsContext2D();
        canvasHeight = gc.getCanvas().getHeight();
        canvasWidth = gc.getCanvas().getWidth();
    }

    public void handle(long currentNanoTime)
    {

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        // background image clears canvas
        x += 1;
        y += 2;

        if (x > canvasWidth || y > canvasHeight){
            x = 0;
            y = 0;
        }

        gc.setFill(boatColors.get(0));
        gc.fillOval(x, y, 15, 15);
        gc.setFill(boatColors.get(1));
        gc.fillOval(y, x, 15, 15);

    }

}
