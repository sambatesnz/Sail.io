package seng302.RaceObjects;

import javafx.scene.paint.Color;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;
import static java.lang.Math.floorMod;
import static java.lang.Math.round;

/**
 * Represent a boat competing in yacht race
 */
public class Boat extends GenericBoat {
    private static final int HEADING_INCREMENT = 3;

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
    private boolean sailsOut = false;
    private double size;
    private int agarSize;
    private int lives;

    private boolean upwindMemory = false;
    private boolean downwindMemory = false;
    private boolean plusMemory = false;
    private boolean isInCollision = false;

    private Thread turningThread;
    private Boolean stopTurnThread = false;

    private int targetMarkIndex = 0;
    private int lastMarkIndex = 0;
    private int roundingStage = 0;


    private boolean finished;
    private long finishTime;
    private int placement;

    private boolean connected;
    private boolean added;

    /**
     * Boat constructor
     * @param name the name of the boat
     * @param shortName e.g. NZL
     * @param sourceId the unique boat id
     * @param country e.g. New Zealand
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
        this.finished = false;
        this.connected = true;
        this.size = 18;
    }

    /**
     * Used to create a boat for testing purposes.
     * @param sourceID  boat source id
     */
    public Boat(Integer sourceID, String boatName) {
        this.sourceId = sourceID;
        this.boatName = boatName;
        this.finished = false;
    }

    /**
     * Tacks or Gybes the boat depending on which way the boat is oriented to the wind. Calling this
     * function while the boat is already tacking or gybing will stop the last tack or gybe instead.
     * @param windDirection The direction the wind is currently coming from
     */
    public void tackOrGybe(int windDirection) {
        boolean isClockwise = true;
        double headingDif = (360 + heading - windDirection) % 360;
        double finalHeading = heading;
        if (headingDif < 90) {      // Tack counter-clockwise
            finalHeading = windDirection + 360 - headingDif;
            isClockwise = false;
        } else if (headingDif > 270 && headingDif < 360) { // Tack clockwise
            finalHeading = windDirection + 360 - headingDif;
            isClockwise = true;
        } else if (headingDif > 90 && headingDif < 180) {   // Gybe clockwise
            finalHeading = windDirection + 180 + (180 - headingDif);
            isClockwise = true;
        } else if (headingDif < 270 && headingDif > 180) {  // Gybe counter-clockwise
            finalHeading = windDirection + 180 - (headingDif - 180);
            isClockwise = false;
        }
        finalHeading = (360 + finalHeading) % 360;

        updateStopTurnThread();

        turnBoat(isClockwise, finalHeading);

    }

