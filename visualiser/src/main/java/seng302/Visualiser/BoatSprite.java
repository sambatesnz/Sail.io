package seng302.Visualiser;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import seng302.RaceObjects.Boat;


public class BoatSprite {
    private Boat boatObject;
    private Polyline boatIcon;
    private Polyline wake;
    private Text text;
    private Color boatColour;
    private Circle tc;
    private Circle controlCircle;
    private int id;


    public BoatSprite(Boat boat){
        this.boatObject = boat;
        initialiseBoatIcon();
        initialiseWake();
        text = new Text();
        tc = new Circle(2);
        tc.setCenterY(0);
        tc.setCenterX(0);
        initaliseControlCircle();

    }

    public Pane getStack(){
        Pane stack = new Pane();
        stack.getChildren().add(boatIcon);
        stack.getChildren().add(text);
        stack.getChildren().add(wake);
        stack.getChildren().add(tc);
        stack.getChildren().add(controlCircle);
        return stack;
    }

    public Boat getBoat(){
        return boatObject;
    }

    private void initialiseWake(){
        wake = new Polyline();
        wake.getPoints().addAll(0.0, 0.0,
                                0.0, 1.0);
        wake.setStroke(Color.CYAN);
    }

    private void initialiseBoatIcon(){
        boatIcon = new Polyline();
        boatIcon.getPoints().addAll(0.0, 0.0,
                0.0, -8.0,
                0.0, 8.0,
                -5.0, 8.0,
                0.0, -8.0,
                5.0, 8.0,
                -5.0, 8.0);
        boatIcon.setStroke(boatObject.getColour().desaturate().desaturate());
        boatIcon.setFill(boatObject.getColour().saturate().saturate());
    }

    private void initaliseControlCircle(){
        if (boatObject.getSourceId() == 103) {
            controlCircle = new Circle(10);
            controlCircle.setCenterX(0);
            controlCircle.setCenterY(0);
            controlCircle.setStroke(Color.INDIANRED);
            controlCircle.setFill(Color.TRANSPARENT);
        }
    }




}

