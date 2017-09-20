package seng302.RaceObjects;

/**
 * Basic interface for the boat class so that it can be extended via the decorator pattern
 */
public interface BoatInterface {

    void setSpeed(int speed);

    void loseLife();

    int getLives();

    boolean isEliminated();

    int getAgarSize();
}
