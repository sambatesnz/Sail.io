package seng302.Visualiser;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import seng302.RaceObjects.Boat;


public class BoatSprite {
    private Pane stack;
    private Boat boatObject;
    private Polyline boatIcon;
    private Polyline wake;
    private Text text;
    private Color boatColour;
    private Circle tc;
    private Circle controlCircle;
    private Polyline sail;
    private int id;


    public BoatSprite(Boat boat){
        this.boatObject = boat;
        initialiseBoatIcon();
        initialiseWake();
        text = new Text();
        tc = new Circle(2);
        tc.setCenterY(0);
        tc.setCenterX(0);
        initialiseControlCircle();
        initialiseSail();
        stack = new Pane();
        stack.getChildren().add(boatIcon);
        stack.getChildren().add(text);
        stack.getChildren().add(wake);
        stack.getChildren().add(tc);
        stack.getChildren().add(controlCircle);
        stack.getChildren().add(sail);
    }

    public Pane getStack(){
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

    private void initialiseControlCircle(){
        if (boatObject.getSourceId() == 103) {
            controlCircle = new Circle(10);
            controlCircle.setCenterX(0);
            controlCircle.setCenterY(0);
            controlCircle.setStroke(Color.INDIANRED);
            controlCircle.setFill(Color.TRANSPARENT);
        }
    }

    private void initialiseSail(){
        sail = new Polyline();
//        sail.getTransforms().addAll(new Scale(3, 3));
        sail.setStroke(Color.RED);
        for(int i=0; i<360; i++){
            sail.getPoints().addAll(Math.sin(Math.toRadians(i)*2), i/22.5);
        }
    }

    public void sailIn(){
        for(int i=0; i<720; i += 2){
            sail.getPoints().set(i, Math.log(i+1)*Math.sin(Math.toRadians((System.currentTimeMillis()+i)/1.0d)));
            sail.getPoints().set(i + 1, i/45d);
        }
    }
    public void sailOut() {
        for (int i = 0; i < 720; i += 2) {
            sail.getPoints().set(i, 0d);
            sail.getPoints().set(i + 1, i / 45d);
        }

    }



}

