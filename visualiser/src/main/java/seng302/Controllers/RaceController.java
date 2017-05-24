package seng302.Controllers;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import seng302.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

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
    private Label localTimeZone;
    @FXML
    private Label localTime;
    @FXML
    private ListView<String> finishedListView;
    @FXML
    private TableView<Boat> positionTable;
    @FXML
    private TableColumn<Boat, Integer> positionCol;
    @FXML
    private TableColumn<Boat, String> nameCol;
    @FXML
    private TableColumn<Boat, String> speedCol;
    @FXML
    private Label fpsLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button annotationBtn;
    @FXML
    private Button fpsBtn;
    @FXML
    private Button resetViewButton;
    @FXML
    private ListView<String> startersList;

    @FXML private CheckBox BoatNameCheckBox;
    @FXML private CheckBox BoatSpeedCheckBox;

    private Race race;

    private List<Pane> boats = new ArrayList<>();
    private List<Rectangle> compoundMarks = new ArrayList<>();
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
    private int raceHours = 0;
    private int raceMinutes = 0;
    private int raceSeconds = -timeBeforeRace - 1;
    private long lastTime = 0;
    private long timerUpdate = 1000;
    private boolean raceStarted = false;
    private boolean countingDown = false;
    private int frameCount = 0;

    private TimeZoneWrapper timeZoneWrapper;
    private final ImageView selectedImage = new ImageView();

    /**
     * initializes the race display.
     */
    @FXML
    public void initialize() {
        race = new Race();

        Thread serverThread = new Thread(() -> {
            StreamClient client = new StreamClient(race);
            client.connect();
            client.retrieveData();
        });
        serverThread.start();

        while (!race.isRaceReady()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        selectedImage.setPreserveRatio(true);
        Image image = drawMap(String.valueOf(race.getMapCenter().getLatitude()), String.valueOf(race.getMapCenter().getLongitude()));
//        System.out.println(String.valueOf(race.getMapCenter().getLatitude()) + ", " + String.valueOf(race.getMapCenter().getLongitude()));
        selectedImage.setImage(image);
        group.getChildren().add(selectedImage);

        // handles zooming when a boat is selected
        viewAnchorPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (resetViewButton.visibleProperty().get()) {
                    if (event.getDeltaY() < 0) {
                       Coordinate.decreaseZoom();
                    }
                    if (event.getDeltaY() > 0) {
                       Coordinate.increaseZoom();
                    }
                }
            }
        });

        //Where should we put this?
        this.timeZoneWrapper = new TimeZoneWrapper("Atlantic/Bermuda");
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
            boatSprite.setStroke(race.getBoats().get(i).getColour().desaturate().desaturate());
            boatSprite.setFill(race.getBoats().get(i).getColour().saturate().saturate());
            boatSprite.setId(Integer.toString(i));

            // Used when selecting a boat to follow
            boatSprite.onMousePressedProperty().setValue(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    race.setBoatToFollow(race.getBoats().get(Integer.parseInt(boatSprite.getId())));
                    resetViewButton.setVisible(true);
                }
            });
            // to give the user more space to click on the boat
            tc.onMousePressedProperty().setValue(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    race.setBoatToFollow(race.getBoats().get(Integer.parseInt(boatSprite.getId())));
                    resetViewButton.setVisible(true);
                }
            });

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

            lastHeadings.add(race.getBoats().get(i).getHeading() + 21);  // guarantee its different
        }

        mainBorderPane.setLeft(positionTable);
        mainBorderPane.setCenter(viewAnchorPane);

        // set the data types for the table columns.
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Boat, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Boat, String> p) {
                String speed = String.valueOf(p.getValue().getSpeed());
                return new ReadOnlyObjectWrapper<String>(speed);
            }
        });

        //Initialises compoundMarks
        for (CompoundMark lm : race.getCompoundMarks()) {
            for (Mark pos : lm.getMarks()){
                Rectangle square = new Rectangle(10, 10, lm.getColor());
                compoundMarks.add(square);
            }
        }

        //Initialises gates
        for (int i = 0; i < race.getGates().size(); i++) {
            CompoundMark gate = race.getGates().get(i);
            Mark gateHead = gate.getMarks().get(0);
            Mark gateTail = gate.getMarks().get(1);
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

        //Initialise time zone
        localTimeZone.setFont(new Font("Arial", 15));
        localTimeZone.setText(timeZoneWrapper.getRaceTimeZoneString());
        localTimeZone.setVisible(true);

        localTime.setFont(new Font("Arial", 15));
        localTime.setVisible(true);

        group.getChildren().addAll(gates);
        group.getChildren().addAll(compoundMarks);
        group.getChildren().addAll(boats);

//        RacersListBeforeStart(race);

        runRace();
    }

    /**
     * draws the google maps image onto the screen
     */
    private Image drawMap(String centerLat, String centerLon) {
        String key = "AIzaSyAAmj8rsEdHfH4WbXbqB4ugZEKVBrvCyaw";
        int zoom = 12;
        int sizeH = 640;
        int sizeV = 640;
//        String imageUrl = String.format("https://maps.googleapis.com/maps/api/staticmap?" +
//                "center=%s,%s&size=%dx%d&scale=2&zoom=%d&key=%s", centerLat, centerLon, sizeH, sizeV, zoom, key);

        StringBuilder sb = new StringBuilder("visible=");
        for (Mark cl: race.getBoundaries()) {
            sb.append(cl.getLatitude());
            sb.append(',');
            sb.append(cl.getLongitude());
            sb.append('|');
        }
        sb.setLength(sb.length() - 1);
        String imageUrl = String.format("https://maps.googleapis.com/maps/api/staticmap?" +
                "center=%s,%s&size=%dx%d&%s&scale=2&key=%s", centerLat, centerLon, sizeH, sizeV, sb.toString(), key);

//        System.out.println("imageURL: " + imageUrl);
//        System.out.println("ez: " + viewAnchorPane.getBackground());
//        BackgroundImage backgroundImage = new BackgroundImage(new Image(imageUrl),
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT);
//        viewAnchorPane.setBackground(new Background(backgroundImage));
        return new Image(imageUrl);
    }

    private void RacersListBeforeStart(Race race) {
        startersList.setVisible(true);
        ObservableList<String> listOfStarters = FXCollections.observableArrayList();
        for(Boat boat : race.getBoats()) {
            String name = boat.getName();
            listOfStarters.add(name);
        }
        startersList.setItems(listOfStarters);
    }

    private void RemoveRacersList() {
        startersList.setVisible(false);
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
     * resets the view back to its original state
     */
    public void resetViewButtonPressed() {
        race.resetZoom();
        Coordinate.setZoom(0);
        resetViewButton.setVisible(false);
    }

    /**
     * When the annotationBtn is clicked, this method is called.
     * Changes the boolean values for showSpeed and showName, hence changing the visibility of the annotations.
     */
    @FXML
    private void annotationBtnClicked(ActionEvent event) {

        if (BoatNameCheckBox.isSelected() && BoatSpeedCheckBox.isSelected()) {
            annotationBtn.setText("Show Annotations");

            BoatNameCheckBox.setSelected(false);
            showName = false;

            BoatSpeedCheckBox.setSelected(false);
            showSpeed = false;

        } else {
            annotationBtn.setText("Remove Annotations");

            BoatNameCheckBox.setSelected(true);
            showName = true;
            BoatSpeedCheckBox.setSelected(true);
            showSpeed = true;
        }
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
     * Toggles the Boat Name annotation when the boat name checkbox is clicked
     */
    @FXML
    private void ToggleBoatNameAnnotation() {
        if (BoatNameCheckBox.isSelected()) {
            showName = true;
        } else {
            showName = false;
        }

        if (BoatNameCheckBox.isSelected() && BoatSpeedCheckBox.isSelected()) {
            annotationBtn.setText("Remove Annotations");
        } else {
            annotationBtn.setText("Show Annotations");
        }
    }

    /**
     * Toggles the Boat Speed annotation when the boat speed checkbox is clicked
     */
    @FXML
    private void ToggleBoatSpeedAnnotation() {
        if (BoatSpeedCheckBox.isSelected()) {
            showSpeed = true;
        } else {
            showSpeed = false;
        }

        if (BoatNameCheckBox.isSelected() && BoatSpeedCheckBox.isSelected()) {
            annotationBtn.setText("Remove Annotations");
        } else {
            annotationBtn.setText("Show Annotations");
        }
    }

    /**
     * Updates the Raceview window, and the coordinates of the nodes within the pane.
     * Keeps the boats in the correct position as they move around the course.
     * If the window is resized, the objects will keep a relatively similar distribution
     * across the new window size.
     */
    private void updateView() {
        Coordinate.setOffset(race.calculateOffset());
        Coordinate.updateViewCoordinates();

        positionTable.refresh();

        viewAnchorPane.setMinHeight(Coordinate.getWindowY());
        viewAnchorPane.setMaxHeight(Coordinate.getWindowY());
        viewAnchorPane.setMinWidth(Coordinate.getWindowX());
        viewAnchorPane.setMaxWidth(Coordinate.getWindowX());

        Coordinate.updateBorder();
        viewAnchorPane.setMinHeight(Coordinate.getWindowY());
        viewAnchorPane.setMaxHeight(Coordinate.getWindowY());
        viewAnchorPane.setMinWidth(Coordinate.getWindowX());
        viewAnchorPane.setMaxWidth(Coordinate.getWindowX());


        for (int i = 0; i < absolutePaths.size(); i++) {
            if (absolutePaths.get(i).size() > 500) {
                paths.get(i).getElements().remove(1);
                absolutePaths.get(i).remove(0);
            }
        }


        for (int i = 0; i < boats.size(); i++) {

            double boatSpeed = race.getBoats().get(i).getSpeed();
            String speed = "";
            String name = "";
            if (showSpeed) {
                speed = String.valueOf(boatSpeed) + " knots";
            }
            if (showName) {
                name = race.getBoats().get(i).getShortName();
            }

            boats.get(i).setLayoutX(Coordinate.getRelativeX(race.getBoats().get(i).getX()));
            boats.get(i).setLayoutY(Coordinate.getRelativeY(race.getBoats().get(i).getY()));
            updateNodeScale(boats.get(i).getChildren().get(0));
            boats.get(i).getChildren().get(0).setRotate(race.getBoats().get(i).getHeading()); //Sets rotation of boat

            if(!raceStarted){
                boats.get(i).getChildren().set(2, new Polyline());
            } else {
                boats.get(i).getChildren().set(2, newWake(boatSpeed));
            }

            updateNodeScale(boats.get(i).getChildren().get(2));

            boats.get(i).getChildren().get(2).setRotate(race.getBoats().get(i).getHeading()); //Sets rotation of wake
            boats.get(i).getChildren().get(2).setLayoutX(((9 + boatSpeed) *(1/(1+Coordinate.getZoom()*0.9))) * Math.sin(-Math.toRadians(race.getBoats().get(i).getHeading())));
            boats.get(i).getChildren().get(2).setLayoutY(((9 + boatSpeed) *(1/(1+Coordinate.getZoom()*0.9))) * Math.cos(-Math.toRadians(race.getBoats().get(i).getHeading())));
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


            ((MoveTo) paths.get(i).getElements().get(0)).setX(Coordinate.getRelativeX(absolutePaths.get(i).get(0).getX()));
            ((MoveTo) paths.get(i).getElements().get(0)).setY(Coordinate.getRelativeY(absolutePaths.get(i).get(0).getY()));
            for (int j = 1; j < paths.get(i).getElements().size(); j++) {
                ((LineTo) paths.get(i).getElements().get(j)).setX(Coordinate.getRelativeX(absolutePaths.get(i).get(j - 1).getX()));
                ((LineTo) paths.get(i).getElements().get(j)).setY(Coordinate.getRelativeY(absolutePaths.get(i).get(j - 1).getY()));
            }


        }

        ArrayList<Mark> marks = new ArrayList<>();
        for (CompoundMark lm : race.getCompoundMarks()){
            for (Mark pos : lm.getMarks()){
                marks.add(pos);
            }
        }

        for (int i = 0; i < compoundMarks.size(); i++) {
            compoundMarks.get(i).setX(Coordinate.getRelativeX(marks.get(i).getX()) - compoundMarks.get(i).getWidth() / 2);
            compoundMarks.get(i).setY(Coordinate.getRelativeY(marks.get(i).getY()) - compoundMarks.get(i).getHeight() / 2);
            updateNodeScale(compoundMarks.get(i));
        }

        for (int i = 0; i < gates.size(); i++) {
            gates.get(i).setStartX(Coordinate.getRelativeX(race.getGates().get(i).getMarks().get(0).getX()));
            gates.get(i).setStartY(Coordinate.getRelativeY(race.getGates().get(i).getMarks().get(0).getY()));
            gates.get(i).setEndX(Coordinate.getRelativeX(race.getGates().get(i).getMarks().get(1).getX()));
            gates.get(i).setEndY(Coordinate.getRelativeY(race.getGates().get(i).getMarks().get(1).getY()));
        }

        windArrow.setLayoutX(50);
        windArrow.setLayoutY(50);
        windArrow.setRotate(race.getWindHeading());

        if (showFPS) {
            fpsLabel.setLayoutX(Coordinate.getWindowX() - 90);
            fpsLabel.setLayoutY(60);
        }

        resetViewButton.setLayoutX(14);
        resetViewButton.setLayoutY(Coordinate.getWindowY() - 75);

        startButton.setLayoutX(14);
        startButton.setLayoutY(Coordinate.getWindowY() - 100);

        fpsBtn.setLayoutX(14);
        fpsBtn.setLayoutY(Coordinate.getWindowY() - 125);

        annotationBtn.setLayoutX(14);
        annotationBtn.setLayoutY(Coordinate.getWindowY() - 150);

        BoatNameCheckBox.setLayoutX(14);
        BoatNameCheckBox.setLayoutY(Coordinate.getWindowY() - 275);

        BoatSpeedCheckBox.setLayoutX(14);
        BoatSpeedCheckBox.setLayoutY(Coordinate.getWindowY() - 250);

        clock.setLayoutY(20);
        clock.setLayoutX(Coordinate.getWindowX() - 155);

        localTimeZone.setLayoutX(Coordinate.getWindowX() - 115);
        localTimeZone.setLayoutY(80);

        localTime.setLayoutX(Coordinate.getWindowX() - 110);
        localTime.setLayoutY(100);
        localTime.setText(timeZoneWrapper.getLocalTimeString());

        startersList.setPrefSize(Coordinate.getWindowX() - 400, Coordinate.getWindowY() - 200);

        startersList.setLayoutX(200);
        startersList.setLayoutY(100);

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
    private void updateRaceClock() {
        if (currentTimeMillis() - lastTime >= timerUpdate) {
            raceSeconds++;
            fpsLabel.setText(frameCount + " fps");
            frameCount = 0;
            if ((raceSeconds / 60) >= 1) {
                raceSeconds = 0;
                raceMinutes++;
                if ((raceMinutes / 60) >= 1) {
                    raceMinutes = 0;
                    raceHours++;
                }
            }
            if (raceSeconds < 0) {
                clock.setText(String.format("-%02d:%02d:%02d", raceHours, raceMinutes, -raceSeconds));
            } else {
                clock.setText(String.format(" %02d:%02d:%02d", raceHours, raceMinutes, raceSeconds));
            }
            lastTime = currentTimeMillis();
        }
    }

    /**
     * Given the bat source ID, will update the position of that boat as it is displayed in the GUi.
     * @param sourceID the key for the boat in te map of boats.
     */
    private void updateBoatPosition(int sourceID) {

    }

    /**
     * @param nodeToScale node to scale based on current level of zoom
     */
    private void updateNodeScale(Node nodeToScale) {
        nodeToScale.setScaleX(1/(1+Coordinate.getZoom()*0.9));
        nodeToScale.setScaleY(1/(1+Coordinate.getZoom()*0.9));
    }

    /**
     * Initiates an animationTimer to start the race in which the boats will participate. This will count down from the
     * timeToStart countdown, then start the boats racing. Keeps the window updated with calls to
     * updateView. Also displays the current position that the boats are in
     */
    private void runRace() {
        updateView();

        new AnimationTimer() {
            //Message message = new Message();
            @Override
            public void handle(long currentNanoTime) {
                frameCount++;

//                for (Path path : paths){
//                    if (path.getElements().size() >= 30) {
//                        path.getElements().remove(0);
//                    }
//                }

//                for (int i = 0; i < absolutePaths.size(); i++){
//                    if (paths.get(i).getElements().size() >= 30) {
//                        paths.get(i).getElements().remove(0);
//                        absolutePaths.get(i).remove(0);
//                    }
//                }

                updateView();

//                if (paths != null) {
//                    System.out.println(paths.get(i).getElements().size());
//                    if (paths.get(i).getElements().size() >= 200) {
//                        paths.get(i).getElements().remove(0);
//                    }
//                    System.out.println(paths.get(i).getElements().size());
//                }

                if (raceStarted) {
//                    race.updateBoats();

                    Coordinate.updateBorder();
                    updateRaceClock();
                }
                else if (countingDown){
                    if (currentTimeMillis() - lastTime >= timerUpdate) {
                        raceSeconds++;
                        frameCount = 0;
                        clock.setText(String.format("-%02d:%02d:%02d", raceHours, raceMinutes, -raceSeconds));
                        if (raceSeconds == -2) {
                            RemoveRacersList();
                        }
                        lastTime = currentTimeMillis();
                    }
                    if (raceSeconds == 0) {
                        clock.setText(String.format(" %02d:%02d:%02d", raceHours, raceMinutes, raceSeconds));
                        raceStarted = true;
                    }
                }
                if (race.finished) {
                    positionTable.setVisible(false);
                    mainBorderPane.setLeft(finishedListView);
                    finishedListView.setVisible(true);
                    finishedListView.setItems(race.getPositionStrings());

                } else {
                    positionTable.setItems(FXCollections.observableArrayList(race.getBoats()));
                }
            }
        }.start();
    }
}
