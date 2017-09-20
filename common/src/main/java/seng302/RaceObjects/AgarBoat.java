package seng302.RaceObjects;

/**
 * Boat to use for Agar mode, which has lives, size, and a base speed.
 */
public class AgarBoat extends BoatDecorator {

    private int lives;
    private int agarSize;

    public AgarBoat(BoatInterface boat) {
        super(boat);
        lives = 3;
        agarSize = 1;
    }

    @Override
    public void setSpeed(int speed) {
        int totalSpeed = speed + 100000;
        super.setSpeed(totalSpeed);
    }

    @Override
    public void loseLife() {
        lives = lives - 1;
        resetAgarSize();
    }

    private void resetAgarSize() {
        agarSize = 1;
    }

    @Override
    public int getAgarSize() {
        return agarSize;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public boolean isEliminated() {
        return lives == 0;
    }



}
