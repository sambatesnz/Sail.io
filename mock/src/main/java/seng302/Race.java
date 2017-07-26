package seng302;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng302.PacketParsing.XMLParser;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.Leg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.System.currentTimeMillis;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class that simulates the racing of the boats competing in the America's Cup 35
 * This displays a text-based play by play commentary of the race as it happens
 */
public class Race {
    private int numFinishers = 0;
    private List<CompoundMark> compoundMarks;
    private List<CompoundMark> gates;
    private List<Boat> boats;
    private List<Boat> finishedBoats;
    private List<Leg> legs;
    private List<CourseLimit> boundaries;
    private short windHeading;
    private short startingWindSpeed;
    private int windSpeed;
    private int raceID;
    private char raceType;
    private int raceStatus = 0;
    private ObservableList<Boat> currentOrder;
    private ObservableList<String> positionStrings;
    public boolean finished = false;
    private static final int TEN_KNOTS = 5145;
    private static final int FORTY_KNOTS = 20577;
    private static final int FIVE_KNOTS = 2573;
    private static final int DIRECTION_CHANGE_PROB = 10;

    /**
     * Constructor for the race class.
     */
    public Race() {
        parseCourseXML("Race.xml");
        parseRaceXML("Race.xml");
        // setWindHeading(190);

        setStartingWindSpeed();
        this.windSpeed = this.startingWindSpeed;
        instantiateWindHeading();

        boats = getContestants();
        finishedBoats = new ArrayList<>();
        currentOrder = observableArrayList(boats);
        positionStrings = FXCollections.observableArrayList();
        for (Boat boat : boats) {
            int speed = (new Random().nextInt(5000)+100);
            boat.setSpeed(speed);
            boat.setHeading(legs.get(boat.getCurrentLegIndex()).getHeading());
            boat.getMark().setLongitude(legs.get(0).getStart().getLongitude());
            boat.getMark().setLatitude(legs.get(0).getStart().getLatitude());
        }
    }

    /**
     * Sets the starting wind speed of the race. Randomly selects the wind speed from
     * between valid race wind speeds, between 40 knots (approx. 20,600 mm/s) and 5 knots
     * (approx. 2,600 mm/s)
     */
    private void setStartingWindSpeed() {
        Random random = new Random();
        int startWindVal = random.nextInt(FORTY_KNOTS - FIVE_KNOTS) + FIVE_KNOTS;
        startingWindSpeed = (short) startWindVal;
    }

    private void instantiateWindHeading() {
        Random random = new Random();
        windHeading = (short) random.nextInt(360);
    }

