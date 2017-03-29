package seng302.Model;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seng302.controller.MainController;
import seng302.controller.XYPoint;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by tjg73 on 2/03/17.
 */
public class Race {

    private ArrayList<Boat> racingBoats = new ArrayList<>();
    private Course raceCourse;
    private ArrayList<String> finishingOrder = new ArrayList<>();
    private int racePlaybackDuration = -1;
    private float playbackSpeedMultiplier = -1;
    private float slowestBoatSpeed = Integer.MAX_VALUE;
    private double totalRaceDistance;
    private Group raceGroup;
    private Canvas canvas;
    private HashMap<String, Circle> boatCircles = new HashMap<>();
    private ArrayList<Color> boatColors = new ArrayList<>(asList(Color.CHOCOLATE, Color.GREEN, Color.CYAN,Color.DARKGREY, Color.GOLD,  Color.PURPLE));
    private MainController mainController;

    /**
     * Basic constructor for the Race. this may be subject to change at some point.
     * @param raceCourse
     */
    public Race (Course raceCourse) {
        this.raceCourse = raceCourse;
        generateBoats(6);
    }

    public void passMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Creates the boats and assigns the race its node group
     * @param raceGroup javafx Group node
     * @param raceCourse
     */
    public Race(Group raceGroup, Course raceCourse){
        this.raceCourse = raceCourse;
        this.raceGroup = raceGroup;
        this.canvas = (Canvas)raceGroup.getChildren().get(0);

    }

    public Race(Group raceGroup, Course raceCourse, Canvas mainCanvas){
        this.raceCourse = raceCourse;
        this.raceGroup = raceGroup;
        this.canvas = mainCanvas;
    }

    /**
     * For setting up initial race conditions
     */
    public void raceSetup(){
        generateBoats(6);
        for(int i = 0; i < racingBoats.size(); i++){
            Boat boat = racingBoats.get(i);
            int markIndex = raceCourse.getCourseOrder(boat.getCurrentLeg());
            CompoundMark start = raceCourse.getCourseCompoundMarks().get(0);
            start = start.findAverageGate(start);

            boat.setDestinationMark(raceCourse.getCourseCompoundMarks().get(markIndex));
            boat.setCurrentPosition(start);
            boat.updateHeading();

            XYPoint convertedMark = convertCompoundMarkToXYPoint(start);
            Circle boatCircle = new Circle(convertedMark.x, convertedMark.y, 6, boatColors.get(i));
            raceGroup.getChildren().add(boatCircle);
            boatCircles.put(boat.getBoatName(), boatCircle);
        }
        totalRaceDistance = raceCourse.generateTotalCourseLength() * 1000; //TODO WE EXPECT 10626m (Convert that bad boy from km to m)
    }

    private double calculateDistanceIncrement(Boat boat, double timeDifference){

        double v_desired = totalRaceDistance  / racePlaybackDuration;
        double K = v_desired / slowestBoatSpeed;
        double scaled_boat_speed = boat.getBoatSpeed() * K;
        double distance_increment = scaled_boat_speed * timeDifference;

        return distance_increment;
    }

    public void updatePositions(double timeDifference){
        for (int i=0; i<racingBoats.size(); i++){
            Boat currentBoat = racingBoats.get(i);
            double increment_distance = calculateDistanceIncrement(currentBoat, timeDifference);
            updateBoat(increment_distance, currentBoat);
        }
    }
    /**
     *Calculates the spherical distance between two airports based off their latitude longitude and altitude.
     * Implementation taken from http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
     * @return Returns a double of the spherical distance between airports.
     */
    private static double sphericalDistance(Boat boat) {

        CompoundMark boatPosition = boat.getCurrentPosition();
        CompoundMark destinationPosition = boat.getDestinationMark();

        final int R = 6371; //

        CompoundMark.Point  boatCoords = boatPosition.getCompoundMarks().get(0);
        CompoundMark.Point  destCoords = destinationPosition.getCompoundMarks().get(0);

        Double latDistance = Math.toRadians(destCoords.getLatitude() - boatCoords.getLatitude());
        Double longDistance = Math.toRadians(destCoords.getLongitude() - boatCoords.getLongitude());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(boatCoords.getLatitude())) * Math.cos(Math.toRadians(destCoords.getLatitude()))
                * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


