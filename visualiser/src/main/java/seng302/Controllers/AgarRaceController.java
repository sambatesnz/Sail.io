package seng302.Controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import seng302.RaceObjects.*;
import seng302.Rounding;
import seng302.Visualiser.Arrow;
import seng302.Visualiser.BoatSprite;
import seng302.Visualiser.FPSCounter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;
import static java.lang.Math.atan2;
import static javafx.scene.input.KeyCode.Z;

/**
 * Controls the agar race
 */
public class AgarRaceController implements IRaceController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML private AnchorPane viewAnchorPane;
    @FXML private Group group;
    @FXML private Group boundaryGroup;
    @FXML private Label clock;
    @FXML private Label localTimeZone;
    @FXML private Label localTime;
    @FXML private ListView<String> finishedListView;
    @FXML private TableView<BoatInterface> positionTable;
    @FXML private TableColumn<BoatInterface, String> positionCol;
    @FXML private TableColumn<BoatInterface, String> nameCol;
    @FXML private TableColumn<BoatInterface, String> speedCol;
    @FXML private TableColumn<BoatInterface, String> legCol;
    @FXML private Label fpsLabel;
    @FXML private Button annotationBtn;
    @FXML private Button fpsBtn;
    @FXML private ListView<String> startersList;
    @FXML private LineChart<Number, Number> sparklinesChart;
    @FXML private SplitPane sidePanelSplit;
    @FXML private Pane finishingPane;
    @FXML private Group finishingGroup;
    @FXML private CheckBox BoatNameCheckBox;
    @FXML private CheckBox BoatSpeedCheckBox;
    @FXML private Button toggleFinishersBtn;

    private Race race;

    private List<BoatSprite> boats = new ArrayList<>();
    private List<Rectangle> compoundMarks = new ArrayList<>();
    private List<Line> gates = new ArrayList<>();
    private List<List<Point2D>> absolutePaths = new ArrayList<>();
    private List<Double> lastHeadings = new ArrayList<>();
    private Polygon boundary = new Polygon();
    private Arrow nextMarkArrow;
    private Arrow windArrow = new Arrow();
    private Group roundingArrow1 = new Group();
    private Group roundingArrow2 = new Group();
    private Group roundingArrowMirrored1 = new Group();
    private Group roundingArrowMirrored2 = new Group();

    private boolean showName = true;
    private boolean showSpeed = true;
    private boolean showFPS = true;
    private List<Path> paths = new ArrayList<>();
    private BoatInterface boatToFollow;
    private BoatInterface spectatorBoat;
    private BoatInterface centerOfScreen;
    private double zoomLevel = 0;
    private boolean followingBoat = false;
    private int raceHours = 0;
    private int raceMinutes = 0;
    private int raceSeconds = 0;
    private long lastTime = 0;
    private long timerUpdate = 1000000000;
    private int frameCount = 0;
    private int viewUpdateCount = 0;
    private double windowWidth = 0;
    private double windowHeight = 0;
    private String ipAddr;
    private int port;
    private AnimationTimer raceListener;
    private boolean isFinishersHidden = true;

    // Sparkline variables
    @FXML    private NumberAxis xAxis;
    @FXML    private NumberAxis yAxis;
    private ObservableList<XYChart.Series<Number, Number>> seriesList;
    private Integer secondCounter = 1;
    private Integer sparkCounter = 0;
    private List<BoatInterface> sortedBoats;
    private List<BoatInterface> otherSortedBoats;
    private int EARTH_RADIUS = 6371;
    private int METERS_CONVERSION = 1000;
    private final int SPARKLINEHEIGHT = 239;
    private final double BOUNDARY_OPACITY = 0.5;
    private FPSCounter fpsCounter;
    private int roundingArrowRotationClockwise = 0;
    private int roundingArrowRotationAntiClockwise = 0;


    private boolean boatMetaDataInitialised = false;
    private boolean boatLocationDataInitialised = false;
    private boolean viewInitialised = false;
    private Stage primaryStage;
    private boolean clientFinished = false;

    private ImageView imageOne;
    private ImageView imageTwo;
    private ImageView imageThree;


    public AgarRaceController(Race race){
        this.race = race;
    }

    /**
     * initializes the race display.
     */
    @FXML
    public void initialize() throws IOException {
        mainBorderPane.setLeft(sidePanelSplit);
        mainBorderPane.setCenter(viewAnchorPane);

        windArrow.setTranslateX(50);
        windArrow.setTranslateY(50);
        group.getChildren().add(windArrow);

        clock.setFont(new Font("Arial", 30));
        clock.setText(" 00:00:00");
        clock.setVisible(true);

        fpsCounter = new FPSCounter(fpsLabel);

        initialiseZoomFollowing();
        initialiseRoundingArrow();
        initialiseNextMarkArrow();
        initialisePositionsTable();
        enableScrolling();
        toggleFinishersBtn.setVisible(false);
        toggleFinishersBtn.setText("Hide Finishers");
        finishingPane.setVisible(false);
        race.finishedProperty().addListener((observable, oldValue, raceFinished) -> {
            if (raceFinished) {
                stopRaceListener();
            }
        });
        loadFinishers();
        initialiseRaceListener();
        initFinisherObserver();
        if (race.getClientSourceId() == 0){
            initialiseSpectatorZoom();
        }

        drawLives();

        startRaceListener();
    }

    private void initialiseSpectatorZoom() {
        positionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                spectatorBoat = race.getBoatsMap().get(newValue.getSourceId());
                if (followingBoat) {
                    zoomLevel = Coordinate.getZoom();
                    resetZoom();
                    Coordinate.setTrackingBoat(false);
                    followingBoat = !followingBoat;
                }
                Platform.runLater( ()-> {  positionTable.getSelectionModel().clearSelection();  });
            }
        });
    }

    private void initialiseZoomFollowing() {
        mainBorderPane.setOnKeyPressed(event -> {
            if (event.getCode().equals(Z)) {
                resetViewButtonPressed();
            }
        });
    }

    private void enableScrolling() {
        mainBorderPane.setOnScroll(event -> {
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

    private void initialiseMarkRoundingSprites() {

    }

    private void initialisePositionsTable() {
//        positionCol.setCellValueFactory(p -> {
//            String pos = String.valueOf(p.getValue().getPosition());
//            return new ReadOnlyObjectWrapper<>(pos);
//        });
//        positionCol.setVisible(false);


        legCol.setCellValueFactory(new PropertyValueFactory<>("currentLegIndex"));
        legCol.setSortType(TableColumn.SortType.ASCENDING);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedCol.setCellValueFactory(p -> {
            String speed = String.valueOf(p.getValue().getSpeedInKnots());
            return new ReadOnlyObjectWrapper<>(speed);
        });
    }

    private void initialiseRaceListener() {
        raceListener = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                rotateWindArrow();
                scaleWindArrow();
                setUTC();
                updateClock();
                fpsCounter.update(currentNanoTime);
                setViewParameters();

                try {
                    updateViewLayout();
                } catch (Exception e) {
//                    e.printStackTrace(); //removed for production environment
                }

                initialiseBoatMetaData();
                initialiseBoatLocation();
                try {
                    updateCourseLayout();
                    if (race.isRaceReady() && boatLocationDataInitialised) {
                        updateBoatPositions();
                        updateBoatLives();
                        //updateBoatPaths();
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                }
                updateBoundary();

                viewUpdateCount++;
                if (race.isRaceReady() && fpsCounter.getFrameCount() % 30 == 0) {
                    positionTable.refresh();
                    positionTable.setItems(FXCollections.observableArrayList(race.getBoats()));
                    positionTable.setPrefHeight(Coordinate.getWindowHeightY());
                }
                if (race.getCollisionCount() > 0) {
                    race.reduceCollisionCount();
                    // need to find a way to highlight only the users specific boat.
                    for (BoatSprite boatSprite : boats) {
                        if (boatSprite.getBoat().getSourceId() == race.getClientSourceId()) {
                            boatSprite.collisionHighlight();
                            if (race.getCollisionCount() == 0) {
                                boatSprite.removeCollisionHighlight();
                            }
                        }
                    }
                }
                if (race.isFinished()) {
                    raceListener.stop();
                }
                sparkCounter++;
            }
        };
    }

    private void drawLives() {
        double totalVert = 100;
        imageOne = new ImageView();
        imageOne.setImage(new Image("2000px-Love_Heart_symbol.svg.png"));
        imageOne.setFitHeight(40.0);
        imageOne.setFitWidth(40.0);
        imageOne.setX(20);
        imageOne.setY(totalVert);
        totalVert += imageOne.getFitHeight();

        imageTwo = new ImageView();
        imageTwo.setImage(new Image("2000px-Love_Heart_symbol.svg.png"));
        imageTwo.setFitHeight(40.0);
        imageTwo.setFitWidth(40.0);
        imageTwo.setX(20);
        imageTwo.setY(totalVert);
        totalVert += imageTwo.getFitHeight();

        imageThree = new ImageView();
        imageThree.setImage(new Image("2000px-Love_Heart_symbol.svg.png"));
        imageThree.setFitHeight(40.0);
        imageThree.setFitWidth(40.0);
        imageThree.setX(20);
        imageThree.setY(totalVert);

        viewAnchorPane.getChildren().add(imageOne);
        viewAnchorPane.getChildren().add(imageTwo);
        viewAnchorPane.getChildren().add(imageThree);
    }

    private void updateBoatLives() {
        BoatInterface boat =  race.getClientBoat();
        switch (boat.getLives()) {
            case 1:
                imageOne.setVisible(true);
                imageTwo.setVisible(false);
                imageThree.setVisible(false);
                break;
            case 2:
                imageOne.setVisible(true);
                imageTwo.setVisible(true);
                imageThree.setVisible(false);
                break;
            case 3:
                imageOne.setVisible(true);
                imageTwo.setVisible(true);
                imageThree.setVisible(true);
                break;
        }
    }

    private void startRaceListener() {
        raceListener.start();
    }

    private void stopRaceListener() {
        raceListener.stop();
    }

    private void updateBoatPositions() {
        final int SAIL_OFFSET = 7;
        for (int i = 0; i < boats.size(); i++) {
            Node boat = boats.get(i).getStack().getChildren().get(BoatSprite.BOAT);
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
                boats.get(i).getStack().setLayoutX(Coordinate.getRelativeX(race.getBoats().get(i).getX()));
                boats.get(i).getStack().setLayoutY(Coordinate.getRelativeY(race.getBoats().get(i).getY()));
                System.out.println("agarsize: " + boats.get(i).getBoat().getAgarSize());
                updateNodeScale(boat, boats.get(i).getBoat().getAgarSize());
                boats.get(i).getStack().getChildren().get(BoatSprite.BOAT).setRotate(race.getBoats().get(i).getHeading());

                // Temporary (turns out it's permanent) hard coding to differentiate between the boat in user control
                if (race.getBoats().get(i).getSourceId() == race.getClientSourceId()) {
                    updateNodeScale(boats.get(i).getStack().getChildren().get(BoatSprite.CONTROL_CIRCLE), boats.get(i).getBoat().getAgarSize());
                }

                //Boats wake
                boats.get(i).getStack().getChildren().set(BoatSprite.WAKE, newWake(boatSpeed));
                updateNodeScale(boats.get(i).getStack().getChildren().get(BoatSprite.WAKE), boats.get(i).getBoat().getAgarSize());
                boats.get(i).getStack().getChildren().get(BoatSprite.WAKE).setRotate(race.getBoats().get(i).getHeading());
                boats.get(i).getStack().getChildren().get(BoatSprite.WAKE).setLayoutX(((9 + boatSpeed) * (1 / (1 + Coordinate.getZoom() * 0.9)))
                        * Math.sin(-Math.toRadians(race.getBoats().get(i).getHeading())));
                boats.get(i).getStack().getChildren().get(BoatSprite.WAKE).setLayoutY(((9 + boatSpeed)
                        * (1 / (1 + Coordinate.getZoom() * 0.9))) * cos(-Math.toRadians(race.getBoats().get(i).getHeading())));

                //Boat annotations (name and speed)
                boats.get(i).getStack().getChildren().set(BoatSprite.TEXT, new Text(name + " " + speed));
                boats.get(i).getStack().getChildren().get(BoatSprite.TEXT).setTranslateX(10);
                boats.get(i).getStack().getChildren().get(BoatSprite.TEXT).setTranslateY(0);

                //Sails
                Node sail = boats.get(i).getStack().getChildren().get(BoatSprite.SAIL);
                updateNodeScale(boats.get(i).getStack().getChildren().get(BoatSprite.SAIL), boats.get(i).getBoat().getAgarSize());
                double headingDif = (360 + boats.get(i).getBoat().getHeading() - race.getWindHeading()) % 360;
                if (race.getBoats().get(i).isSailsOut()){
                    boats.get(i).sailOut();
                    sail.getTransforms().clear();
                    if (headingDif < 180 ) {
                        sail.getTransforms().add(new Rotate(race.getWindHeading() + 30, 0, 0));
                    }
                    else {
                        sail.getTransforms().add(new Rotate(race.getWindHeading() - 30, 0, 0));
                    }
                } else {
                    boats.get(i).sailIn();
                    sail.getTransforms().clear();
                    sail.getTransforms().add(new Rotate(race.getWindHeading(), 0,0));

                }
                double sailLength = 720d / 45d;
                sail.setLayoutY((1/(1 + Coordinate.getZoom())) * (sailLength)/2 - SAIL_OFFSET);
            }
        }
    }

    private void updateBoatPaths(){
        //boat paths
        int pathPoints = 250;
        int skipAmount = 15;
        for (int i = 0; i < boats.size(); i++){
            try {
                if (race.getBoats().get(i).isKnowsBoatLocation()) {
                    if (viewUpdateCount % skipAmount == 0) {
                        if (absolutePaths.get(i).size() > pathPoints) {
                            paths.get(i).getElements().remove(1);
                            absolutePaths.get(i).remove(0);
                        }
                        absolutePaths.get(i).add(new Point2D(race.getBoats().get(i).getX(), race.getBoats().get(i).getY()));
                        paths.get(i).getElements().add(new LineTo());
                    }
                    lastHeadings.set(i, race.getBoats().get(i).getHeading());

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
            } catch (IndexOutOfBoundsException e) {
//                e.printStackTrace();
            }
        }
    }

    private void initialiseBoatMetaData() {
        if(race.boatsReady() && !boatMetaDataInitialised && race.isRaceReady()){
            for (int i = 0; i < race.getBoats().size(); i++) {
                BoatSprite boatSprite = new BoatSprite(race.getBoats().get(i), race.getClientSourceId());
                boats.add(boatSprite);
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
            for (BoatSprite boat : boats){
                group.getChildren().addAll(boat.getStack());
            }
//            group.getChildren().addAll(boats);
//            for (Path path: paths) {
//                group.getChildren().add(path);
//            }

            //createChart();
            boatMetaDataInitialised = true;
        }
    }

    private void initialiseBoatLocation() {
        if(!boatLocationDataInitialised && boatMetaDataInitialised) {
            boolean knowAllLocations = true;
            paths = new ArrayList<>();
            lastHeadings = new ArrayList<>();
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
            }
            boatLocationDataInitialised = true;

        }
    }

    private void updateCourseLayout() {
        if (race.isRaceXMLReceived()){
            updateGates();
            updateMarks();
            updateMarkArrows();
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

    private void updateNextMarkArrow(CompoundMark cm) {
        double arrowTranslate = 15/(1+Coordinate.getZoom());
        int playerBoat = race.getClientSourceId();
        if (playerBoat == 0) {
            nextMarkArrow.setVisible(false);
        } else {
            double playerX = Coordinate.getRelativeX(race.getBoatsMap().get(playerBoat).getX());
            double playerY = Coordinate.getRelativeY(race.getBoatsMap().get(playerBoat).getY());
            double arrowX = playerX + arrowTranslate;
            double arrowY = playerY + arrowTranslate;
            double markX = Coordinate.getRelativeX(cm.getX());
            double markY = Coordinate.getRelativeY(cm.getY());
            boolean markIsFar = cm.getMarks().stream().allMatch(mark -> {

                double markX1 = Coordinate.getRelativeX(mark.getX());
                double markY1 = Coordinate.getRelativeY(mark.getY());
                double distX = abs(markX1 - arrowX);
                double distY = abs(markY1 - arrowY);

                double offsetX = 100;
                double offsetY = 100;
                if (markX1 > playerX) {
                    offsetX *= -1;
                }
                if (markY1 > playerY) {
                    offsetY *= -1;
                }

                double nearDistanceX = Coordinate.getWindowWidthX() / 2 + offsetX;
                double nearDistanceY = Coordinate.getWindowHeightY() / 2 + offsetY;

                return distX > nearDistanceX || distY > nearDistanceY;
            });


            if (followingBoat && markIsFar) {
                double angleToNextMark = toDegrees(atan2(markY - arrowY, markX - arrowX));
                nextMarkArrow.setTranslateX(arrowX);
                nextMarkArrow.setTranslateY(arrowY);
                nextMarkArrow.setRotate(angleToNextMark + 90);
                nextMarkArrow.setVisible(true);
                updateNodeScale(nextMarkArrow);
            } else {
                nextMarkArrow.setVisible(false);
            }
        }
    }

    private void updateRoundingArrows(CompoundMark cm) {
        int playerBoat = race.getClientSourceId();
        if (playerBoat != 0) {
            Rounding markRounding = race.getCourseOrder().get(race.getBoatsMap().get(playerBoat).getTargetMarkIndex()).getRounding();
            int rotationIncrement;
            Group currentRoundingArrow1;
            Group currentRoundingArrow2 = roundingArrow2;
            if (markRounding == Rounding.STARBOARD) {
                roundingArrow1.setVisible(false);
                roundingArrowMirrored1.setVisible(true);
                roundingArrow2.setVisible(false);
                roundingArrowMirrored2.setVisible(false);
                rotationIncrement = 3;
                currentRoundingArrow1 = roundingArrowMirrored1;

            } else if (markRounding == Rounding.STARBOARD_PORT) {
                roundingArrow1.setVisible(false);
                roundingArrowMirrored1.setVisible(true);
                roundingArrow2.setVisible(true);
                roundingArrowMirrored2.setVisible(false);
                rotationIncrement = 3;
                currentRoundingArrow1 = roundingArrowMirrored1;
                currentRoundingArrow2 = roundingArrow2;

            } else if (markRounding == Rounding.PORT) {
                roundingArrow1.setVisible(true);
                roundingArrowMirrored1.setVisible(false);
                roundingArrow2.setVisible(false);
                roundingArrowMirrored2.setVisible(false);
                rotationIncrement = -3;
                currentRoundingArrow1 = roundingArrow1;

            } else {
                roundingArrow1.setVisible(true);
                roundingArrowMirrored1.setVisible(false);
                roundingArrow2.setVisible(false);
                roundingArrowMirrored2.setVisible(true);
                rotationIncrement = -3;
                currentRoundingArrow1 = roundingArrow1;
                currentRoundingArrow2 = roundingArrowMirrored2;
            }

            roundingArrowRotationClockwise += rotationIncrement;
            roundingArrowRotationAntiClockwise -= rotationIncrement;

            currentRoundingArrow1.setLayoutX(Coordinate.getRelativeX(cm.getMarks().get(0).getX()));
            currentRoundingArrow1.setLayoutY(Coordinate.getRelativeY(cm.getMarks().get(0).getY()));
            currentRoundingArrow1.setRotate(roundingArrowRotationClockwise);
            updateNodeScale(currentRoundingArrow1);
            if (cm.getMarks().size() > 1) {
                double x1 = cm.getMarks().get(0).getX();
                double y1 = cm.getMarks().get(0).getY();
                double x2 = cm.getMarks().get(1).getX();
                double y2 = cm.getMarks().get(1).getY();
                double angle = Math.toDegrees(atan2(y2 - y1, x2 - x1)) * 2;

                currentRoundingArrow2.setLayoutX(Coordinate.getRelativeX(cm.getMarks().get(1).getX()));
                currentRoundingArrow2.setLayoutY(Coordinate.getRelativeY(cm.getMarks().get(1).getY()));
                currentRoundingArrow2.setRotate(roundingArrowRotationAntiClockwise - angle);
                updateNodeScale(currentRoundingArrow2);
            }
        }
    }

    private void updateMarkArrows() {
        int playerBoat = race.getClientSourceId();
        if (playerBoat != 0) {
            if (race.getBoatsMap().get(playerBoat).getTargetMarkIndex() >= race.getCourseOrder().size()) {
                roundingArrow1.setVisible(false);
                roundingArrowMirrored1.setVisible(false);
                roundingArrow2.setVisible(false);
                roundingArrowMirrored2.setVisible(false);
            } else {
                int cmId = race.getCourseOrder().get(race.getBoatsMap().get(playerBoat).getTargetMarkIndex()).getCompoundMarkId();
                for (int i = 0; i < race.getCompoundMarks().size(); i++) {
                    CompoundMark cm = race.getCompoundMarks().get(i);
                    if (cmId == cm.getId()) {
                        updateNextMarkArrow(cm);
                        updateRoundingArrows(cm);
                    }
                }
            }
        }
    }

    private void initialiseRoundingArrow() {
        roundingArrow1 = createRoundingArrow();
        roundingArrow2 = createRoundingArrow();
        roundingArrowMirrored1 = createMirroredRoundingArrow();
        roundingArrowMirrored2 = createMirroredRoundingArrow();

        if (race.getClientSourceId() == 0){
            roundingArrow1.setVisible(false);
            roundingArrowMirrored1.setVisible(false);
            roundingArrow2.setVisible(false);
            roundingArrowMirrored2.setVisible(false);
        }

        group.getChildren().addAll(roundingArrow1, roundingArrow2, roundingArrowMirrored1, roundingArrowMirrored2);
    }

    private Group createRoundingArrow() {
        Group roundingArrow = new Group();

        Circle rotationBase = new Circle(35);
        rotationBase.setOpacity(0);

        Polyline arrow = new Polyline();
        arrow.getPoints().addAll(
                10.0, -15.0,
                0.0, -25.0,
                12.0, -30.0);
        Arc arc = new Arc(0, 0, 25, 25, 330, 120);
        arc.setType(ArcType.OPEN);

        for (Shape shape: Arrays.asList(arrow, arc)) {
            shape.setFill(null);
            shape.setStroke(Color.GREEN);
            shape.setStrokeWidth(3);
        }
        roundingArrow.getChildren().addAll(arrow, arc, rotationBase);

        return roundingArrow;
    }

    private Group createMirroredRoundingArrow() {
        Group roundingArrow = new Group();

        Circle rotationBase = new Circle(35);
        rotationBase.setOpacity(0);


        Polyline arrowMirrored = new Polyline();
        arrowMirrored.getPoints().addAll(
                -10.0, -15.0,
                0.0, -25.0,
                -12.0, -30.0);
        Arc arcMirrored = new Arc(0, 0, 25, 25, 90, 120);
        arcMirrored.setType(ArcType.OPEN);

        for (Shape shape: Arrays.asList(arrowMirrored, arcMirrored)) {
            shape.setFill(null);
            shape.setStroke(Color.GREEN);
            shape.setStrokeWidth(3);
        }
        roundingArrow.getChildren().addAll(arrowMirrored, arcMirrored, rotationBase);

        return roundingArrow;
    }

    private void initialiseNextMarkArrow() {
        if (race.getClientSourceId() != 0) {
            nextMarkArrow = new Arrow();
            nextMarkArrow.setFill(Color.GREEN);
            group.getChildren().add(nextMarkArrow);
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

            centerOfScreen = new Boat(-1, "CenterOfScreen");
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
        Coordinate.setZoom(0);
    }

    private void updateViewLayout(){
        try {
            if(race.isViewReady() && viewInitialised){
                Coordinate.updateBorder();
                Coordinate.setOffset(calculateOffset());
                Coordinate.updateViewCoordinates();
            }
        } catch (Exception e) {
        }


        if(Coordinate.getWindowHeightY() != windowHeight || Coordinate.getWindowWidthX() != windowWidth) {
            viewAnchorPane.setMinHeight(Coordinate.getWindowHeightY());
            viewAnchorPane.setMaxHeight(Coordinate.getWindowHeightY());
            viewAnchorPane.setMinWidth(Coordinate.getWindowWidthX());
            viewAnchorPane.setMaxWidth(Coordinate.getWindowWidthX());

            finishingGroup.setLayoutX(Coordinate.getWindowWidthX()/2 - 300) ;
            finishingGroup.setLayoutY(Coordinate.getWindowHeightY()/2 - 200);

            finishingPane.setLayoutX(Coordinate.getWindowWidthX()/2 - 300);
            finishingPane.setLayoutY(Coordinate.getWindowHeightY()/2 - 200);

            fpsLabel.setLayoutX(Coordinate.getWindowWidthX() - 90);
            fpsLabel.setLayoutY(60);
            clock.setLayoutY(20);
            clock.setLayoutX(Coordinate.getWindowWidthX() - 155);
            fpsBtn.setLayoutX(14);
            fpsBtn.setLayoutY(Coordinate.getWindowHeightY() - 75);
            annotationBtn.setLayoutX(14);
            annotationBtn.setLayoutY(Coordinate.getWindowHeightY() - 50);
            toggleFinishersBtn.setLayoutX(14);
            toggleFinishersBtn.setLayoutY(Coordinate.getWindowHeightY() - 100);
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
        windArrow.setRotate(race.getWindHeading() + 180);
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

        List<BoatInterface> boats = race.getBoats();
        boats.sort((o1, o2) -> o1.getCurrentLegIndex()>o2.getCurrentLegIndex()?-1:o1.getCurrentLegIndex()<=o2.getCurrentLegIndex()?1: 0);
        for (int i = 0; i < boats.size(); i++) {
            int position = i + 1; //offset by 1 because noone can be in 0th position
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
                -0.2 * speed, speed,
                0.2 * speed,
                speed, 0.0, -speed);
        wake.setStroke(new Color(0.0f, 1.0f, 1.0f, 0.3));
        wake.setFill(new Color(0.0f, 1.0f, 1.0f, 0.3));

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
        for (BoatInterface boat :race.getBoats()) {
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
        // Retrieve the boat position data.'
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
        List<Mark> boundaries = race.getBoundaries();
        for (Mark position : boundaries) {
            boundary.getPoints().add(Coordinate.getRelativeX(position.getX()));
            boundary.getPoints().add(Coordinate.getRelativeY(position.getY()));
        }
        boundary.setFill(Color.LIGHTBLUE);
        boundary.setOpacity(BOUNDARY_OPACITY);
        return boundary;
    }

    /**
     * Resets the view back to its original state.
     * If the boat map is null, it has no effect.
     */
    private void resetViewButtonPressed() {
        if (followingBoat) {
            zoomLevel = Coordinate.getZoom();
            resetZoom();
            Coordinate.setTrackingBoat(false);
        } else {
            int playerBoat = race.getClientSourceId();
            if (race.getBoatsMap() == null || (spectatorBoat == null && playerBoat == 0)) {
                return;
            }
            if (playerBoat == 0){
                boatToFollow = spectatorBoat;
            } else {
                boatToFollow = race.getBoatsMap().get(playerBoat);
            }
            Coordinate.setZoom(zoomLevel);
            Coordinate.setTrackingBoat(true);
        }
        followingBoat = !followingBoat;

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
        try {
            if(race.isViewReady()){
                boundary = getBoundary(race);
                boundaryGroup.getChildren().clear();
                boundaryGroup.getChildren().add(boundary);
            }
        } catch (Exception e) {

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
     * updates node scale with a % of  area of boat( used for when boats grow )
     * @param nodeToScale node to scale based on current level of zoom
     * @param areaPercent area % of the base area that the boat should be drawn at.
     */
    private void updateNodeScale(Node nodeToScale, int areaPercent) {
        double extraArea = ((double) areaPercent) / 100 - 1;
        double extraRadius = sqrt(extraArea/Math.PI);
        nodeToScale.setScaleX((1/(1+Coordinate.getZoom()*0.9) ) * (extraRadius + 1));
        nodeToScale.setScaleY((1/(1+Coordinate.getZoom()*0.9) )* (extraRadius + 1));
    }

    /**
     * @param nodeToScale node to scale based on current level of zoom
     */
    private void updateNodeScale(Node nodeToScale) {
        nodeToScale.setScaleX(1/(1+Coordinate.getZoom()*0.9));
        nodeToScale.setScaleY(1/(1+Coordinate.getZoom()*0.9));
    }

    public void setAddr(String ip, int port) {
        this.ipAddr = ip;
        this.port = port;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    /**
     * This gets called once the visualiser receives information that the players boat has finished the race.
     */
    public void raceFinished() throws IOException {
        finishingPane.setVisible(true);
    }

    private void initFinisherObserver(){
        race.getBoatsForScoreBoard().addListener(new ListChangeListener<BoatInterface>() {
            @Override
            public void onChanged(Change<? extends BoatInterface> c) {
                for (BoatInterface boat : race.getBoatsForScoreBoard()) {
                    if (boat.getSourceId() == race.getClientSourceId()){
                        if(!clientFinished) {
                            finishingPane.setVisible(true);
                            toggleFinishersBtn.setVisible(true);
                            isFinishersHidden = false;
                            clientFinished = true;
                        }


                    }
                }
            }

        });
    }

    @FXML
    public void toggleFinishers(){
        if (isFinishersHidden) {
            finishingPane.setVisible(true);
            toggleFinishersBtn.setText("Hide Finishers");
            isFinishersHidden = false;
        } else {
            finishingPane.setVisible(false);
            toggleFinishersBtn.setText("Show Finishers");
            isFinishersHidden = true;
        }
    }

    public void loadFinishers() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane anchorPane = fxmlLoader.load(getClass().getClassLoader().getResource("FXML/FinishingPage.fxml").openStream());
        FinishingController finishingController = fxmlLoader.getController();
        finishingController.setPrimaryStage(primaryStage);
        finishingController.setRace(race);
        finishingController.initialiseTable();
        finishingPane.getChildren().setAll(anchorPane);
    }
}