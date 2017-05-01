package seng302.Controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import seng302.*;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Class that controls the race window and updates the race as it proceeds
 */
public class RaceController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private AnchorPane viewAnchorPane;
    @FXML
    private Group group;
    @FXML
    private Label clock;
    @FXML
    private ListView<String> finishedListView;
    @FXML
    private TableView<Boat> positionTable;
    @FXML
    private TableColumn<Boat, Integer> positionCol;
    @FXML
    private TableColumn<Boat, String> nameCol;
    @FXML
    private TableColumn<Boat, Integer> speedCol;
    @FXML
    private Label fpsLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button annotationBtn;
    @FXML
    private Button fpsBtn;

    private Race race;

    private List<Pane> boats = new ArrayList<>();
    private List<Rectangle> landmarks = new ArrayList<>();
    private List<Line> gates = new ArrayList<>();
    private List<List<Point2D>> absolutePaths = new ArrayList<>();
    private List<Double> lastHeadings = new ArrayList<>();
    private Polygon boundary = new Polygon();
    private Polyline windArrow = new Polyline();
    private boolean showName = true;
    private boolean showSpeed = true;
    private boolean showFPS = true;
    private List<Path> paths = new ArrayList<>();

    private int timeBeforeRace = 5;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = -timeBeforeRace - 1;
    private long lastTime = 0;
    private long timerUpdate = 1000;
    private boolean raceStarted = false;
    private boolean countingDown = false;
    private int frameCount = 0;

    /**
     * initializes the race display.
     */
    @FXML
    public void initialize() {
        race = new Race();

        finishedListView = new ListView<>();
        boundary = getBoundary(race);
        group.getChildren().add(boundary);

        //Initialises boats
        for (int i = 0; i < race.getBoats().size(); i++) {
            Pane stack = new Pane();
            Text text = new Text();
            Polyline boatSprite = new Polyline();
            Polyline wake = new Polyline();

            //creates a boat sprite
            boatSprite.getPoints().addAll(0.0, 0.0,
                    0.0, -8.0,
                    0.0, 8.0,
                    -5.0, 8.0,
                    0.0, -8.0,
                    5.0, 8.0,
                    -5.0, 8.0);

            wake.getPoints().addAll(0.0, 0.0,
                    0.0, 1.0);

            Circle tc = new Circle(2);
            tc.setCenterX(0);
            tc.setCenterY(0);
            wake.setStroke(Color.CYAN);
            text.relocate(155,2);
            text.setTextAlignment(TextAlignment.RIGHT);
            boatSprite.setStroke(race.getBoats().get(i).getColour());
            stack.getChildren().add(boatSprite);
            stack.getChildren().add(text);
            stack.getChildren().add(wake);
            stack.getChildren().add(tc);
            boats.add(stack);

            Path path = new Path();
            path.setStroke(race.getBoats().get(i).getColour());
            path.getElements().add(new MoveTo(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
            path.setFill(Color.TRANSPARENT);
            paths.add(path);
            absolutePaths.add(new ArrayList<>());

            lastHeadings.add(race.getBoats().get(i).getHeading() / 2);  // guarantee its different
        }

        mainBorderPane.setLeft(positionTable);
        mainBorderPane.setCenter(viewAnchorPane);

        // set the data types for the table columns.
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));

        //Initialises landmarks
        for (Landmark lm : race.getLandmarks()) {
            for (Position pos : lm.getPositions()){
                Rectangle square = new Rectangle(10, 10, lm.getColor());
                landmarks.add(square);
            }
        }

        //Initialises gates
        for (int i = 0; i < race.getGates().size(); i++) {
            Landmark gate = race.getGates().get(i);
            Position gateHead = gate.getPositions().get(0);
            Position gateTail = gate.getPositions().get(1);
            Line line = new Line(gateHead.getX(), gateHead.getY(), gateTail.getX(), gateTail.getY());
            line.setStrokeWidth(3);
            gates.add(line);
        }

        for (Path path: paths) {
            group.getChildren().add(path);
        }

        //Initialises wind arrow
        windArrow.getPoints().addAll(0.0, 0.0,
                -5.0, 15.0,
                5.0, 15.0,
                0.0, 0.0,
                0.0, 30.0);
        windArrow.setScaleX(2);
        windArrow.setScaleY(2);
        group.getChildren().add(windArrow);
        windArrow.setStroke(Color.BLACK);

        //Initialises race clock
        clock.setFont(new Font("Arial", 30));
        clock.setText(" 00:00:00");
        clock.setVisible(true);

        group.getChildren().addAll(gates);
        group.getChildren().addAll(landmarks);
        group.getChildren().addAll(boats);

        runRace();
    }

    /**
     * Generates a line used for a wake
     * @param speed speed of the boat
     * @return a polyline that can be used as a wake.
     */
    private Polyline newWake(double speed){
        Polyline wake = new Polyline();
        wake.getPoints().addAll(0.0, -speed,
                0.0, speed);
        wake.setStroke(Color.CYAN);
        return wake;
    }

    /**
     * Generates the boundary to be displayed around the race course
     * @param race the race being run
     * @return a polygon object of the race boundary
     */
    private Polygon getBoundary(Race race){
        Polygon boundary = new Polygon();
        for (int i=0; i<race.getBoundaries().size(); i++) {
            boundary.getPoints().add(Coordinate.getRelativeX(race.getBoundaries().get(i).getX()));
            boundary.getPoints().add(Coordinate.getRelativeY(race.getBoundaries().get(i).getY()));
        }
        boundary.setFill(Color.LIGHTBLUE);
        return boundary;
    }

    /**
     * When the startButton is pressed, this method is called.
     * Starts the race by changing the countingDown boolean to true
     * Hides the startButton after being pressed
     */
    public void startButtonPressed() {
        countingDown = true;
        startButton.setVisible(false);
    }

    /**
     * When the annotationBtn is clicked, this method is called.
     * Changes the boolean values for showSpeed and showName, hence changing the visibility of the annotations.
     */
    @FXML
    private void annotationBtnClicked(ActionEvent event) {

        if (showSpeed) {
            annotationBtn.setText("Show Annotations");
        } else {
            annotationBtn.setText("Remove Annotations");
        }
        showSpeed = !showSpeed;
        showName = !showName;
    }

    /**
     * Called when the fpsBtn is clicked.
     * Changes the showFPS boolean to toggle the visibility of the FPS label.
     */
    @FXML
    private void fpsBtnClicked(ActionEvent event) {
        showFPS = !showFPS;
        fpsLabel.setVisible(showFPS);
        if (showFPS) {
            fpsBtn.setText("Remove FPS counter");
        } else {
            fpsBtn.setText("Show FPS counter");
        }
    }

    /**
     * Updates the Raceview window, and the coordinates of the nodes within the pane.
     * Keeps the boats in the correct position as they move around the course.
     * If the window is resized, the objects will keep a relatively similar distribution
     * across the new window size.
     */
    private void updateView() {
        Coordinate.updateBorder();
        for (int i = 0; i < boats.size(); i++) {
            double boatSpeed = race.getBoats().get(i).getSpeed();
            String speed = "";
            String name = "";
            if (showSpeed) {
                speed = String.valueOf(boatSpeed) + " m/s";
            }
            if (showName) {
                name = race.getBoats().get(i).getAbrv();
            }
            boats.get(i).setLayoutX(Coordinate.getRelativeX(race.getBoats().get(i).getX()));
            boats.get(i).setLayoutY(Coordinate.getRelativeY(race.getBoats().get(i).getY()));
            boats.get(i).getChildren().get(0).setRotate(race.getBoats().get(i).getHeading()); //Sets rotation of boat
            boats.get(i).getChildren().remove(2); //Sets the length of wake
            boats.get(i).getChildren().add(2, newWake(boatSpeed));
            boats.get(i).getChildren().get(2).setRotate(race.getBoats().get(i).getHeading()); //Sets rotation of wake
            boats.get(i).getChildren().get(2).setLayoutX((8 + boatSpeed) * Math.sin(-Math.toRadians(race.getBoats().get(i).getHeading())));
            boats.get(i).getChildren().get(2).setLayoutY((8 + boatSpeed) * Math.cos(-Math.toRadians(race.getBoats().get(i).getHeading())));
            boats.get(i).getChildren().set(1, new Text(name + " " + speed));
            boats.get(i).getChildren().get(1).setTranslateX(10);
            boats.get(i).getChildren().get(1).setTranslateY(0);


            if (!lastHeadings.get(i).equals(race.getBoats().get(i).getHeading())) {
                absolutePaths.get(i).add(new Point2D(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
                paths.get(i).getElements().add(new LineTo());
                lastHeadings.set(i, race.getBoats().get(i).getHeading());
            } else {
                absolutePaths.get(i).set(absolutePaths.get(i).size() - 1, new Point2D(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
                paths.get(i).getElements().set(paths.get(i).getElements().size() - 1, new LineTo(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
            }

            ((MoveTo) paths.get(i).getElements().get(0)).setX(Coordinate.getRelativeX(race.getLegs().get(0).getStart().getX()));
            ((MoveTo) paths.get(i).getElements().get(0)).setY(Coordinate.getRelativeY(race.getLegs().get(0).getStart().getY()));
            for (int j = 1; j < paths.get(i).getElements().size(); j++) {
                ((LineTo) paths.get(i).getElements().get(j)).setX(Coordinate.getRelativeX(absolutePaths.get(i).get(j - 1).getX()));
                ((LineTo) paths.get(i).getElements().get(j)).setY(Coordinate.getRelativeY(absolutePaths.get(i).get(j - 1).getY()));
            }
        }

        ArrayList<Position> positions = new ArrayList<>();
        for (Landmark lm : race.getLandmarks()){
            for (Position pos : lm.getPositions()){
                positions.add(pos);
            }
        }


        for (int i = 0; i < landmarks.size(); i++) {
            landmarks.get(i).setX(Coordinate.getRelativeX(positions.get(i).getX()) - landmarks.get(i).getWidth() / 2);
            landmarks.get(i).setY(Coordinate.getRelativeY(positions.get(i).getY()) - landmarks.get(i).getHeight() / 2);
        }

        for (int i = 0; i < gates.size(); i++) {
            gates.get(i).setStartX(Coordinate.getRelativeX(race.getGates().get(i).getPositions().get(0).getX()));
            gates.get(i).setStartY(Coordinate.getRelativeY(race.getGates().get(i).getPositions().get(0).getY()));
            gates.get(i).setEndX(Coordinate.getRelativeX(race.getGates().get(i).getPositions().get(1).getX()));
            gates.get(i).setEndY(Coordinate.getRelativeY(race.getGates().get(i).getPositions().get(1).getY()));
        }

        windArrow.setLayoutX(50);
        windArrow.setLayoutY(50);
        windArrow.setRotate(race.getWindHeading());

        if (showFPS) {
            fpsLabel.setLayoutX(Coordinate.getWindowX() - 90);
            fpsLabel.setLayoutY(60);
        }

        startButton.setLayoutX(14);
        startButton.setLayoutY(Coordinate.getWindowY() - 100);

        fpsBtn.setLayoutX(14);
        fpsBtn.setLayoutY(Coordinate.getWindowY() - 125);

        annotationBtn.setLayoutX(14);
        annotationBtn.setLayoutY(Coordinate.getWindowY() - 150);

        clock.setLayoutX(Coordinate.getWindowX() - 160);
        clock.setLayoutY(20);

        updateBoundary();
    }

    /**
     Updates the course boundaries on the view
     */
    private void updateBoundary(){
        for (int i=0; i<race.getBoundaries().size(); i++) {
            boundary.getPoints().set(2*i, Coordinate.getRelativeX(race.getBoundaries().get(i).getX()));
            boundary.getPoints().set(2*i + 1, Coordinate.getRelativeY(race.getBoundaries().get(i).getY()));
        }
    }

    /**
     * Increments the race clock, and updates the fps display
     */
    private void updateClock() {
        if (currentTimeMillis() - lastTime >= timerUpdate) {
            seconds++;
            fpsLabel.setText(frameCount + " fps");
            frameCount = 0;
            if ((seconds / 60) >= 1) {
                seconds = 0;
                minutes++;
                if ((minutes / 60) >= 1) {
                    minutes = 0;
                    hours++;
                }
            }
            if (seconds < 0) {
                clock.setText(String.format("-%02d:%02d:%02d", hours, minutes, -seconds));
            } else {
                clock.setText(String.format(" %02d:%02d:%02d", hours, minutes, seconds));
            }
            lastTime = currentTimeMillis();
        }
    }

    /**
     * Initiates an animationTimer to start the race in which the boats will participate. This will count down from the
     * timeToStart countdown, then start the boats racing. Keeps the window updated with calls to
     * updateView. Also displays the current position that the boats are in
     */
    private void runRace() {
        updateView();

        new AnimationTimer() {
            Message message = new Message();
            @Override
            public void handle(long currentNanoTime) {
                frameCount++;
                updateView();

                if (raceStarted) {
                    race.updateBoats();
                    List<List<Byte>> boatsBinList
                    for (Boat boat : race.getBoats()) {
                        List<Byte> boatsBin = message.boatPositionMessage(boat);
                    }

                    Coordinate.updateBorder();
                    updateClock();
                }
                else if (countingDown){
                    if (currentTimeMillis() - lastTime >= timerUpdate) {
                        seconds++;
                        frameCount = 0;
                        clock.setText(String.format("-%02d:%02d:%02d", hours, minutes, -seconds));
                        lastTime = currentTimeMillis();
                    }
                    if (seconds == 0) {
                        clock.setText(String.format(" %02d:%02d:%02d", hours, minutes, seconds));
                        raceStarted = true;
                    }
                }
                if (race.finished) {
                    positionTable.setVisible(false);
                    mainBorderPane.setLeft(finishedListView);
                    finishedListView.setVisible(true);
                    finishedListView.setItems(race.getPositionStrings());

                } else {
                    positionTable.setItems(race.getCurrentOrder());
                }
            }
        }.start();
    }
}
