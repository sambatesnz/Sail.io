package seng302;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import seng302.PacketGeneration.RaceStatus;
import seng302.PacketParsing.XMLParser;
import seng302.Polars.PolarUtils;
import seng302.RaceObjects.*;
import seng302.Server.RaceCreator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.*;

/**
 * Class that simulates the racing of the boats competing in the America's Cup 35
 * This displays a text-based play by play commentary of the race as it happens
 */
public class Race {
    private Map<BoatPair, BoatCollision> collisionMap;
    private int numFinishers = 0;
    private List<Pair<CompoundMark, Rounding>> courseRoundingInfo;
    private List<CompoundMark> compoundMarks;
    private List<CompoundMark> gates;
    private List<Boat> boats;
    private Map<Integer, Integer> clientIDs;
    private List<CourseLimit> boundaries;
    private short windHeading;
    private short startingWindSpeed;
    private int windSpeed;
    private Boolean windHeadingChanged = false;
    private Boolean windSpeedChanged = false;
    private boolean practiceRace = false;
    private int raceID;
    private char raceType;
    private RaceStatus raceStatus = RaceStatus.START_TIME_NOT_SET;
    private ObservableList<String> positionStrings;
    private Date startingTime;
    private SimpleStringProperty timeToStart;
    private boolean raceFinishing = false;
    private static final int TEN_KNOTS = 5145;
    private static final int FORTY_KNOTS = 20577;
    private static final int FIVE_KNOTS = 2573;
    private static final int DIRECTION_CHANGE_PROB = 25;
    private final long ONE_MINUTE_IN_MILLIS = 60000;
    private static int MAX_NUMBER_OF_BOATS = 20;

    private BoatGenerator boatGenerator;
    private BoatManager boatManager;
    private long firstFinishTime;


    /**
     * Constructor for the race class.
     */
    public Race() {
        parseCourseXML("Race.xml");
        parseRaceXML("Race.xml");
        // setWindHeading(190);

        setStartingWindSpeed();
        boatGenerator = new BoatGenerator();
        boatManager = new BoatManager();
        this.windSpeed = this.startingWindSpeed;
        instantiateWindHeading();

        startingTime = getNewStartTime();
        firstFinishTime = 0;

        ObservableList<Boat> b = FXCollections.observableArrayList();
        b.addListener((ListChangeListener<Boat>) c -> {
            initCollisions();
        });
        boats = b;

        clientIDs = new HashMap<>();

//        boats = new ArrayList<>();
//        finishedBoats = new ArrayList<>();
        positionStrings = FXCollections.observableArrayList();
        for (Boat boat : boats) {
            int speed = (new Random().nextInt(5000) + 100);
            boat.setSpeed(speed);
            boat.setHeading(0);
            boat.getMark().setLongitude(getCompoundMarks().get(0).getLongitude());
            boat.getMark().setLatitude(getCompoundMarks().get(0).getLatitude());
        }
    }

    /**
     * Creates a new start time based on the current time
     * @return a new time to start the race
     */
    private Date getNewStartTime() {
        Calendar date = Calendar.getInstance();
        long currentTime = date.getTimeInMillis();
        return new Date(currentTime + ONE_MINUTE_IN_MILLIS * 3 / 2);
    }

    /**
     * When called, removes the boat from the race, both the current boats, and the boats that have finished.
     * Race should not be in STATUS WARNING, PREP, STARTED, or FINISHED for a multi-player race
     * @param clientSocketSourceID the source id of the clients socket of the boat to be removed.
     */
    public void removeBoat(int clientSocketSourceID) {
        try {
            int sourceId = clientIDs.get(clientSocketSourceID);
            Boat boat = null;
            for (Boat currentBoat: boats){
                if (currentBoat.getSourceId() == sourceId) {
                    boat = currentBoat;
                }
            }
            boatGenerator.makeAvailable(boat);
            boats.removeIf(currentBoat -> currentBoat.getSourceId() == sourceId);
            clientIDs.remove(clientSocketSourceID);
        }catch (NullPointerException nullPoint){
            System.out.println("Remove called on boat that has already been removed");
            nullPoint.printStackTrace();
        }
    }

    /**
     * Sets the connected flag on a boat to false, where the given boat is the one identified by
     * the source id of the client in the map of boats to clients.
     * @param clientSocketSourceID
     */
    public void setBoatAsDisconnected(int clientSocketSourceID) {
        int sourceId = clientIDs.get(clientSocketSourceID);
        Boat boat = getBoatByID(sourceId);
        boat.disconnect();
    }