        double distance = R * c * 1000; // convert to meters
        double height = 0;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);  //In meters
    }

    private void updateBoat(double distanceTravelled, Boat boat){
        Circle boatCircle = boatCircles.get(boat.getBoatName());

        boolean hasPassed = hasBoatPassedMark(boat, distanceTravelled);

        if (hasPassed) {

            if (boat.hasFinished){
                distanceTravelled = 0;
            }
            else{
                boat.incrementLeg();
                CompoundMark newDest = raceCourse.getCompoundMarkById(raceCourse.getCourseOrder(boat.getCurrentLeg()));
                boat.setDestinationMark(newDest);
                boat.updateHeading();
            }

        }

        boat.updateCurrentPosition(distanceTravelled);
        mainController.updateAnnoPos(boat,
                                    this.convertCompoundMarkToXYPoint(boat.getCurrentPosition()).x,
                                    this.convertCompoundMarkToXYPoint(boat.getCurrentPosition()).y);
        XYPoint convertedMark = convertCompoundMarkToXYPoint(boat.getCurrentPosition());
        boatCircle.relocate(convertedMark.x, convertedMark.y);
    }

    public boolean hasBoatPassedMark(Boat boat, double distanceTravelled) {
        boolean hasPassed = false;
        double distanceFromCurrentPosToMark = Course.findDistBetweenCompoundMarks(boat.getCurrentPosition(), boat.getDestinationMark()) * 1000; //Scaled to 1km
        if( distanceTravelled > distanceFromCurrentPosToMark) {
            hasPassed = true;
            if (boat.getDestinationMark().getName().equals("Finish")){
                boat.hasFinished = true;
            }
        }
        return hasPassed;
    }

    /**
     * Given a number of boats, generate a number of random boats to participate in the race.
     * @param numBoats
     */
    public void generateBoats(int numBoats){
        racingBoats.clear();
        ArrayList<Boat> allBoats = new ArrayList<>();
        allBoats.add(new Boat("ORACLE TEAM USA", 50, "USA"));
        allBoats.add(new Boat("Artemis Racing", 55, "SWE"));
        allBoats.add(new Boat("Emirates Team New Zealand", 60, "NZL"));
        allBoats.add(new Boat("Groupama Team France", 65, "FRA"));
        allBoats.add(new Boat("Land Rover BAR", 70, "GBR"));
        allBoats.add(new Boat("SoftBank Team Japan", 75, "JPN"));

        setSlowestBoatSpeed(Integer.MAX_VALUE);

        Collections.shuffle(allBoats);
        for (int i=0; i< numBoats; i++){
            racingBoats.add(allBoats.get(i));

            float boatSpeed = allBoats.get(i).getBoatSpeed();
            if (getSlowestBoatSpeed() > boatSpeed) {
                setSlowestBoatSpeed(boatSpeed);
            }
        }
    }

    private XYPoint convertCompoundMarkToXYPoint(CompoundMark mark){
        XYPoint convertedMark = new XYPoint();
        double minCanvasBoundsXY = 50;
        double maxXBound = canvas.getWidth() - 100;
        double maxYBound = canvas.getHeight() - 100;
        ArrayList<Double> latLongBounds = raceCourse.findMaxMinLatLong();

        double deltaLat = Math.abs((latLongBounds.get(1) - latLongBounds.get(0)));
        double deltaLong = Math.abs((latLongBounds.get(3) - latLongBounds.get(2)));

        double markLat = mark.getCompoundMarks().get(0).getLatitude();
        double markLong = mark.getCompoundMarks().get(0).getLongitude();

        convertedMark.x = (maxXBound*(markLong - latLongBounds.get(2)) / deltaLong) + minCanvasBoundsXY;
        convertedMark.y = maxYBound - (maxYBound*(markLat - latLongBounds.get(0)) / deltaLat) + minCanvasBoundsXY;

        return convertedMark;
    }

    /**
     * Adds the boats in the current race from the regatta
     * @param numberOfBoats Number of boats to be added from the current regatta to the race
     * @param competitors List of current boats in the regatta
     */
    public void addRacingBoats(int numberOfBoats, ArrayList<Boat> competitors){
        Random random = new Random();
        if (numberOfBoats <= competitors.size()){
            for (int i = 0; i < numberOfBoats; i++){
                int nextBoatToAdd = random.nextInt(competitors.size());
                racingBoats.add(competitors.get(nextBoatToAdd));
                if (competitors.get(nextBoatToAdd).getBoatSpeed() < getSlowestBoatSpeed()){
                    setSlowestBoatSpeed(competitors.get(nextBoatToAdd).getBoatSpeed());
                }
                competitors.remove(nextBoatToAdd);
            }
        }
    }

    public void setRaceSpeed(){;
        HashSet<Integer> validRaceLength = new HashSet<>(asList(1, 5));
        Scanner input = new Scanner(System.in);
        System.out.println("What duration do you want the race to be in minutes?");
        while (!(validRaceLength.contains(racePlaybackDuration))){
            try {
                racePlaybackDuration = input.nextInt();
            }catch (InputMismatchException ex){
                System.out.println("Input must be a whole number.");
                input.next();
            }
            if(!(validRaceLength.contains(racePlaybackDuration))){
                System.out.println("Please ensure that the number is a valid race duration (1 or 5 minutes long).");
            }
        }

        racePlaybackDuration = racePlaybackDuration * 60;

    }

    public ArrayList<Boat> getRacingBoats() {
        return racingBoats;
    }

    public ArrayList<String> getFinishingOrder() {
        return finishingOrder;
    }

    public void setRacePlaybackDuration(int racePlaybackDuration) {
        this.racePlaybackDuration = racePlaybackDuration;
    }

    public float getSlowestBoatSpeed() {
        return slowestBoatSpeed;
    }

    private void setSlowestBoatSpeed(float slowestBoatSpeed) {
        this.slowestBoatSpeed = slowestBoatSpeed;
    }
}