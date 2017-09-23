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
        boat.loseLife();
    }

    @Override
    public int getLives() {
        return boat.getLives();
    }

    @Override
    public boolean isEliminated() {
        return boat.isEliminated();
    }

    @Override
    public int getAgarSize() {
        return boat.getAgarSize();
    }

    @Override
    public int getSourceId() {
        return boat.getSourceId();
    }

    @Override
    public double getLatitude() {
        return boat.getLatitude();
    }

    @Override
    public double getLongitude() {
        return boat.getLongitude();
    }

    @Override
    public double getHeading() {
        return boat.getHeading();
    }

    @Override
    public int getSpeed() {
        return boat.getSpeed();
    }

    @Override
    public boolean isSailsOut() {
        return boat.isSailsOut();
    }

    @Override
    public boolean getHeadingChanged() {
        return boat.getHeadingChanged();
    }

    @Override
    public double getX() {
        return boat.getX();
    }

    @Override
    public double getY() {
        return boat.getY();
    }

    @Override
    public Mark getMark() {
        return boat.getMark();
    }

    @Override
    public boolean isFinished() {
        return boat.isFinished();
    }

    @Override
    public boolean isAdded() {
        return boat.isAdded();
    }

    @Override
    public void setAdded(boolean b) {
        boat.setAdded(b);
    }

    @Override
    public void setHeadingChangedToFalse() {
        boat.setHeadingChangedToFalse();
    }

    @Override
    public boolean isConnected() {
        return boat.isConnected();
    }

    @Override
    public void disconnect() {
        boat.disconnect();
    }

    @Override
    public double getSize() {
        return boat.getSize();
    }

    @Override
    public void setColour(Color color) {
        boat.setColour(color);
    }

    @Override
    public String getName() {
        return boat.getName();
    }

    @Override
    public void setFinished(boolean b) {
        boat.setFinished(b);
    }

    @Override
    public void setFinishTime(long l) {
        boat.setFinishTime(l);
    }

    @Override
    public void setPlacement(int size) {
        boat.setPlacement(size);
    }

    @Override
    public double getSpeedInKnots() {
        return boat.getSpeedInKnots();
    }

    @Override
    public boolean isKnowsBoatLocation() {
        return boat.isKnowsBoatLocation();
    }

    @Override
    public String getShortName() {
        return boat.getShortName();
    }

    @Override
    public Color getColour() {
        return boat.getColour();
    }

    @Override
    public int getTargetMarkIndex() {
        return boat.getTargetMarkIndex();
    }

    @Override
    public void setMark(Mark mapCenter) {
        boat.setMark(mapCenter);
    }

    @Override
    public int getCurrentLegIndex() {
        return boat.getCurrentLegIndex();
    }

    @Override
    public void setPosition(int position) {
        boat.setPosition(position);
    }

    @Override
    public int getPosition() {
        return boat.getPosition();
    }

    @Override
    public void setStatus(int status) {
        boat.setStatus(status);
    }

    @Override
    public void setCurrentLegIndex(int legNumber) {
        boat.setCurrentLegIndex(legNumber);
    }

    @Override
    public void passMark() {
        boat.passMark();
    }

    @Override
    public void setTimeToNextMark(long estTimeToNextMark) {
        boat.setTimeToNextMark(estTimeToNextMark);
    }

    @Override
    public void setTimeToFinish(long estTimeToFinish) {
        boat.setTimeToFinish(estTimeToFinish);
    }

    @Override
    public void setLives(int boatLives) {
        boat.setLives(boatLives);
    }

    @Override
    public void setAgarSize(int boatSize) {
        boat.setAgarSize(boatSize);
    }

    @Override
    public void setHeading(double heading) {
        boat.setHeading(heading);
    }

    @Override
    public void setKnowsBoatLocation(boolean b) {
        boat.setKnowsBoatLocation(b);
    }

    @Override
    public void setSailsOut(boolean sailOut) {
        boat.setSailsOut(sailOut);
    }

    @Override
    public long getTimeToNextMark() {
        return boat.getTimeToNextMark();
    }

    @Override
    public long getTimeToFinish() {
        return boat.getTimeToFinish();
    }

    @Override
    public int getLastMarkIndex() {
        return boat.getLastMarkIndex();
    }

    @Override
    public void resetRoundingStage() {
        boat.resetRoundingStage();
    }

    @Override
    public int getRoundingStage() {
        return boat.getRoundingStage();
    }

    @Override
    public void updateRoundingStage() {
        boat.updateRoundingStage();
    }

    @Override
    public String getBoatName() {
        return boat.getBoatName();
    }

    @Override
    public String getCountry() {
        return boat.getCountry();
    }

    @Override
    public void tackOrGybe(int windHeading) {
        boat.tackOrGybe(windHeading);
    }

    @Override
    public void updateHeading(int windHeading, boolean upwind) {
        boat.updateHeading(windHeading, upwind);
    }

    @Override
    public void setHeadingToVMG(int windHeading) {
        boat.setHeadingToVMG(windHeading);
    }

    @Override
    public double getCollisionFactor() {
        return boat.getCollisionFactor();
    }

    @Override
    public long getLastAgarSizeDecreaseTime() {
        return boat.getLastAgarSizeDecreaseTime();
    }

    @Override
    public void setLastAgarSizeDecreaseTime(long time) {
        boat.setLastAgarSizeDecreaseTime(time);
    }

    @Override
    public void setBaseSpeed() {
        boat.setBaseSpeed();
    }
    @Override
    public void resetAgarSize() {
        boat.resetAgarSize();
    }

    @Override
    public int getBaseSpeed() {
        return boat.getBaseSpeed();
    }

    @Override
    public void setSize(double size) {
        boat.setSize(size);
    }

}
