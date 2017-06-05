package seng302.Race;

import javafx.scene.paint.Color;

/**
 * Represent a boat competing in yacht race
 */
public class Boat {
    private Mark mark;
    private String boatName;
    private double currentLegDistance;
    private int currentLegIndex;
    private double heading = 0;
    private Color colour;
    private Long raceTime;
    private int position;
    private String abrv;
    private int sourceId;
    private int status;
    private long timeToNextMark;
    private long timeToFinish;
    private String shortName;
    private String country;
    private double speed;

    /**
     * Gets the abbreviation of the name of the team's boat
     * @return the boat's abbreviation string
     */
    public String getShortName() {
        return shortName;
    }

    public String getBoatName() {
        return boatName;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getCountry() {
        return country;
    }

    /**
     * Getter for the race time.
     * @return raceTime, a long of the current race time.
     */
    public Long getRaceTime() { return raceTime; }

    /**
     * Setter for the race time.
     * @param time the time to be set.
     */
    public void setRaceTime(Long time) { this.raceTime = time; }

    /**
     * Constructs a boat
     * @param name the name of the boat/team
     */
    public Boat(String name, String shortName, int sourceId, String country) {
        this.boatName = name;
        //this.colour = Color.color(Math.random(), Math.random(), Math.random());
        this.shortName = shortName;
        this.sourceId = sourceId;
        this.country = country;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    /**
     * Used to create a boat for testing purposes.
     * @param sourceID  boat source id
     */
    public Boat(Integer sourceID) {
        this.sourceId = sourceID;
    }



    /**
     * Get the boats colour
     * @return the boats colour
     */
    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Get the current heading
     * @return the current heading
     */
    public double getHeading() {
        return heading;
    }

    /**
     * Set the current heading
     */
    public void setHeading(double heading) {
        this.heading = heading;
    }

    /**
     * Get the current x. coordinate
     * @return the current x coordinate
     */
    public double getX() {
        return mark.getX();
    }

    /**
     * Get the current y coordinate
     * @return the current y coordinate
     */
    public double getY() {
        return mark.getY();
    }

    /**
     * Get the index that the current leg is at in the leg list
     * @return Index that the current leg is at
     */
    public int getCurrentLegIndex() {
        return currentLegIndex;
    }

    /**
     * Set the index that the current leg is at in the left list
     * @param currentLegIndex the index to set
     */
    public void setCurrentLegIndex(int currentLegIndex) {
        this.currentLegIndex = currentLegIndex;
    }

    /**
     * Get the distance of the current leg
     * @return the distance of the current leg
     */
    public double getCurrentLegDistance() {
        return currentLegDistance;
    }

    /**
     * Set the distance of the current leg
     * @param currentLegDistance the distance to set
     */
    public void setCurrentLegDistance(double currentLegDistance) {
        this.currentLegDistance = currentLegDistance;
    }

    /**
     * Get the speed of the boat
     * @return speed of the boat
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set the speed of the boat
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Get the name of the boat
     * @return the name of the boat
     */
    public String getName() {
        return boatName;
    }

    /**
     * Get the relative position of the boat in the race
     * @return the position of the boat
     */
    public int getPosition() { return position; }

    /**
     * Sets the position of the boat.
     * @param position1 the position of the boat in the race
     */
    public void setPosition(int position1) { position = position1; }

    /**
     * Get the current latitude
     * @return the current latitude
     */
    public double getLatitude(){
        return mark.getLatitude();
    }

    /**
     * Get the current latitude
     * @return the current latitude
     */
    public double getLongitude(){
        return mark.getLongitude();
    }

    /**
     * Sets the source ID, or the identification number, of the boat
     * @param id The number that the boat will be identified by
     */
    public void setSourceID(int id) { this.sourceId = id; }

    /**
     * Getter for the source ID, or the identification number, of the boat
     * @return The number that the boat is identified by
     */
    public int getSourceID() { return sourceId; }

    /**
     * Getter for the status of the boat:
     * 0: Undefined
     * 1: Prestart
     * 2: Racing
     * 3: Finished
     * 4: DNS (did not start)
     * 5: DNF (did not finish)
     * 6: DSQ (disqualified)
     * 7: OCS (On Course Side – across start line early)
     * @return the status of the boat
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for the status of the boat:
     * 0: Undefined
     * 1: Prestart
     * 2: Racing
     * 3: Finished
     * 4: DNS (did not start)
     * 5: DNF (did not finish)
     * 6: DSQ (disqualified)
     * 7: OCS (On Course Side – across start line early)
     * @param status the status of the boat
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Getter for the estimated time that the boat will take to get to the next mark
     * @return The estimated time to the next mark
     */
    public long getTimeToNextMark() {
        return timeToNextMark;
    }

    /**
     * Setter for the estimated time that the boat will take to get to the next mark
     * @param timeToNextMark The estimated time to the next mark
     */
    public void setTimeToNextMark(long timeToNextMark) {
        this.timeToNextMark = timeToNextMark;
    }

    /**
     * Getter for the estimated time that the boat will take to finish
     * @return The estimated time to finish
     */
    public long getTimeToFinish() {
        return timeToFinish;
    }

    /**
     * Setter for the estimated time that the boat will take to finish
     * @param timeToFinish The estimated time to finish
     */
    public void setTimeToFinish(long timeToFinish) {
        this.timeToFinish = timeToFinish;
    }
}

