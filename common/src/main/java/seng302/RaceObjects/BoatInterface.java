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
}
