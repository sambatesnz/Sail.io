package seng302.RaceObjects;

import javafx.scene.paint.Color;

/**
 * Basic interface for the boat class so that it can be extended via the decorator pattern
 */
public interface BoatInterface {

    void setSpeed(int speed);

    void loseLife();

    int getLives();

    boolean isEliminated();

    int getAgarSize();

    int getSourceId();

    double getLatitude();

    double getLongitude();

    double getHeading();

    int getSpeed();

    boolean isSailsOut();

    boolean getHeadingChanged();

    double getX();

    double getY();

    Mark getMark();

    boolean isFinished();

    boolean isAdded();

    void setAdded(boolean b);

    void setHeadingChangedToFalse();

    boolean isConnected();

    void disconnect();

    double getSize();

    void setColour(Color color);

    String getName();

    void setFinished(boolean b);

    void setFinishTime(long l);

    void setPlacement(int size);

    double getSpeedInKnots();

    boolean isKnowsBoatLocation();

    String getShortName();

    Color getColour();

    int getTargetMarkIndex();

    void setMark(Mark mark);

    int getCurrentLegIndex();

    void setPosition(int position);

    int getPosition();

    void setStatus(int status);

    void setCurrentLegIndex(int legNumber);

    void passMark();

    void setTimeToNextMark(long estTimeToNextMark);

    void setTimeToFinish(long estTimeToFinish);

    void setLives(int boatLives);

    void setAgarSize(int boatSize);

    void setHeading(double heading);

    void setKnowsBoatLocation(boolean b);

    void setSailsOut(boolean sailOut);

    long getTimeToNextMark();

    long getTimeToFinish();

    int getLastMarkIndex();

    void resetRoundingStage();

    int getRoundingStage();

    void updateRoundingStage();

    String getBoatName();

    String getCountry();

    void tackOrGybe(int windHeading);

    void updateHeading(int windHeading, boolean upwind);

    void setHeadingToVMG(int windHeading);

    double getCollisionFactor();

    long getLastAgarSizeDecreaseTime();

    void setLastAgarSizeDecreaseTime(long time);

    void setBaseSpeed();

    void resetAgarSize();

    int getBaseSpeed();

    void setSize(double size);
}
