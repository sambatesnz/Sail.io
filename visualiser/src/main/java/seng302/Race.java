package seng302;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import seng302.Controllers.Coordinate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.System.currentTimeMillis;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class that simulates the racing of the boats competing in the America's Cup 35
 * This displays a text-based play by play commentary of the race as it happens
 */
public class Race {
    private int numFinishers = 0;
    private List<Landmark> landmarks;
    private List<Landmark> gates;
    private List<Boat> boats;
    private List<Boat> finishedBoats;
    private List<Leg> legs;
    private List<Position> boundaries;
    private double windHeading;
    private ObservableList<Boat> currentOrder;
    private ObservableList<String> positionStrings;
    public boolean finished = false;
    private Position center;
    private Boat boatToFollow;

    /**
     * Constructor for the race class.
     */
    public Race() {
        parseXML("course.xml");
        setWindHeading(190);
        boats = getContestants();
        finishedBoats = new ArrayList<>();
        currentOrder = observableArrayList(boats);
        positionStrings = FXCollections.observableArrayList();
        boatToFollow = boats.get(0);
        Position courseMin = new Position(32.275, -64.855);
        Position courseMax = new Position(32.32, -64.831);
        Coordinate.setOffset(new Position(0, 0));
        Coordinate.setDefaultCourseMin(courseMin);
        Coordinate.setDefaultCourseMax(courseMax);
        Coordinate.setViewMin(courseMin.getCopy());
        Coordinate.setViewMax(courseMax.getCopy());

        center = getCenter(courseMin.getCopy(), courseMax.getCopy());
        Coordinate.setCenter(getCenter(courseMin.getCopy(), courseMax.getCopy()));
        Coordinate.updateViewCoordinates();
        for (Boat boat : boats) {
            boat.setHeading(legs.get(boat.getCurrentLegIndex()).getHeading());
            boat.setX(legs.get(0).getStart().getX());
            boat.setY(legs.get(0).getStart().getY());
        }
    }
    /**
     * Setter for finishedBoat, mainly to allow for testing.
     * @param finishedBoats set the finished list of boats
     */
    public void setFinishedBoats(List<Boat> finishedBoats) {
        this.finishedBoats = finishedBoats;
    }

    public Position getCenter(Position min, Position max) {
        Position center = new Position();   // changing from setting lat/long to x/y
        center.setX((max.getX() + min.getX()) / 2);
        center.setY((max.getY() + min.getY()) / 2);
        return center;
}

