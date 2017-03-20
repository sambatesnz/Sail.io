package seng302.objects;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static java.util.Arrays.asList;

/**
 * Created by tjg73 on 17/03/17.
 */
public class RaceAnimationTimer extends AnimationTimer {

    private double previousTime = 0;


    public RaceAnimationTimer(){

    }

    public void handle(long currentNanoTime)
    {
        double currentTime = ((double) currentNanoTime) / 1E9; //to convert from nano-seconds to seconds






        previousTime = currentTime;

    }

}
