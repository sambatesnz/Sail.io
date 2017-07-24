package seng302.Visualiser;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 * WindArrow Polyline
 */
public class WindArrow extends Polyline{

    private int defaultScale = 2;
    private Color defaultColor = Color.RED;

    public WindArrow(double... points) {
        super(points);
        addPoints();
        setScale();
        addPoints();
        setColor();
        setLayout();
    }

    private void setLayout() {
        setLayoutX(50);
        setLayoutY(50);
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
