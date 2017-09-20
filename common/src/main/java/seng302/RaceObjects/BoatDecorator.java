package seng302.RaceObjects;

import javafx.scene.paint.Color;

/**
 * Decorator for the boat class
 */
public class BoatDecorator implements BoatInterface{

    protected BoatInterface boat;

    public BoatDecorator(BoatInterface boat) {
        this.boat = boat;
    }

    @Override
    public void setSpeed(int speed) {
        this.boat.setSpeed(speed);
    }

    @Override
    public void loseLife() {
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public boolean isEliminated() {
        return false;
    }

    @Override
    public int getAgarSize() {
        return 0;
    }

    @Override
    public int getSourceId() {
        return 0;
    }

    @Override
    public double getLatitude() {
        return 0;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public double getHeading() {
        return 0;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public boolean isSailsOut() {
        return false;
    }

    @Override
    public boolean getHeadingChanged() {
        return false;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public Mark getMark() {
        return null;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isAdded() {
        return false;
    }

    @Override
    public void setAdded(boolean b) {

    }

    @Override
    public void setHeadingChangedToFalse() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public void setColour(Color color) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setFinished(boolean b) {

    }

    @Override
    public void setFinishTime(long l) {

    }

    @Override
    public void setPlacement(int size) {

    }

    @Override
    public double getSpeedInKnots() {
        return 0;
    }

    @Override
    public boolean isKnowsBoatLocation() {
        return false;
    }

    @Override
    public String getShortName() {
        return null;
    }

    @Override
    public Color getColour() {
        return null;
    }


}