    /**
     * Increments or decrements the boat heading by a set amount (currently 3 degrees but default)
     * towards or away from the current wind direction based on the command upwind, or downwind.
     * @param windDirection The current direction that the wind is coming from
     * @param upwind Whether to increment the heading towards (true) or away
     *               from (false) the current wind direction
     */
    public void updateHeading(int windDirection, boolean upwind) {
        updateStopTurnThread();

        double headingMinusWind = (360 + heading - windDirection) % 360;
        if (upwind) {   // turning upwind
            downwindMemory = false;
            if (upwindMemory && heading == windDirection) {
                if (plusMemory) {
                    heading += HEADING_INCREMENT;
                } else {
                    heading -= HEADING_INCREMENT;
                }
                upwindMemory = false;
            }
            if (headingMinusWind > 180) {
                heading += HEADING_INCREMENT;
                upwindMemory = true;
                plusMemory = true;
            } else {
                heading -= HEADING_INCREMENT;
                upwindMemory = true;
                plusMemory = false;
            }
        } else {        // turning downwind
            upwindMemory = false;
            if (downwindMemory && heading == windDirection) {
                if (plusMemory) {
                    heading += HEADING_INCREMENT;
                } else {
                    heading -= HEADING_INCREMENT;
                }
                downwindMemory = false;
            }
            if (headingMinusWind > 180) {
                heading -= HEADING_INCREMENT;
                downwindMemory = true;
                plusMemory = false;
            } else {
                heading += HEADING_INCREMENT;
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

    /**
     * Sets the boat to the current VMG depending on the boats current heading and the wind direction
     * Function will stop any current turning action when first pressed, then a consecutive press will activate VMG
     * @param windHeading The current direction that the wind is coming from
     */
    public void setHeadingToVMG(int windHeading) {
        final double VMG_UPWIND = 180 - 105;  //needs to be 180 - vmg because of wind direction
        final double VMG_DOWNWIND = 180 - 60;
        boolean isClockwise = true;
        double finalHeading = heading;
        int relativeAngle = getRelativeAngle(windHeading, heading);

        if (relativeAngle < 85) {
            finalHeading = (double) Math.floorMod((int) (windHeading + VMG_UPWIND), 360);
            isClockwise = getRelativeAngle((int)finalHeading, heading) >= 180;
        }else if (relativeAngle > 95 && relativeAngle < 180){
            finalHeading = (double) Math.floorMod((int)(windHeading + VMG_DOWNWIND), 360);
            isClockwise = getRelativeAngle((int)finalHeading, heading) >= 180;
        }else if (relativeAngle > 180 && relativeAngle < 265){
            finalHeading = (double) Math.floorMod((int)(windHeading - VMG_DOWNWIND), 360);
            isClockwise = getRelativeAngle((int)finalHeading, heading) >= 180;
        }else if (relativeAngle > 275){
            finalHeading = (double) Math.floorMod((int)(windHeading - VMG_UPWIND), 360);
            isClockwise = getRelativeAngle((int)finalHeading, heading) >= 180;
        }
        updateStopTurnThread();
        turnBoat(isClockwise, finalHeading);
    }

    @Override
    public double getCollisionFactor() {
        return 0;
    }

    @Override
    public long getLastAgarSizeDecreaseTime() {
        return 0;
    }

    @Override
    public void setLastAgarSizeDecreaseTime(long time) {

    }

    @Override
    public void resetAgarSize() {

    }

    private int getRelativeAngle(int angle1, double angle2){
        return Math.floorMod(((int)angle2 - angle1), 360);
    }


    private void turnBoat(boolean isClockwise, double finalHeading) {

        turningThread = new Thread("Boat Turning") {
            public void run() {
                if (isClockwise) {
                    while ((heading < finalHeading - 2 || heading > finalHeading + 2) && !stopTurnThread) {
                        heading += HEADING_INCREMENT;
                        if (heading > 360) {
                            heading -= 360;
                        }
                        headingChanged = true;
                        try {
                            Thread.sleep(17);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    while ((heading > finalHeading + 2 || heading < finalHeading - 2) && !stopTurnThread) {
                        heading -= HEADING_INCREMENT;
                        if (heading < 0) {
                            heading += 360;
                        }
                        headingChanged = true;
                        try {
                            Thread.sleep(17);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(!stopTurnThread){
                    heading = finalHeading; // Sets heading to final heading. This is needed because the code above only gets within 2.
                }
            }
        };
        turningThread.start();
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

    private void updateStopTurnThread(){
        stopTurnThread = turningThread != null && turningThread.isAlive();
    }

    public void setHeadingChangedToFalse() {
        this.headingChanged = false;
    }

    public boolean isSailsOut() {
        return sailsOut;
    }

    public void setSailsOut(boolean sailsOut) {
        this.sailsOut = sailsOut;
    }

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

    public void setMark(Mark mark) {
        this.mark = mark;
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
     * @param heading fff
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

    @Override
    public void loseLife() {
        //Noops
    }

    @Override
    public int getLives() {
        //Noops
        return 0;
    }

    @Override
    public boolean isEliminated() {
        //Noops
        return false;
    }

    @Override
    public int getAgarSize() {
        //Noops
        System.out.println("this shouldn't be called please send help");
        return 0;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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

    public String toString(){
        return "Boat(" + boatName + ")";
    }

    public int getTargetMarkIndex() {
        return targetMarkIndex;
    }

    public int getLastMarkIndex() {
        return lastMarkIndex;
    }

    public void passMark() {
        targetMarkIndex++;
        lastMarkIndex = targetMarkIndex - 1;
    }

    public int getRoundingStage() {
        return roundingStage;
    }

    public void resetRoundingStage() {
        roundingStage = 0;
    }

    public void updateRoundingStage() {
        roundingStage++;
    }

    public boolean isInCollision() {
        return isInCollision;
    }

    public void setInCollision(boolean inCollision) {
        isInCollision = inCollision;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Puts a boat into a disconnected state
     */
    public void disconnect() {
        this.connected = false;
        this.sailsOut = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }


    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public int getPlacement() {
        return placement;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public String getFinishTimeString() {
        if(!finished) return "DNF";
        long time = finishTime;
        int raceHours = (int) TimeUnit.MILLISECONDS.toHours(time);
        int raceMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(time) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)));
        int raceSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
        return String.format(" %02d:%02d:%02d", raceHours, raceMinutes, raceSeconds);
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

//    public int getAgarSize() {
//        return agarSize;
//    }

    public void setAgarSize(int agarSize) {
        this.agarSize = agarSize;
    }

//    public int getLives() {
//        return lives;
//    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}