    /**
     * Adds a singular boat if it can
     * @param clientSocketSourceID SoucrceId of the Clients Socket
     * @return boat that was added
     * @throws Exception the boat could not be created
     */
    public Boat addBoat(int clientSocketSourceID) throws Exception {
        if (boats.size() < MAX_NUMBER_OF_BOATS){
            Boat boat = boatGenerator.generateBoat();
            clientIDs.put(clientSocketSourceID, boat.getSourceId());
            boats.add(boat);
            return boat;
        } else {
            throw new Exception("cannot create boat");
        }
    }

    public boolean isPracticeRace() {
        return practiceRace;
    }

    /**
     * Sets the starting wind speed of the race. Randomly selects the wind speed from
     * between valid race wind speeds, between 45 knots (approx. 23,200 mm/s) and 10 knots
     * (approx. 5,100 mm/s)
     */
    private void setStartingWindSpeed() {
        Random random = new Random();
        int startWindVal = random.nextInt(FORTY_KNOTS - FIVE_KNOTS) + TEN_KNOTS;
        startingWindSpeed = (short) startWindVal;
    }

    private void instantiateWindHeading() {
        Random random = new Random();
        windHeading = (short) random.nextInt(360);
    }

    public List<Pair<CompoundMark, Rounding>> getCourseRoundingInfo() {
        return courseRoundingInfo;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    /**
     * Get the wind direction.
     * Chooses to take a gamble on whether or not to change the wind direction.
     * @return the wind direction
     */
    public short updateWindDirection() {
        this.windHeading = (short) (windHeading + gambleWindDirection());
        if (this.windHeading > 359) {
            /* if the wind heading is greater than or equal to 360, reset it back down to 0 */
            this.windHeading = (short) (this.windHeading - 360);
        } else if (this.windHeading < 0) {
            /* if the wind heading is less than 0, it needs to be reset back up to 360 */
            this.windHeading = (short) (this.windHeading + 360);
        }
        return (short) ((this.windHeading * 65536) / 360);
    }

    public short getWindHeading() {
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
            windHeadingChanged = true;

            if (up) {
                return 5;
            } else {
                return -5;
            }
        }
        return 0;
    }

    public int getWindSpeed() {
        return this.windSpeed;
    }

    /**
     * Randomly selects a new wind speed ranging from five knots below and five knots above the
     * original wind speed
     *
     * @return windVal, the new wind speed value
     */
    public short retrieveWindSpeed() {
        Random random = new Random();
        int low = startingWindSpeed - 2600;
        int high = startingWindSpeed + 2600;
        int windVal = random.nextInt(high - low) + low;
        this.windSpeed = windVal;
        windSpeedChanged = true;
        return (short) windVal;
    }

    public int getRaceID() {
        return raceID;
    }

