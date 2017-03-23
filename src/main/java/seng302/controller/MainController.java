package seng302.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seng302.App;
import seng302.Model.*;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {
    private Course raceCourse;

    @FXML private Canvas mainCanvas;
    @FXML private GridPane boatGridPane;
    @FXML private Group raceGroup;


    public void initialize(){

        Course raceCourse = new Course("Kevin");

        Race mainRace = new Race(raceGroup, raceCourse);
        mainRace.raceSetup();

        mainCanvas.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        mainCanvas.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8);

        RaceAnimationTimer animation = new RaceAnimationTimer(mainRace);
        animation.start();
    }

    public void displayDots() {

        ArrayList<Color> boatColors = new ArrayList<>(asList(Color.CHOCOLATE, Color.GREEN, Color.CYAN, Color.GOLD, Color.DARKGREY, Color.PURPLE));

        GraphicsContext gc= mainCanvas.getGraphicsContext2D();

        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        System.out.println("BoatGridPaneWidth: " + boatGridPane.getWidth());
//        System.out.println("AnchorPaneWidth: " + boatAnchorPane.widthProperty());
        System.out.println("canvasWidth: " + gc.getCanvas().getWidth());
        System.out.println("canvasHeight: " + gc.getCanvas().getHeight());


        new AnimationTimer() {
            double x = 0;
            double y = 0;

            double canvasWidth = gc.getCanvas().getWidth();

            double canvasHeight = gc.getCanvas().getHeight();

            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                gc.setFill(Color.LIGHTCYAN);
                gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                gc.setFill(Color.GREEN);
                gc.fillRect(50, 50, 16, 16);

                x += 2;
                y += 1;

                if (x > canvasWidth || y > canvasHeight || y > canvasWidth || x > canvasHeight) {
                    x = 0;
                    y = 0;
                }
                gc.setFill(boatColors.get(3));
                gc.fillOval(y, x, 15, 15);
                gc.setFill(boatColors.get(2));
                gc.fillOval(x, y, 15, 15);

            }
        }.start();

    }
    public double convertLatitudeToX(double lat) {
        double x =  ((mainCanvas.getWidth()/360.0) * (180 + lat));
        return x;
    }

    public double convertLongitudeToY(double longitude) {
        double y = ((mainCanvas.getHeight()/180.0) * (90 - longitude));
        return y;
    }

    public class XYPoint {
        public double x;
        public double y;

        public XYPoint(double x, double y){
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<Double> getCanvasBounds(){
        double minXBound = 50;
        double minYBound = 50;
        double maxXBound = mainCanvas.getWidth() - 50;
        double maxYBound = mainCanvas.getHeight() - 50;

        ArrayList<Double> canvasBounds = new ArrayList<>();
        canvasBounds.add(minXBound);
        canvasBounds.add(maxXBound);
        canvasBounds.add(minYBound);
        canvasBounds.add(maxYBound);
        return canvasBounds;
    }


    public ArrayList<MainController.XYPoint> convertLatLongtoXY(ArrayList<CompoundMark> raceMarks) {
        ArrayList<MainController.XYPoint> compoundMarksXY = new ArrayList<>();
        ArrayList<Double> bounds = raceCourse.findMaxMinLatLong();

        for (CompoundMark mark : raceMarks) {
            for (int i = 0; i < mark.getCompoundMarks().size(); i++) {
                double x = convertLatitudeToX(mark.getCompoundMarks().get(i).getLatitude());
                double y = convertLongitudeToY(mark.getCompoundMarks().get(i).getLongitude());

                compoundMarksXY.add(new MainController.XYPoint(x, y));
            }
        }
        System.out.println(compoundMarksXY);
        return compoundMarksXY;
    }
}

