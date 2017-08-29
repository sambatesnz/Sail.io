package seng302.RaceObjects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.paint.Color;
import seng302.PacketGeneration.RaceStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class that simulates the racing of the boats competing in the America's Cup 35
 * This displays a text-based play by play commentary of the race as it happens
 */
public class Race {
    private Regatta regatta;
    private int numFinishers = 0;
    private List<CompoundMark> compoundMarks;
    private List<CompoundMark> gates;
    private Map<Integer, Mark> marks;
    // Changing list of boats to hashmap. where key is boat SourceID, as retrieved from the xml message
//    private List<Boat> boats;
    private ObservableMap<Integer, Boat> boats;
    private ObservableList<Boat> finishedBoats;
    private List<Mark> boundaries;
    private List<Leg> courseOrder;
    private double windHeading;
    private double windSpeed;
    private ObservableList<String> MarkStrings;
    private long expectedStartTime;
    private RaceStatus raceStatus;
    private BooleanProperty finished;
    private Mark mapCenter;
    private boolean raceReady = false;
    private List<Integer> participants;
    private LocalDateTime startTime;
    private long currentTime;
    private Mark viewMin;
    private Mark viewMax;
    private SimpleIntegerProperty connectedToServer = new SimpleIntegerProperty(0);

    private boolean raceXMLReceived;

    // yellow, blue, pink, orange, green, purple, red, brown
    private List<String> colourList = Arrays.asList("#ffff00", "#0033cc", "#cc00ff", "#ff6600", "#00cc00", "#6600cc", "#ff0000", "#663300");
    private boolean receivedRaceXML;
    private boolean viewReady;
    private boolean hasRegatta;
    private int clientSourceId;
    public ObservableList<Boat> boatsObs;
    private SimpleStringProperty timeToStart;


    /**
     * Constructor for the race class.
     */
    public Race() {
        finishedBoats = FXCollections.observableArrayList();
        raceXMLReceived = false;
        viewReady = false;
        finished = new SimpleBooleanProperty(false);


        MarkStrings = FXCollections.observableArrayList();
        this.receivedRaceXML = false;
        hasRegatta = false;
        this.clientSourceId = 0;
        boatsObs = FXCollections.observableArrayList();
        timeToStart = new SimpleStringProperty();
    }

    public int isConnectedToServer() {
        return connectedToServer.get();
    }

    public SimpleIntegerProperty connectedToServerProperty() {
        return connectedToServer;
    }

    public void setConnectedToServer(int connectedToServer) {
        this.connectedToServer.set(connectedToServer);
    }

    public boolean isRaceXMLReceived() {
        return raceXMLReceived;
    }

    public void setRaceXMLReceived(boolean raceXMLReceived) {
        this.raceXMLReceived = raceXMLReceived;
    }

    public boolean isRaceReady() {
        return raceReady;
    }

    public boolean isViewReady() {
        return viewReady;
    }

    public void setRaceReady(boolean raceReady) {
        this.raceReady = raceReady;
    }

    public boolean getHasRegatta(){
        return hasRegatta;
    }

    public ObservableList<Boat> getBoatsObs() {
        return boatsObs;
    }

