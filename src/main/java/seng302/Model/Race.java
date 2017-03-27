package seng302.Model;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    /**
     * Basic constructor for the Race. this may be subject to change at some point.
     * @param raceCourse
     */
    public Race (Course raceCourse) {
        this.raceCourse = raceCourse;
        generateBoats(6);
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
            CompoundMark start = raceCourse.getCourseCompoundMarks().get(0);
            start = start.findAverageGate(start);
            XYPoint convertedMark = convertCompoundMarkToXYPoint(start);
            Circle c = new Circle(convertedMark.x, convertedMark.y, 7.5, boatColors.get(i));
            raceGroup.getChildren().add(c);
            boatCircles.put(racingBoats.get(i).getBoatName(), c);
        }
    }

    public void updatePositions(double timeDifference){
        for(int i = 0; i < racingBoats.size(); i++){
            Boat b = racingBoats.get(i);
            double distanceTravelled = timeDifference * b.getBoatSpeed();
            ///TODO cord to pixle math needed
            //b.setLatCord(newLatval);
            //b.setLongCord(newLongval);

            Circle c = boatCircles.get(b.getBoatName());
            //TODO update circles to new x y positions, this automatically updates them in group
            //c.setCenterX(newXval);
            //c.setCenterY(newYval);

        }
    }


    public void updateBoat(double time){

    }

    /**
     * Given a number of boats, generate a number of random boats to participate in the race.
     * @param numBoats
     */
    public void generateBoats(int numBoats){
        racingBoats.clear();
        ArrayList<Boat> allBoats = new ArrayList<>();
        allBoats.add(new Boat("ORACLE TEAM USA", 50));
        allBoats.add(new Boat("Artemis Racing", 55));
        allBoats.add(new Boat("Emirates Team New Zealand", 60));
        allBoats.add(new Boat("Groupama Team France", 65));
        allBoats.add(new Boat("Land Rover BAR", 70));
        allBoats.add(new Boat("SoftBank Team Japan", 75));

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

    /**
     * Gets a playback duration from std input,
     * must be either 0, 1 or 5 minutes.
     */
    public void setRacePlaybackDuration(){
        // TODO: REMOVE THIS 0 BEFORE SUBMISSION.
        HashSet<Integer> validRaceLength = new HashSet<>(asList(0, 1, 5));
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
                System.out.println("Please ensure that the number is a valid race duration (0, 1 or 5 minutes long).");
            }
        }
        playbackSpeedMultiplier = racePlaybackDuration / (60*((float)totalRaceDistance / getSlowestBoatSpeed()));
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