    /**
     * Setter for finishedBoat, mainly to allow for testing.
     * @param finishedBoats set the finished list of boats
     */
    public void setFinishedBoats(List<Boat> finishedBoats) {
        this.finishedBoats = finishedBoats;
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

//    public double getWindHeading() {
//        return windHeading;
//    }

    /**
     * Get the wind direction.
     * Chooses to take a gamble on whether or not to change the wind direction.
     * @return
     */
    public short updateWindDirection() {
        this.windHeading = (short) (windHeading + gambleWindDirection());
        if (this.windHeading > 359) {
            // if the wind heading is greater than or equal to 360, reset it back down to 0
            this.windHeading = (short)(this.windHeading - 360);
        } else if (this.windHeading < 0) {
            // if the wind heading is less than 0, it needs to be reset back up to 360
            this.windHeading = (short)(this.windHeading + 360);
        }
        return (short)((this.windHeading * 65536) / 360);
    }

    public short getWindHeading(){
        return this.windHeading;
    }

    /**
     * Take the current wind direction and decide whether or not it should change based upon a basic mutation
     * probability value
     */
    private int gambleWindDirection() {
        Random random = new Random();
        // temporarily use a 10% chance
        boolean changeVal = random.nextInt(DIRECTION_CHANGE_PROB) == 0;

        if (changeVal) {
            // binary up or down decision
            boolean up = random.nextInt(2) == 0;

            if (up) {
                return 5;
            } else {
                return - 5;
            }
        }
        return 0;
    }

    public int getWindSpeed() {   return this.windSpeed;    }

    /**
     *  Randomly selects a new wind speed ranging from five knots below and five knots above the
     *  original wind speed
     * @return windVal, the new wind speed value
     */
    public short retrieveWindSpeed() {
        Random random = new Random();
        int low = startingWindSpeed - 2600;
        int high = startingWindSpeed + 2600;
        int windVal = random.nextInt(high - low) + low;
        this.windSpeed = windVal;
        return (short) windVal;
    }

    public int getRaceID() {
        return raceID;
    }

    public char getRaceType() {
        return raceType;
    }

    public void setRaceStatus(int raceStatus) {
        this.raceStatus = raceStatus;
    }

    public int getRaceStatus() {
        return raceStatus;
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
    public void setWindHeading(short windHeading) {
        this.windHeading = windHeading;
    }

    /**
     * Getter for the list of compoundMarks
     * @return a list of compoundMarks
     */
    public List<CompoundMark> getCompoundMarks() {
        return compoundMarks;
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
     * Gets the boat identified by a particular source ID number
     * @param sourceID The number that identifies the boat
     * @return The boat identified with SourceID
     */
    public Boat getBoatByID(int sourceID) {
        for (Boat boat : boats) {
            if (boat.getSourceId() == sourceID) {
                return boat;
            }
        }
        return null;
    }

    /**
     * Creates an ArrayList of boats competing in the current race
     * @return The ArrayList of boats
     */
    private ArrayList<Boat> getContestants() {
        ArrayList<Boat> contestants = new ArrayList<>();
        contestants.add(new Boat("ORACLE TEAM USA", "USA", 101, "United States"));
        contestants.add(new Boat("Artemis Racing", "SWE", 102, "Sweden"));
        contestants.add(new Boat("Emirates Team New Zealand", "NZL", 103, "New Zealand"));
        contestants.add(new Boat("SoftBank Team Japan", "JPN", 104, "Japan"));
        contestants.add(new Boat("Groupama Team France", "FRA", 105, "France"));
        contestants.add(new Boat("Land Rover BAR", "GBR", 106, "United Kingdom"));
        return contestants;
    }

    /**
     * Reads the course.xml file to get the attributes of things on the course
     * @param fileName the filename to parse
     */
    private void parseCourseXML(String fileName) {

        try {
            DataGenerator dataGenerator = new DataGenerator();
            String xmlString = dataGenerator.loadFile(fileName);

            XMLParser xmlParser = new XMLParser(xmlString);

            compoundMarks = xmlParser.getCourseLayout();
            boundaries = xmlParser.getCourseLimits();
            List<Integer> courseOrder = xmlParser.getCourseOrder();

            gates = new ArrayList<>();
            for (CompoundMark mark : compoundMarks) {
                if (mark.getType().equals("Gate")) {
                    gates.add(mark);
                }
            }

            legs = new ArrayList<>();
            for (int i = 1; i < courseOrder.size(); i++) {
                int startId = courseOrder.get(i-1);
                int destId = courseOrder.get(i);
                CompoundMark start = null;
                CompoundMark dest = null;
                for (CompoundMark compoundMark : compoundMarks) {
                    if (compoundMark.getId() == startId) {
                        start = compoundMark;
                    } else if (compoundMark.getId() == destId) {
                        dest = compoundMark;
                    }
                }
                legs.add(new Leg(start, dest));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the Race.xml file to get the attributes of things of the race.
     * @param fileName the filename to parse
     */
    private void parseRaceXML(String fileName) {

        try {
            RaceCreator rc = new RaceCreator(fileName);

            raceID = rc.getRaceID();
            raceType = rc.getRaceType();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Get the gates
     * @return the gates
     */
    public List<CompoundMark> getGates() {
        return gates;
    }

    /**
     * Runs a portion of the race, updating boat positions and leg status
     */
    public void updateBoats() {
        double distanceMultiplier = 1;
        double movementMultiplier = 1;

        for (Boat boat : boats) {
            if (!finishedBoats.contains(boat)) {
                boat.setCurrentLegDistance(boat.getCurrentLegDistance() + boat.getSpeed()/1000*distanceMultiplier);

                //todo update boat speed

                //Increments the the distance by the speed
                boat.getMark().setX(boat.getX() + (boat.getSpeed()/1000)*sin(toRadians(boat.getHeading()))*movementMultiplier);
                boat.getMark().setY(boat.getY() + (boat.getSpeed()/1000)*cos(toRadians(boat.getHeading()))*movementMultiplier);
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
//                    }
                }
            }
        }
    }

    public List<CourseLimit> getBoundaries() {
        return boundaries;
    }

}
