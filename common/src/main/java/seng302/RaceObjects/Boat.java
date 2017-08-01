package seng302.RaceObjects;

import javafx.scene.paint.Color;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Math.abs;

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
    private int speed;               //mm/sec
    private boolean knowsBoatLocation;
    private boolean headingChanged;

    private boolean upwindMemory = false;
    private boolean downwindMemory = false;
    private boolean plusMemory = false;

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

    /**
     * Getter for the source ID, or the identification number, of the boat
     * @return The number that the boat is identified by
     */
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
        this.shortName = shortName;
        this.sourceId = sourceId;
        this.country = country;
        this.knowsBoatLocation = false;
        this.mark = new Mark();
        this.raceTime = Integer.toUnsignedLong(0);
        this.headingChanged = false;
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
    public int getSpeed() {
        return speed;
    }

    /**
     * Set the speed of the boat
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
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

    public boolean isKnowsBoatLocation() {
        return knowsBoatLocation;
    }

    public void setKnowsBoatLocation(boolean knowsBoatLocation) {
        this.knowsBoatLocation = knowsBoatLocation;
    }

    public Mark getMark() {
        return mark;
    }

    public boolean getHeadingChanged() {
        return headingChanged;
    }

    public void tackOrGybe(int windDirection) {

        double headingDif = (360 + heading - windDirection) % 360;
        double finalHeading;
        boolean addHeading;
        if (headingDif < 90) {      // Tack counter-clockwise
            finalHeading = 360 - headingDif;
            addHeading = false;
        } else if (headingDif > 270 && headingDif < 360) { // Tack clockwise
            finalHeading = 360 - headingDif;
            addHeading = true;
        } else if (headingDif > 90 && headingDif < 180) {   // Gybe clockwise
            finalHeading =
        }

    }

    /**
     * Increments or decrements the boat heading by a set amount (currently 3 degrees but default)
     * towards or away from the current wind direction based on the command upwind, or downwind.
     * @param windDirection The current direction that the wind is heading
     * @param upwind Whether to increment the heading towards (true) or away
     *               from (false) the current wind direction
     */
    public void updateHeading(int windDirection, boolean upwind) {
        int headingIncrement = 3;

        double headingMinusWind = (360 + heading - windDirection) % 360;
        if (!upwind) {   // turning upwind
            downwindMemory = false;
            if (upwindMemory && heading == windDirection) {
                if (plusMemory) {
                    heading += headingIncrement;
                } else {
                    heading -= headingIncrement;
                }
                upwindMemory = false;
            }
            if (headingMinusWind > 180) {
                heading += headingIncrement;
                upwindMemory = true;
                plusMemory = true;
            } else {
                heading -= headingIncrement;
                upwindMemory = true;
                plusMemory = false;
            }
        } else {        // turning downwind

            upwindMemory = false;
            if (downwindMemory && heading == windDirection) {
                if (plusMemory) {
                    heading += headingIncrement;
                } else {
                    heading -= headingIncrement;
                }
                downwindMemory = false;
            }
            if (headingMinusWind > 180) {
                heading -= headingIncrement;
                downwindMemory = true;
                plusMemory = false;
            } else {
                heading += headingIncrement;
                downwindMemory = true;
                plusMemory = true;
            }
        }
        if (heading > 359) {
            heading -= 360;
        } else if (heading < 0) {
            heading += 360;
        }
        this.headingChanged = true;
    }

    public void setHeadingChangedToFalse() {
        this.headingChanged = false;
    }


    /**
     * Converts and then returns current boat speed (originally in mm/sec) in knots.
     * @return boat speed in knots
     */
    public double getSpeedInKnots() {
        double val = speed * 1.9438444924574 / 1000;
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.valueOf(df.format(val));
    }
}

