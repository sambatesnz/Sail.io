package seng302.Controllers;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import seng302.Client.Client;
import seng302.Race.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.Mark;
import seng302.Visualiser.BoatSprite;
import seng302.Visualiser.FPSCounter;
import seng302.Visualiser.WindArrow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private Group boundaryGroup;
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
    private TableColumn<Boat, String> positionCol;
    @FXML
    private TableColumn<Boat, String> nameCol;
    @FXML
    private TableColumn<Boat, String> speedCol;
    @FXML
    private Label fpsLabel;
    @FXML
    private Button annotationBtn;
    @FXML
    private Button fpsBtn;
    @FXML
    private Button resetViewButton;
    @FXML
    private ListView<String> startersList;
    @FXML
    private LineChart<Number, Number> sparklinesChart;
    @FXML
    private SplitPane sidePanelSplit;

    @FXML private CheckBox BoatNameCheckBox;
    @FXML private CheckBox BoatSpeedCheckBox;

    private Race race;

    private List<Pane> boats = new ArrayList<>();
    private List<Rectangle> compoundMarks = new ArrayList<>();
    private List<Line> gates = new ArrayList<>();
    private List<List<Point2D>> absolutePaths = new ArrayList<>();
    private List<Double> lastHeadings = new ArrayList<>();
    private Polygon boundary = new Polygon();
    private WindArrow windArrow = new WindArrow();
    private boolean showName = true;
    private boolean showSpeed = true;
    private boolean showFPS = true;
    private List<Path> paths = new ArrayList<>();
    private Boat boatToFollow;
    private Boat centerOfScreen;
    private int raceHours = 0;
    private int raceMinutes = 0;
    private int raceSeconds = 0;
    private long lastTime = 0;
    private long timerUpdate = 1000000000;
    private boolean raceStarted = false;
    private int frameCount = 0;
    private int viewUpdateCount = 0;
    private double windowWidth = 0;
    private double windowHeight = 0;

    private TimeZoneWrapper timeZoneWrapper;
    private final ImageView selectedImage = new ImageView();
    private Mark imagePos;
    private final double IMAGE_SCALE = 1.7;

    // Sparkline variables
    @FXML    private NumberAxis xAxis;
    @FXML    private NumberAxis yAxis;
    private ObservableList<XYChart.Series<Number, Number>> seriesList;
    private Integer secondCounter = 1;
    private Integer sparkCounter = 0;
    private List<Boat> sortedBoats;
    private List<Boat> otherSortedBoats;
    private int EARTH_RADIUS = 6371;
    private int METERS_CONVERSION = 1000;
    private final int SPARKLINEHEIGHT = 239;

    private FPSCounter fpsCounter;


    private boolean boatMetaDataInitialised = false;
    private boolean boatLocationDataInitialised = false;
    private boolean viewInitialised = false;


    /**
     * initializes the race display.
     */
    @FXML
    public void initialize() {
        race = new Race();



        Thread serverThread = new Thread(() -> {
            Client client = new Client(race);
            client.connect();
            client.processStreams();
        });
        serverThread.start();


        mainBorderPane.setLeft(sidePanelSplit);
        mainBorderPane.setCenter(viewAnchorPane);


        group.getChildren().add(windArrow);

        clock.setFont(new Font("Arial", 30));
        clock.setText(" 00:00:00");
        clock.setVisible(true);

        fpsCounter = new FPSCounter(fpsLabel);


        resetViewButton.setLayoutX(14);
        resetViewButton.setLayoutY(Coordinate.getWindowHeightY() - 100);
        resetViewButton.setVisible(true);
        resetViewButton.setDisable(true);

        initialisePositionsTable();
        enableScrolling();

        startRaceListener();
    }

    private void enableScrolling() {
        viewAnchorPane.setOnScroll(event -> {
            if (Coordinate.isTrackingBoat()) {
                if (event.getDeltaY() < 0) {
                    Coordinate.decreaseZoom();
                }
                if (event.getDeltaY() > 0) {
                    Coordinate.increaseZoom();
                }
            }
        });
    }

    private void initialisePositionsTable() {
        positionCol.setCellValueFactory(p -> {
            String pos = String.valueOf(p.getValue().getPosition());
            return new ReadOnlyObjectWrapper<>(pos);
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedCol.setCellValueFactory(p -> {
            String speed = String.valueOf(p.getValue().getSpeedInKnots());
            return new ReadOnlyObjectWrapper<>(speed);
        });
    }

    private void startRaceListener() {
        new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                rotateWindArrow();
                scaleWindArrow();
                setUTC();
                updateClock();
                fpsCounter.update(currentNanoTime);
                setViewParameters();

                updateViewLayout();

                initialiseBoatMetaData();
                initialiseBoatLocation();
                updateCourseLayout();
                updateBoundary();

                if (race.isRaceReady() && boatLocationDataInitialised) {
                    updateBoatPositions();
                    updateBoatPaths();

                }
                viewUpdateCount++;

                if (race.isRaceReady() && fpsCounter.getFrameCount() % 30 == 0){
                    positionTable.refresh();
                    positionTable.setItems(FXCollections.observableArrayList(race.getBoats()));
                    positionTable.setPrefHeight(Coordinate.getWindowHeightY() - SPARKLINEHEIGHT);
                }

                if (sparkCounter > 100 && race.started()) {
                    sparkCounter = 0;
                    updateSparkLineChart();
                }
                sparkCounter++;

            }
        }.start();

    }

    private void updateBoatPositions() {
        for (int i = 0; i < boats.size(); i++) {
            if(race.getBoats().get(i).isKnowsBoatLocation()) {
                double boatSpeed = race.getBoats().get(i).getSpeed()/1000;
                String speed = "";
                String name = "";
                if (showSpeed) {
                    speed = String.valueOf(race.getBoats().get(i).getSpeedInKnots()) + " knots";
                }
                if (showName) {
                    name = race.getBoats().get(i).getShortName();
                }
                //Position of boat, wake and annotations.
                boats.get(i).setLayoutX(Coordinate.getRelativeX(race.getBoats().get(i).getX()));
                boats.get(i).setLayoutY(Coordinate.getRelativeY(race.getBoats().get(i).getY()));
                updateNodeScale(boats.get(i).getChildren().get(0));
                boats.get(i).getChildren().get(0).setRotate(race.getBoats().get(i).getHeading());

                // Temporary hard coding to differentiate between the boat in user control
                if (race.getBoats().get(i).getSourceId() == 103) {
                    updateNodeScale(boats.get(i).getChildren().get(4));
                }

                //Boats wake
                boats.get(i).getChildren().set(2, newWake(boatSpeed));
                updateNodeScale(boats.get(i).getChildren().get(2));
                boats.get(i).getChildren().get(2).setRotate(race.getBoats().get(i).getHeading());
                boats.get(i).getChildren().get(2).setLayoutX(((9 + boatSpeed) * (1 / (1 + Coordinate.getZoom() * 0.9))) * Math.sin(-Math.toRadians(race.getBoats().get(i).getHeading())));
                boats.get(i).getChildren().get(2).setLayoutY(((9 + boatSpeed) * (1 / (1 + Coordinate.getZoom() * 0.9))) * Math.cos(-Math.toRadians(race.getBoats().get(i).getHeading())));

                //Boat annotations (name and speed)
                boats.get(i).getChildren().set(1, new Text(name + " " + speed));
                boats.get(i).getChildren().get(1).setTranslateX(10);
                boats.get(i).getChildren().get(1).setTranslateY(0);
            }
        }
    }

    private void updateBoatPaths(){
        //boat paths
        for (int i = 0; i < boats.size(); i++){
//            if(race.getBoats().get(i).isKnowsBoatLocation()) {
                if (viewUpdateCount % 5 == 1) {
                    if (absolutePaths.get(i).size() > 150) {
                        paths.get(i).getElements().remove(1);
                        absolutePaths.get(i).remove(0);
                    }

//                    if (!lastHeadings.get(i).equals(race.getBoats().get(i).getHeading())) {
                        absolutePaths.get(i).add(new Point2D(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
                        paths.get(i).getElements().add(new LineTo());
                        lastHeadings.set(i, race.getBoats().get(i).getHeading());
//                    } else {
//                        absolutePaths.get(i).set(absolutePaths.get(i).size() - 1,
//                                new Point2D(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
//                        paths.get(i).getElements().set(paths.get(i).getElements().size() - 1,
//                                new LineTo(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
//                    }

                    ((MoveTo) paths.get(i).getElements().get(0))
                            .setX(Coordinate.getRelativeX(absolutePaths.get(i).get(0).getX()));
                    ((MoveTo) paths.get(i).getElements().get(0))
                            .setY(Coordinate.getRelativeY(absolutePaths.get(i).get(0).getY()));
                    for (int j = 1; j < paths.get(i).getElements().size(); j++) {
                        ((LineTo) paths.get(i).getElements().get(j))
                                .setX(Coordinate.getRelativeX(absolutePaths.get(i).get(j - 1).getX()));
                        ((LineTo) paths.get(i).getElements().get(j))
                                .setY(Coordinate.getRelativeY(absolutePaths.get(i).get(j - 1).getY()));
                    }
                }
//            }
        }
    }

    private void initialiseBoatMetaData() {
        if(race.boatsReady() && !boatMetaDataInitialised && race.isRaceReady()){
            for (int i = 0; i < race.getBoats().size(); i++) {
                BoatSprite boatSprite = new BoatSprite(race.getBoats().get(i));
                boats.add(boatSprite.getStack());

//                boatSprite.onMousePressedProperty().setValue(event -> {
//                    boatToFollow = race.getBoats().get(Integer.parseInt(boatSprite.getId()));
//                    resetViewButton.setDisable(false);
//                    Coordinate.setTrackingBoat(true);
//                });
            }
////                Path path = new Path();
////                path.setStroke(race.getBoats().get(i).getColour());
////                path.getElements().add(new MoveTo(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
////                path.setFill(Color.TRANSPARENT);
////                paths.add(path);
////                absolutePaths.add(new ArrayList<>());
////
////                lastHeadings.add(race.getBoats().get(i).getHeading() + 1);  // guarantee its different
//
//            }

            group.getChildren().addAll(boats);
//            for (Path path: paths) {
//                group.getChildren().add(path);
//            }

            createChart();
            boatMetaDataInitialised = true;
        }
    }

    private void initialiseBoatLocation() {
        if(!boatLocationDataInitialised && boatMetaDataInitialised) {
            boolean knowAllLocations = true;
            for (int i = 0; i < race.getBoats().size(); i++) {
                boolean knowsLocation = race.getBoats().get(i).isKnowsBoatLocation();
                if (knowsLocation) {
                    Path path = new Path();
                    path.setStroke(race.getBoats().get(i).getColour());
                    path.getElements().add(new MoveTo(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
                    path.setFill(Color.TRANSPARENT);
                    paths.add(path);
                    absolutePaths.add(new ArrayList<>());

                    lastHeadings.add(race.getBoats().get(i).getHeading());
                } else {
                    knowAllLocations = false;
                }
            }
            if (knowAllLocations) {
                for (Path path : paths) {
                    group.getChildren().add(path);
                }
                boatLocationDataInitialised = true;
            }
        }
    }

    private void updateCourseLayout() {
        if (race.isRaceXMLReceived()){
            updateGates();
            updateMarks();
        }
    }

    private void updateMarks() {
        group.getChildren().removeAll(compoundMarks);
        compoundMarks = new ArrayList<>();
        for (CompoundMark lm : race.getCompoundMarks()) {
            for (int n=0; n < lm.getMarks().size(); n++){
                Rectangle square = new Rectangle(10, 10, lm.getColor());
                compoundMarks.add(square);
            }
        }
        group.getChildren().addAll(compoundMarks);

        ArrayList<Mark> marks = new ArrayList<>();
        for (CompoundMark lm : race.getCompoundMarks()){
            marks.addAll(lm.getMarks());
        }

        for (int i = 0; i < compoundMarks.size(); i++) {
            compoundMarks.get(i).setX(Coordinate.getRelativeX(marks.get(i).getX()) - compoundMarks.get(i).getWidth() / 2);
            compoundMarks.get(i).setY(Coordinate.getRelativeY(marks.get(i).getY()) - compoundMarks.get(i).getHeight() / 2);
            updateNodeScale(compoundMarks.get(i));
        }
    }

    private void updateGates() {
        group.getChildren().removeAll(gates);
        gates = new ArrayList<>();
        for (int i = 0; i < race.getGates().size(); i++) {
            CompoundMark gate = race.getGates().get(i);
            Mark gateHead = gate.getMarks().get(0);
            Mark gateTail = gate.getMarks().get(1);
            Line line = new Line(gateHead.getX(), gateHead.getY(), gateTail.getX(), gateTail.getY());
            line.setStrokeWidth(3);
            gates.add(line);
        }
        group.getChildren().addAll(gates);

        for (int i = 0; i < gates.size(); i++) {
            gates.get(i).setStartX(Coordinate.getRelativeX(race.getGates().get(i).getMarks().get(0).getX()));
            gates.get(i).setStartY(Coordinate.getRelativeY(race.getGates().get(i).getMarks().get(0).getY()));
            gates.get(i).setEndX(Coordinate.getRelativeX(race.getGates().get(i).getMarks().get(1).getX()));
            gates.get(i).setEndY(Coordinate.getRelativeY(race.getGates().get(i).getMarks().get(1).getY()));
        }
    }

    private void setViewParameters(){
        if(race.isViewReady() && !viewInitialised) {

            race.updateViewMinMax();

            Coordinate.setOffset(new Mark(0, 0));
            Coordinate.setDefaultCourseMin(race.getViewMin());
            Coordinate.setDefaultCourseMax(race.getViewMax());
            Coordinate.setViewMin(race.getViewMin().getCopy());
            Coordinate.setViewMax(race.getViewMax().getCopy());

            centerOfScreen = new Boat(-1);
            centerOfScreen.setMark(race.getMapCenter());

            if (!Coordinate.isTrackingBoat()) {
                boatToFollow = centerOfScreen;
            }

            Coordinate.setCenter(race.getCenter(race.getViewMin().getCopy(), race.getViewMax().getCopy()));
            Coordinate.updateViewCoordinates();
        }
        viewInitialised = true;
    }

    private Mark calculateOffset(){
        Mark offset = new Mark();

        offset.setX(boatToFollow.getX() - race.getMapCenter().getX());
        offset.setY(boatToFollow.getY() - race.getMapCenter().getY());

        return offset;
    }

    private void resetZoom() {
        boatToFollow = centerOfScreen;
    }

    private void updateViewLayout(){
        if(race.isViewReady() && viewInitialised){
            Coordinate.updateBorder();
            Coordinate.setOffset(calculateOffset());
            Coordinate.updateViewCoordinates();

        }

        if(Coordinate.getWindowHeightY() != windowHeight || Coordinate.getWindowWidthX() != windowWidth) {
            viewAnchorPane.setMinHeight(Coordinate.getWindowHeightY());
            viewAnchorPane.setMaxHeight(Coordinate.getWindowHeightY());
            viewAnchorPane.setMinWidth(Coordinate.getWindowWidthX());
            viewAnchorPane.setMaxWidth(Coordinate.getWindowWidthX());

            fpsLabel.setLayoutX(Coordinate.getWindowWidthX() - 90);
            fpsLabel.setLayoutY(60);
            clock.setLayoutY(20);
            clock.setLayoutX(Coordinate.getWindowWidthX() - 155);
            resetViewButton.setLayoutX(14);
            resetViewButton.setLayoutY(Coordinate.getWindowHeightY() - 100);
            fpsBtn.setLayoutX(14);
            fpsBtn.setLayoutY(Coordinate.getWindowHeightY() - 75);
            annotationBtn.setLayoutX(14);
            annotationBtn.setLayoutY(Coordinate.getWindowHeightY() - 50);
            BoatNameCheckBox.setLayoutX(14);
            BoatNameCheckBox.setLayoutY(Coordinate.getWindowHeightY() - 150);
            BoatSpeedCheckBox.setLayoutX(14);
            BoatSpeedCheckBox.setLayoutY(Coordinate.getWindowHeightY() - 125);
            localTime.setLayoutX(Coordinate.getWindowWidthX() - 110);
            localTime.setLayoutY(100);
            localTimeZone.setLayoutX(Coordinate.getWindowWidthX() - 115);
            localTimeZone.setLayoutY(80);

            windowHeight = Coordinate.getWindowHeightY();
            windowWidth = Coordinate.getWindowWidthX();
        }


    }

    private void updateClock() {
        updateRaceClock();
    }

    private void setUTC() {
        if (race.getHasRegatta()){
            Font font = new Font("Arial", 15);
            localTime.setFont(font);
            localTime.setVisible(true);
            int utc = race.getRegatta().getUtcOffset();
            TimeZoneWrapper timeZoneWrapper = new TimeZoneWrapper(utc);
            String text = timeZoneWrapper.getLocalTimeString();
            localTime.setText(text);
            localTimeZone.setFont(font);
            localTimeZone.setText(timeZoneWrapper.getRaceTimeZoneString());
            localTimeZone.setVisible(true);
        }
    }

    /**
     * Rotates the wind arrow based on the heading
     */
    private void rotateWindArrow() {
        windArrow.setRotate(race.getWindHeading()
        );
    }

    /**
     * Scales the wind arrow based on the wind speed
     */
    private void scaleWindArrow() {
        windArrow.updateScaling(race.getWindSpeed());
    }

    /**
     * When called, checks the boats time to finish, updating positions based on that.
     */
    private void checkPositions() {

        List<Boat> boats = race.getBoats();

        boats.sort((o1, o2) -> o1.getCurrentLegIndex()>o2.getCurrentLegIndex()?-1:o1.getCurrentLegIndex()<=o2.getCurrentLegIndex()?1: 0);
        for (int i = 0; i < boats.size(); i++) {
            int position = i + 1;
            boats.get(i).setPosition(position);
        }

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
     * Creates the chart that gets displayed in the sidebar. Created at first with no data.
     * Creates a timer that calls the update sparklines every second, allowing the graph to continue to update
     */
    private void createChart() {

        sparklinesChart.setLegendVisible(false);

        // Hide the Y axis
        sparklinesChart.getYAxis().setTickLabelsVisible(false);
        sparklinesChart.getYAxis().setVisible(false);

        // Hide the X axis
        sparklinesChart.getXAxis().setTickLabelsVisible(false);
        sparklinesChart.getXAxis().setVisible(false);

        sparklinesChart.setCreateSymbols(false);

        List<XYChart.Series<Number, Number>> series = new ArrayList<>();
        for (Boat boat :race.getBoats()) {
            XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
            newSeries.getData().add(new XYChart.Data<>(0,0));
            newSeries.setName(boat.getName());
            series.add(newSeries);
        }

        seriesList = FXCollections.observableList(series);
        sparklinesChart.setData(seriesList);
    }

    /**
     * Called by a timer, updates the data displayed in the sparkline chart in the sidebar.
     */
    private void updateSparkLineChart() {
        checkPositions();
        // Check the data is up to date.
        // Retrieve the boat position data.
        for (int i = 0; i < race.getBoats().size(); i++) {
            // update the chart
            Number reversedOrder = race.getBoats().size() - race.getBoats().get(i).getPosition() + 1;
            seriesList.get(i).getData().add(new XYChart.Data<>(secondCounter, reversedOrder));
        }

        otherSortedBoats = sortedBoats;
        sortedBoats = race.getBoats();
        sparklinesChart.setData(seriesList);
        positionTable.setItems(FXCollections.observableArrayList(race.getBoats()));
        secondCounter++;
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
        boundary.setOpacity(0.5);
        return boundary;
    }

    /**
     * resets the view back to its original state
     */
    public void resetViewButtonPressed() {
        resetZoom();
        Coordinate.setZoom(0);
        resetViewButton.setDisable(true);
        Coordinate.setTrackingBoat(false);
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
        showName = BoatNameCheckBox.isSelected();

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
        showSpeed = BoatSpeedCheckBox.isSelected();

        if (BoatNameCheckBox.isSelected() && BoatSpeedCheckBox.isSelected()) {
            annotationBtn.setText("Remove Annotations");
        } else {
            annotationBtn.setText("Show Annotations");
        }
    }

    /**
     *Updates the course boundaries on the view,
     * clears and re-draws boundary to avoid problem when course bounds change
     */
    private void updateBoundary(){
        if(race.isViewReady()){
            boundary = getBoundary(race);
            boundaryGroup.getChildren().clear();
            boundaryGroup.getChildren().add(boundary);
        }

    }

    /**
     * Increments the race clock, and updates the fps display
     */
    private void updateRaceClock() {
        long raceTime;
        if (race.started()) {
            raceTime = race.getCurrentTime() - race.getExpectedStartTime();
        } else {
            raceTime = race.getExpectedStartTime() - race.getCurrentTime();
        }
        System.out.println("expected: " + race.getExpectedStartTime());
        System.out.println("current: " + race.getCurrentTime());
        System.out.println(raceTime);

        raceHours = (int) TimeUnit.MILLISECONDS.toHours(raceTime);
        raceMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(raceTime) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(raceTime)));
        raceSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(raceTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(raceTime)));

        if (race.notGoing()) {
            raceHours = 0;
            raceMinutes = 0;
            raceSeconds = 0;
        }

        clock.setText(String.format(" %02d:%02d:%02d", raceHours, raceMinutes, raceSeconds));
    }

    /**
     * @param nodeToScale node to scale based on current level of zoom
     */
    private void updateNodeScale(Node nodeToScale) {
        nodeToScale.setScaleX(1/(1+Coordinate.getZoom()*0.9));
        nodeToScale.setScaleY(1/(1+Coordinate.getZoom()*0.9));
    }
}
