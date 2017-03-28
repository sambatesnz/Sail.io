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
    public boolean hasFinished;


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
        this.hasFinished = false;
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
     * takes the distance the boat has travelled and calculates a new lat/long
     * position based on the currentPosition and the heading to find a new position
     * http://www.movable-type.co.uk/scripts/latlong.html maths from here
     * @param metersTravelled distance the boat has travelled along its heading
     */
    public void updateCurrentPosition(double metersTravelled){

        CompoundMark.Point currentMark = currentPosition.getCompoundMarks().get(0);
        double currentLat = currentMark.getLatitude();
        currentLat = Math.toRadians(currentLat);
        double currentLong = currentMark.getLongitude();
        currentLong = Math.toRadians(currentLong);
        double radiansHeading = Math.toRadians(currentHeading);

        double angularDistance = metersTravelled / 6371000; // divide by radius of earth
        double newLat = Math.asin(Math.sin(currentLat) * Math.cos(angularDistance) + Math.cos(currentLat) * Math.sin(angularDistance)*Math.cos(radiansHeading));
        double newLong = currentLong + Math.atan2(Math.sin(radiansHeading)*Math.sin(angularDistance)*Math.cos(currentLat), Math.cos(angularDistance) - Math.sin(currentLat) * Math.sin(newLat));

        newLat = Math.toDegrees(newLat);
        newLong = Math.toDegrees(newLong);

        currentMark.setLongitude(newLong);
        currentMark.setLatitude(newLat);
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