    public Mark getMapCenter() {
        return mapCenter;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public LocalDateTime getStartTime() { return startTime; }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setViewMinMax(Mark min, Mark max){
        viewMin = min;
        viewMax = max;
    }

    public Mark getViewMin(){
        double minLat = boundaries.stream().min(Comparator.comparingDouble(Mark::getLatitude)).get().getLatitude();
        double minLon = boundaries.stream().min(Comparator.comparingDouble(Mark::getLongitude)).get().getLongitude();

        viewMin = new Mark(minLat, minLon);
        return viewMin;

    }
    public Mark getViewMax(){
        double maxLat = boundaries.stream().max(Comparator.comparingDouble(Mark::getLatitude)).get().getLatitude();
        double maxLon = boundaries.stream().max(Comparator.comparingDouble(Mark::getLongitude)).get().getLongitude();

        viewMax = new Mark(maxLat, maxLon);
        return viewMax;
    }

    public void updateViewMinMax(){
        double minLat = boundaries.stream().min(Comparator.comparingDouble(Mark::getLatitude)).get().getLatitude();
        double minLon = boundaries.stream().min(Comparator.comparingDouble(Mark::getLongitude)).get().getLongitude();
        double maxLat = boundaries.stream().max(Comparator.comparingDouble(Mark::getLatitude)).get().getLatitude();
        double maxLon = boundaries.stream().max(Comparator.comparingDouble(Mark::getLongitude)).get().getLongitude();
        viewMin = new Mark(minLat, minLon);
        viewMax = new Mark(maxLat, maxLon);
        mapCenter = getCenter(viewMin.getCopy(), viewMax.getCopy());
    }

    /**
     * Setter for finishedBoat, mainly to allow for testing.
     * @param finishedBoats set the finished list of boats
     */
    public void setFinishedBoats(List<Boat> finishedBoats) {
        this.finishedBoats = FXCollections.observableArrayList(finishedBoats);
    }



    /**
     * @param min viewMin of the course boundary
     * @param max viewMax of the course boundary
     * @return the center of these points
     */
    public Mark getCenter(Mark min, Mark max) {
        Mark center = new Mark();   // changing from setting lat/long to x/y
        center.setX((max.getX() + min.getX()) / 2);
        center.setY((max.getY() + min.getY()) / 2);
        center.setLatitude((max.getLatitude() + min.getLatitude()) / 2);
        center.setLongitude((max.getLongitude() + min.getLongitude()) / 2);
        return center;
    }

    /**
     * Getter for finished boat list.
     * @return finished boat list.
     */
    public ObservableList<Boat> getFinishedBoats() { return finishedBoats; }

    public double getWindHeading() {
        return windHeading;
    }

    /**
     * Takes the list of finished boats and creates a list of strings.
     * @return MarkStrings, an OberservableList of strings with in the order of finishers.
     */
    public ObservableList<String> getPositionStrings() {
        if (!MarkStrings.isEmpty()) {
            MarkStrings.clear();
        }
        MarkStrings.add("Race Results");
        int i = 1;
        for (Boat boat : finishedBoats) {
            MarkStrings.add(i + ": " + boat.getName());
            i++;
        }
        return MarkStrings;
    }

    /**
     * Sets the current direction of the wind
     * @param windHeading the direction that the wind is heading
     */
    public void setWindHeading(double windHeading) {
        this.windHeading = windHeading;
    }

    /**
     * Getter for the speed of the wind for the race
     * @return the speed of the wind, in mm/s
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Sets the current speed of the wind
     * @param windSpeed the speed that the wind is travelling
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Gets the time (in ms since 1 Jan, 1970) that the race is expected to start
     * @return The time that the race is expected to start
     */
    public long getExpectedStartTime() {
        return expectedStartTime;
    }

    /**
     * Sets the time that the race is expected to start (in ms since 1 Jan, 1970)
     * @param expectedStartTime The time that the race is expected to start
     */
    public void setExpectedStartTime(long expectedStartTime) {
        long raceTime;
        this.expectedStartTime = expectedStartTime;
        int raceHours = 0;
        int raceMinutes = 0;
        int raceSeconds = 0;
        raceTime = getExpectedStartTime() - getCurrentTime();

        raceHours = (int) TimeUnit.MILLISECONDS.toHours(raceTime);
        raceMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(raceTime) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(raceTime)));
        raceSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(raceTime) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(raceTime)));


        timeToStart.set(String.format(" %02d:%02d:%02d", raceHours, raceMinutes, raceSeconds));
    }

    /**
     * Gets the current status of the race:
     * 0: Not active
     * 1: Warning (between 3:00 and 1:00 before start)
     * 2: Preparatory (less than 1:00 before start)
     * 3: Started
     * 4: Finished (obsolete)
     * 5: Retired (obsolete)
     * 6: Abandoned
     * 7: Postponed
     * 8: Terminated
     * 9: Race start time not set
     * 10: Prestart (more than 3:00 until start)
     * @return The status of the race
     */
    public RaceStatus getRaceStatus() {
        return raceStatus;
    }

    public boolean started() {
        return raceStatus == RaceStatus.STARTED;
    }

    public boolean notGoing() {
        return !started() && raceStatus != RaceStatus.WARNING && raceStatus != RaceStatus.PREP && raceStatus != RaceStatus.STARTED && raceStatus != RaceStatus.PRESTART;
    }

    /**
     * Sets the current status of the race
     * 0: Not active
     * 1: Warning (between 3:00 and 1:00 before start)
     * 2: Preparatory (less than 1:00 before start)
     * 3: Started
     * 4: Finished (obsolete)
     * 5: Retired (obsolete)
     * 6: Abandoned
     * 7: Postponed
     * 8: Terminated
     * 9: Race start time not set
     * 10: Prestart (more than 3:00 until start)
     * @param raceStatus The status of the race
     */
    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
    }

    /**
     * Getter for the list of compoundMarks
     * @return a list of compoundMarks
     */
    public List<CompoundMark> getCompoundMarks() {
        return compoundMarks;
    }

    /**
     * Get the boats competing
     * @return the boats competing
     */
    public List<Boat> getBoats() {
        if (!raceReady){
            return FXCollections.observableList(new ArrayList<>());
        } else {
            //return boats.values();
            return new ArrayList<>(boats.values());
        }
    }

    public List<Leg> getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(List<Leg> courseOrder) {
        this.courseOrder = courseOrder;
    }

    public void setCompoundMarks(List<CompoundMark> compoundMarks) {
        this.compoundMarks = compoundMarks;
    }

    public void setMarks(Map<Integer, Mark> marks) {
        this.marks = marks;
    }

    public Map<Integer, Mark> getMarks() {
        return marks;
    }

    public void setGates(List<CompoundMark> compoundMarks) {
        gates = new ArrayList<>();
        for (CompoundMark mark : compoundMarks) {
            if (mark.getType().equals("Gate")) {
                gates.add(mark);
            }
        }
    }

    public void setBoundaries(List<CourseLimit> courseLimits) {
        boundaries = new ArrayList<>();
        for (CourseLimit cl: courseLimits) {
            boundaries.add(new Mark(cl.getLat(), cl.getLon()));
        }
    }

    public void setRegatta(Regatta regatta) {
        this.regatta = regatta;
        this.hasRegatta = true;
    }

    public Regatta getRegatta() {
        return regatta;
    }

    public void setBoats(Map<Integer, Boat> boats) {
        ObservableMap<Integer, Boat> participatingBoats = FXCollections.observableHashMap();
        for (int i = 0; i < participants.size(); i++) {
            Integer boatId = participants.get(i);
            Boat boat = boats.get(boatId);
            boat.setColour(Color.valueOf(colourList.get(i)));
            participatingBoats.put(boatId, boat);
        }
        this.boats = participatingBoats;
        boatsObs.setAll(participatingBoats.values());
    }

    public boolean boatsReady(){
        return boats != null;
    }

    public List<CompoundMark> getGates() {
        return gates;
    }

    public List<Mark> getBoundaries() {
        return boundaries;
    }

    public ObservableMap<Integer, Boat> getBoatsMap() {
        return boats;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public void setReceivedRaceXML(boolean receivedRaceXML) {
        this.receivedRaceXML = receivedRaceXML;
    }

    public void setViewReady(boolean viewReady) {
        this.viewReady = viewReady;
    }

    public void setClientSourceId(int clientSourceId) {
        this.clientSourceId = clientSourceId;
    }

    public int getClientSourceId(){
        return this.clientSourceId;
    }

    public String getTimeToStart() {
        return timeToStart.get();
    }

    public SimpleStringProperty timeToStartProperty() {
        return timeToStart;
    }

    public void finishRace() {
        finished.setValue(true);
    }

    public boolean isFinished() {
        return finished.getValue();
    }

    public BooleanProperty finishedProperty() {
        return finished;
    }
}