    public Position calculateOffset(Boat boat){
//      TODO: calculate offset based on center and boat (boat - center)
        Position offset = new Position();

        offset.setX(boat.getX() - center.getX());
        offset.setY(boat.getY() - center.getY());

        return offset;
    }
    /**
     * Setter for current order, mainly to allow for testing.
     * @param currentOrder sets the current order of boats
     */
    public void setCurrentOrder(ObservableList<Boat> currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * Getter for finished boat list.
     * @return finished boat list.
     */
    public List<Boat> getFinishedBoats() { return finishedBoats; }

    public double getWindHeading() {
        return windHeading;
    }

    /**
     * Takes the list of finished boats and creates a list of strings.
     * @return positionStrings, an OberservableList of strings with in the order of finishers.
     */
    public ObservableList<String> getPositionStrings() {
        if (!positionStrings.isEmpty()) {
            positionStrings.clear();
        }
        positionStrings.add("Race Results");
        int i = 1;
        for (Boat boat : finishedBoats) {
            positionStrings.add(i + ": " + boat.getName());
            i++;
        }
        return positionStrings;
    }

    /**
     * Getter for the observableList currentOrder. Gets the current position of the boats and adds it to the boat
     * position attribute.
     * @return currentOrder as an observable list.
     */
    public ObservableList<Boat> getCurrentOrder() {
        int i = 1;
        for (Boat boat : currentOrder) {
            boat.setPosition(i);
            i++;
        }
        return currentOrder;
    }

    /**
     * Sets the current direction of the wind
     * @param windHeading the direction that the wind is heading
     */
    public void setWindHeading(double windHeading) {
        this.windHeading = windHeading;
    }

    /**
     * Getter for the list of landmarks
     * @return a list of landmarks
     */
    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    /**
     * Get the legs in the race
     * @return the legs in the race
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * Get the boats competing
     * @return the boats competing
     */
    public List<Boat> getBoats() {
        return boats;
    }

    /**
     * Creates an ArrayList of boats competing in the current race
     * @return The ArrayList of boats
     */
    private ArrayList<Boat> getContestants() {
        ArrayList<Boat> contestants = new ArrayList<>();
        contestants.add(new Boat("ORACLE TEAM USA", 5.8, Color.RED, "USA"));
        contestants.add(new Boat("Artemis Racing", 7.1, Color.BLUE, "SWE"));
        contestants.add(new Boat("Emirates Team New Zealand", 11.2, Color.BLACK, "NZL"));
        contestants.add(new Boat("Groupama Team France", 6.7, Color.WHEAT, "FRA"));
        contestants.add(new Boat("Land Rover BAR", 7.6, Color.AQUAMARINE, "GBR"));
        contestants.add(new Boat("SoftBank Team Japan", 9.3, Color.DARKSALMON, "JPN"));
        return contestants;
    }

    /**
     * Reads an XML file to get the attributes of things on the course
     * @param fileName the filename to parse
     */
    private void parseXML(String fileName) {

        try {
            CourseCreator cc = new CourseCreator(fileName);

            landmarks = cc.getLandmarks();
            gates = new ArrayList<>();
            for (Landmark mark : landmarks) {
                if (mark.getType().equals("Gate")) {
                    gates.add(mark);
                }
            }
            boundaries = cc.getBoundaries();

            ArrayList<Integer> courseOrder = cc.getGateOrderForRace();

            legs = new ArrayList<>();
            for (int i = 1; i < courseOrder.size(); i++) {
                int startId = courseOrder.get(i-1);
                int destId = courseOrder.get(i);
                Landmark start = null;
                Landmark dest = null;
                for (Landmark lm : landmarks) {
                    if (lm.getId() == startId) {
                        start = lm;
                    } else if (lm.getId() == destId) {
                        dest = lm;
                    }
                }
                legs.add(new Leg(start, dest));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Get the gates
     * @return the gates
     */
    public List<Landmark> getGates() {
        return gates;
    }

    /**
     * Runs a portion of the race, updating boat positions and leg status
     */
    public void updateBoats() {
        double distanceMultiplier = 1;
        double movementMultiplier = 1;
        Coordinate.setOffset(calculateOffset(boatToFollow));
        Coordinate.updateViewCoordinates();
        for (Boat boat : boats) {
            if (!finishedBoats.contains(boat)) {
                boat.setCurrentLegDistance(boat.getCurrentLegDistance() + boat.getSpeed()*distanceMultiplier);

                //Increments the the distance by the speed
                boat.setX(boat.getX() + boat.getSpeed()*sin(toRadians(boat.getHeading()))*movementMultiplier);
                boat.setY(boat.getY() + boat.getSpeed()*cos(toRadians(boat.getHeading()))*movementMultiplier);

                if (boat.getCurrentLegDistance() > legs.get(boat.getCurrentLegIndex()).getDistance()) {
                    String passed = legs.get(boat.getCurrentLegIndex()).getDest().getName();
                    boat.setCurrentLegDistance(boat.getCurrentLegDistance() - legs.get(boat.getCurrentLegIndex()).getDistance());
                    boat.setCurrentLegIndex(boat.getCurrentLegIndex() + 1);

                    // Gives each boat it's race time to the last mark.
                    boat.setRaceTime(currentTimeMillis());
                    // Sorts the boats in order. Attempts by leg it's doing first, then by time to complete last leg from start.
                    currentOrder.sort((boat1, boat2) -> {
                        if (boat1.getCurrentLegIndex() == boat2.getCurrentLegIndex()) {
                            return Long.compare(boat1.getRaceTime(), boat2.getRaceTime());
                        } else {
                            return Integer.compare(boat2.getCurrentLegIndex(),boat1.getCurrentLegIndex());
                        }
                    });

                    if (boat.getCurrentLegIndex() == legs.size()) {
//                        System.out.println(boat.getName() + " finished race!");
                        numFinishers++;
                        boat.setSpeed(0);
                        finishedBoats.add(boat);
                        if (numFinishers == boats.size()) {
                            finished = true;
                            return;
                        }
                    } else {
                        boat.setHeading(legs.get(boat.getCurrentLegIndex()).getHeading());
//                        System.out.println(boat.getName() + " passed " + passed + ", now sailing to " +
//                                legs.get(boat.getCurrentLegIndex()).getDest().getName() + " with a heading of " +
//                                String.format("%.2f", legs.get(boat.getCurrentLegIndex()).getHeading()) + "°");
                    }
                }
            }
        }
    }

    public void setBoatToFollow(Boat boatToFollow) {
        this.boatToFollow = boatToFollow;
    }

    public List<Position> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(ArrayList<Position> boundaries) {
        this.boundaries = boundaries;
    }
}
