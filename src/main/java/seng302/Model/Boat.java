package seng302.Model;

import java.util.ArrayList;

/**
 * Created by osr13 on 2/03/17.
 */
public class Boat {
    private String boatName;
    private float boatSpeed = 0; // in km/h
    private int currentLeg = 0;
    private double currentHeading = 0.0; // in degrees
    private CompoundMark currentPosition;
    private CompoundMark destinationMark;


    /**
     * Basic Constructor for Boat objects.
     * @param name
     * @param speed
     */
    public Boat(String name, float speed) {
        this.boatName = name;
        this.boatSpeed = speed;
        this.currentPosition = new CompoundMark("currentPosition", -1);
        this.currentPosition.addMark(0,0);
    }

    /**
     * updates the current heading of the boat based on its current position and its destination position
     */
    public void updateHeading(){
        double currentLat = currentPosition.getCompoundMarks().get(0).getLatitude();
        currentLat = Math.toRadians(currentLat);
        double currentLong = currentPosition.getCompoundMarks().get(0).getLongitude();
        double destinationLat = destinationMark.getCompoundMarks().get(0).getLatitude();
        destinationLat = Math.toRadians(destinationLat);
        double destinationLong = destinationMark.getCompoundMarks().get(0).getLongitude();

        double longDifference = Math.toRadians(destinationLong - currentLong);
        double x = Math.cos(currentLat)*Math.sin(destinationLat)-Math.sin(currentLat)*Math.cos(destinationLat)*Math.cos(longDifference);
        double y = Math.sin(longDifference)*Math.cos(destinationLat);

        currentHeading = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    /**
     * increments the leg which the boat is currently on
     */
    public void incrementLeg(){
        this.currentLeg++;
    }

    public String getBoatName() {
        return boatName;
    }

    public float getBoatSpeed() {
        return boatSpeed;
    }

    public String toString(){
        return this.boatName;
    }

    public int getCurrentLeg() {
        return currentLeg;
    }

    public void setCurrentLeg(int currentLeg) {
        this.currentLeg = currentLeg;
    }

    public CompoundMark getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(CompoundMark currentPosition) {
        this.currentPosition = currentPosition;
    }

    public CompoundMark getDestinationMark() {
        return destinationMark;
    }

    public void setDestinationMark(CompoundMark destinationMark) {
        this.destinationMark = destinationMark;
    }

    public double getCurrentHeading() {
        return currentHeading;
    }
}
