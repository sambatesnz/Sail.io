package seng302.Visualiser;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 * Arrow Polyline
 */
public class Arrow extends Polyline{

    private int defaultScale = 2;
    private Color defaultColor = Color.RED;

    public Arrow(double... points) {
        super(points);
        addPoints();
        setScale();
        addPoints();
        setColor();
    }

    public void updateScaling(double windSpeed){
        int MAXIMUM_WIND_SPEED = 23150;
        int SCALING_VECTOR_X = 8000;
        int MINIMUM_ARROW_SIZE = 2;
        double SCALING_VECTOR_Y = 0.7;
        windSpeed = windSpeed < 0 ? 0 : (windSpeed > MAXIMUM_WIND_SPEED ? MAXIMUM_WIND_SPEED: windSpeed);
        double scaleX = MINIMUM_ARROW_SIZE + windSpeed/SCALING_VECTOR_X;
        double scaleY = scaleX * SCALING_VECTOR_Y;
        setScaleX(scaleX);
        setScaleY(scaleY);
    }

    private void setColor(){
        setStroke(defaultColor);
        setFill(defaultColor);
    }

    private void setScale(){
        setScaleX(defaultScale);
        setScaleY(defaultScale);
    }

    private void addPoints(){
        getPoints().addAll(0.0, 0.0,
                -5.0, 15.0,
                5.0, 15.0,
                1.0, 0.0,
                0.0, 30.0);
    }
}
