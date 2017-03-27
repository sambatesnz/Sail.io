package seng302.Model;

import java.util.ArrayList;

/**
 * Created by osr13 on 2/03/17.
 */
public class Boat {
    private String boatName;
    private float boatSpeed = 0;
    private int currentLeg = 0;
    private CompoundMark currentPosition;
    private CompoundMark destinationMark;
    private String shorthandName;

    /**
     * Basic Constructor for Boat objects.
     * @param name
     * @param speed
     */
    public Boat(String name, float speed, String shorthandName) {
        this.boatName = name;
        this.boatSpeed = speed;
        this.shorthandName = shorthandName;
        this.currentPosition = new CompoundMark("currentPosition", -1);
        this.currentPosition.addMark(0,0);
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

    public String getShorthandName() {
        return shorthandName;
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
}