    public char getRaceType() {
        return raceType;
    }

    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
    }

    public RaceStatus getRaceStatus() {
        return raceStatus;
    }

    public BoatManager getBoatManager() {
        return boatManager;
    }


    /**
     * Sets the current direction of the wind
     *
     * @param windHeading the direction that the wind is heading
     */
    public void setWindHeading(short windHeading) {
        this.windHeading = windHeading;
    }

    /**
     * Getter for the list of compoundMarks
     *
     * @return a list of compoundMarks
     */
    public List<CompoundMark> getCompoundMarks() {
        return compoundMarks;
    }

    /**
     * Get the boats competing
     *
     * @return the boats competing
     */
    public List<Boat> getBoats() {
        return boats;
    }

    /**
     * Gets the boat identified by a particular source ID number
     *
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
            List<Leg> courseOrder = xmlParser.getCourseOrder();


            gates = new ArrayList<>();
            for (CompoundMark mark : compoundMarks) {
                if (mark.getType().equals("Gate")) {
                    gates.add(mark);
                }
            }

            courseRoundingInfo = new ArrayList<>();
            for (Leg leg: courseOrder) {
                int compoundMarkID = leg.getCompoundMarkId();
                Optional<CompoundMark> optional = compoundMarks.stream().filter(cm -> cm.getId() == compoundMarkID).findFirst();
                if (optional.isPresent()) {
                    CompoundMark compoundMark = optional.get();
                    Rounding rounding = leg.getRounding();
                    courseRoundingInfo.add(new Pair<>(compoundMark, rounding));
                } else {
                    System.err.println("No matching Compound mark id");
                }
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

    public List<CompoundMark> getGates() {
        return gates;
    }

    public String getDateString() {
        TimeZone tz = TimeZone.getTimeZone("NZST");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(startingTime);
    }

    /**
     * Runs a portion of the race, updating boat positions and leg status
     */
    public void updateBoats() {
        double movementMultiplier = 1;

        for (Boat boat : boats) {
            if (windHeadingChanged || boat.getHeadingChanged() || windSpeedChanged) {
                PolarUtils.updateBoatSpeed(boat, windHeading, windSpeed);
            }
            //Increments the the distance by the speed
            //Increments the the distance by the speed
            double newX = boat.getX() + (boat.getSpeed() / (1000 / (17.0 / 1000)) * sin(toRadians(boat.getHeading()))) * movementMultiplier;
            double newY = boat.getY() + (boat.getSpeed() / (1000 / (17.0 / 1000)) * cos(toRadians(boat.getHeading()))) * movementMultiplier;

            boat.getMark().setX(boat.getX() + (boat.getSpeed() / (1000 / (17.0 / 1000)) * sin(toRadians(boat.getHeading()))) * movementMultiplier); //TODO put this 17 ticks into a config file
            boat.getMark().setY(boat.getY() + (boat.getSpeed() / (1000 / (17.0 / 1000)) * cos(toRadians(boat.getHeading()))) * movementMultiplier);

            if (raceStatus == RaceStatus.STARTED) {
                if (!boat.isFinished()) {
                    RoundingUtility.determineMarkRounding(courseRoundingInfo, boat);
                } else if (!boat.isAdded()) {
                    boatManager.addFinishedBoat(boat);
                    boat.setAdded(true);
                    if (!raceFinishing) {
                        setFirstFinishTime();
                    }
                }
            }

            if ((isFinishTimerExpired() || areAllContestantsFinished()) && raceStatus != RaceStatus.FINISHED) {
                raceStatus = RaceStatus.FINISHED;
                System.out.println("Race is completely finished!");
            }

            boat.getMark().setX(newX); //TODO put this 17 ticks into a config file
            boat.getMark().setY(newY);
            boat.setHeadingChangedToFalse();
        }
        windHeadingChanged = false;
        windSpeedChanged = false;

    }

    private void setFirstFinishTime() {
        firstFinishTime = System.currentTimeMillis();
        raceFinishing = true;
    }

    private boolean isFinishTimerExpired(){
        return (firstFinishTime > 0) && (System.currentTimeMillis() > firstFinishTime + ONE_MINUTE_IN_MILLIS);
    }

    private boolean areAllContestantsFinished() {
        for (Boat boat : boats) {
            if (!(boat.isFinished() || !boat.isConnected())){
                return false;
            }
        }
        return true;
    }

    public Map<BoatPair, BoatCollision> getCollisionMap() {
        return collisionMap;
    }


    // @Matt
    public void updateRaceInfo(){
        if (raceStatus != RaceStatus.FINISHED) {
            if (clientIDs.size() < 2) {
                raceStatus = RaceStatus.START_TIME_NOT_SET;
                startingTime = getNewStartTime();
            } else if (startingTime.getTime() < new Date().getTime()) {
                raceStatus = RaceStatus.STARTED;
            } else if (startingTime.getTime() < new Date().getTime() + ONE_MINUTE_IN_MILLIS) {
                raceStatus = RaceStatus.PREP;
            } else {
                raceStatus = RaceStatus.WARNING;
            }
        }
    }

    public List<CourseLimit> getBoundaries () {
        return boundaries;
    }

    public void setPracticeRace(boolean practiceRace) {
        this.practiceRace = practiceRace;
    }


    private void initCollisions(){
        collisionMap = new HashMap<>();
        for (Boat boat : boats){
            for (Boat checkBoat : boats){
                BoatPair boatPair = new BoatPair(boat, checkBoat);
                if(!collisionMap.containsKey(new BoatPair(boat, checkBoat))){
                    collisionMap.put(boatPair, new BoatCollision(boat, checkBoat));
                }else{
                    System.out.println("KEY EXISTS: " + boatPair.getBoat1().getSourceId() + " + " + boatPair.getBoat2().getSourceId());
                }
            }
        }
    }

}