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

import java.util.ArrayList;
import static java.util.Arrays.asList;

/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {

    @FXML private Canvas mainCanvas;
//    @FXML private AnchorPane boatAnchorPane;
    @FXML private GridPane boatGridPane;
    @FXML private Group raceGroup;


    public void initialize(){

        AppConfig appconfig = new AppConfig();
        String fileLocation = appconfig.getProperty(AppConfig.COURSE_FILE_LOCATION);
        CourseCreator courseCreator = new CourseCreator(fileLocation);
        ArrayList<CompoundMark> myMarks = courseCreator.getCompoundMarks();
        Course raceCourse = new Course("Kevin", myMarks);


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
                gc.fillRect(0, 0, 16, 16);

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
}
