package seng302;

import javafx.scene.paint.Color;

/**
 * Represent a boat competing in yacht race
 */
public class Boat {
    private String name;
    private double speed;
    private double currentLegDistance;
    private int currentLegIndex;
    private double heading = 0;
    private double x;
    private double y;
    private Color colour;
    private Long raceTime;
    private int position;
    private String abrv;
    private int sourceID;

    private int timeToNextMark;
    private int timeToFinish;

    /**
     * Gets the abbreviation of the name of the team's boat
     * @return the boat's abbreviation string
     */
    public String getAbrv() {
        return abrv;
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
     * @param speed the speed of the boat
     */
    public Boat(String name, double speed, Color colour, String abrv) {
        this.name = name;
        this.currentLegDistance = 0;
        this.currentLegIndex = 0;
        this.speed = speed;
        this.colour = colour;
        this.abrv = abrv;
        this.raceTime = Integer.toUnsignedLong(0);
    }

    /**
     * Get the boats colour
     * @return the boats colour
     */
    public Color getColour() {
        return colour;
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
     * Set the current x. coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Set the current y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get the current x. coordinate
     * @return the current x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Get the current y coordinate
     * @return the current y coordinate
     */
    public double getY() {
        return y;
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
        return name;
    }

    /**
     * Get the relative position of the boat in the race
     * @return the position of the boat
     */
    public int getPosition() { return position; }

    /**
     * Sets the position of the boat.
     * @param position1
     */
    public void setPosition(int position1) { position = position1; }

    /**
     * Get the current latitude
     * @return the current latitude
     */
    public double getLatitude(){
        return Position.convertY(y);
    }

    /**
     * Get the current latitude
     * @return the current latitude
     */
    public double getLongitude(){
        return Position.convertX(x);
    }

    public void setSourceID(int id) { this.sourceID = id; }

    public int getSourceID() { return sourceID; }

    /**
     * Getter for the estimated time that the boat will take to get to the next mark
     * @return The estimated time to the next mark
     */
    public int getTimeToNextMark() {
        return timeToNextMark;
    }

    /**
     * Setter for the estimated time that the boat will take to get to the next mark
     * @param timeToNextMark The estimated time to the next mark
     */
    public void setTimeToNextMark(int timeToNextMark) {
        this.timeToNextMark = timeToNextMark;
    }

    /**
     * Getter for the estimated time that the boat will take to finish
     * @return The estimated time to finish
     */
    public int getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(int timeToFinish) {
        this.timeToFinish = timeToFinish;
    }
}

