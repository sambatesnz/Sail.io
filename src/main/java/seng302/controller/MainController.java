package seng302.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import seng302.Model.*;

import java.util.ArrayList;

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

        raceCourse = new Course("Kevin");

        Race mainRace = new Race(raceGroup, raceCourse);
        mainRace.raceSetup();

        mainCanvas.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        mainCanvas.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8);

        ArrayList<XYPoint> courseXY = convertLatLongToXY();
        displayMarks(courseXY);
        for (XYPoint pt : courseXY){
            System.out.printf("(%f, %f) ", pt.x, pt.y);
        }

        RaceAnimationTimer animation = new RaceAnimationTimer(mainRace);
        animation.start();
    }

    public void displayMarks(ArrayList<XYPoint> courseXY) {

        for (XYPoint point : courseXY) {
            Rectangle r = new Rectangle(point.x, point.y, 5, 5);
            r.setFill(Color.BLACK);
            raceGroup.getChildren().add(r);
        }

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
        double maxXBound = mainCanvas.getWidth() - 100;
        double maxYBound = mainCanvas.getHeight() - 100;

        ArrayList<Double> canvasBounds = new ArrayList<>();
        canvasBounds.add(minXBound);
        canvasBounds.add(maxXBound);
        canvasBounds.add(minYBound);
        canvasBounds.add(maxYBound);
        return canvasBounds;
    }


    public ArrayList<XYPoint> convertLatLongToXY() {
        ArrayList<XYPoint> compoundMarksXY = new ArrayList<>();
        ArrayList<Double> latLongBounds = raceCourse.findMaxMinLatLong();
        ArrayList<Double> canvasBounds = getCanvasBounds();
        double deltaLat = Math.abs((latLongBounds.get(1) - latLongBounds.get(0)));
        double deltaLong = Math.abs((latLongBounds.get(3) - latLongBounds.get(2)));

        for (CompoundMark mark : raceCourse.getCourseCompoundMarks()) {
            for (int i = 0; i < mark.getCompoundMarks().size(); i++){
                double markLat = mark.getCompoundMarks().get(i).getLatitude();
                double markLong = mark.getCompoundMarks().get(i).getLongitude();

                double maxWidth = canvasBounds.get(1);
                double maxHeight = canvasBounds.get(3);

                double minLat = latLongBounds.get(0);
                double minLong = latLongBounds.get(2);

                double x = maxWidth*(markLong - minLong)/deltaLong;
                double y = maxHeight*(markLat - minLat)/deltaLat;

                XYPoint newPoint = new XYPoint(x+50, y+50);
                compoundMarksXY.add(newPoint);
            }
        }
        return compoundMarksXY;
    }
}

