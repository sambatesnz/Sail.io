package seng302.RaceObjects;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 * Basic interface for the boat class so that it can be extended via the decorator pattern
 */
public abstract class GenericBoat extends RecursiveTreeObject<GenericBoat> {

    public abstract void setSpeed(int speed);

    public abstract void loseLife();

    public abstract int getLives();

    public abstract boolean isEliminated();

    public abstract int getAgarSize();

    public abstract int getSourceId();

    public abstract double getLatitude();

    public abstract double getLongitude();

    public abstract double getHeading();

    public abstract int getSpeed();

    public abstract boolean isSailsOut();

    public abstract boolean getHeadingChanged();

    public abstract double getX();

    public abstract double getY();

    public abstract Mark getMark();

    public abstract boolean isFinished();

    public abstract boolean isAdded();

    public abstract void setAdded(boolean b);

    public abstract void setHeadingChangedToFalse();

    public abstract boolean isConnected();

    public abstract void disconnect();

    public abstract double getSize();

    abstract void setColour(Color color);

    public abstract StringProperty getName();

    public abstract void setFinished(boolean b);

    abstract void setFinishTime(long l);

    public abstract String getFinishTimeString();

    abstract void setPlacement(int size);

    public abstract int getPlacement();

    public abstract double getSpeedInKnots();

    public abstract boolean isKnowsBoatLocation();

    public abstract String getShortName();

    public abstract Color getColour();

    public abstract int getTargetMarkIndex();

    public abstract void setMark(Mark mark);

    public abstract int getCurrentLegIndex();

    public abstract void setPosition(int position);

    public abstract int getPosition();

    public abstract void setStatus(int status);

    public abstract void setCurrentLegIndex(int legNumber);

    public abstract void passMark();

    public abstract void setTimeToNextMark(long estTimeToNextMark);

    public abstract void setTimeToFinish(long estTimeToFinish);

    public abstract void setLives(int boatLives);

    public abstract void setAgarSize(int boatSize);

    public abstract void setHeading(double heading);

    public abstract void setKnowsBoatLocation(boolean b);

    public abstract void setSailsOut(boolean sailOut);

    public abstract long getTimeToNextMark();

    public abstract long getTimeToFinish();

    public abstract int getLastMarkIndex();

    public abstract void resetRoundingStage();

    public abstract int getRoundingStage();

    public abstract void updateRoundingStage();

    public abstract StringProperty getBoatName();

    public abstract StringProperty getCountry();

    public abstract void tackOrGybe(int windHeading);

    public abstract void updateHeading(int windHeading, boolean upwind);

    public abstract void setHeadingToVMG(int windHeading);

    abstract double getCollisionFactor();

    public abstract long getLastAgarSizeDecreaseTime();

    public abstract void setLastAgarSizeDecreaseTime(long time);

    public abstract void resetAgarSize();
}
