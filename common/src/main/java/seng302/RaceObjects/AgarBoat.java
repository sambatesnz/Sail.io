package seng302.RaceObjects;

/**
 * Boat to use for Agar mode, which has lives, size, and a base speed.
 */
public class AgarBoat extends BoatDecorator {

    private boolean eliminated = false;
    private int lives;
    private int agarSize;

    public AgarBoat(BoatInterface boat) {
        super(boat);

        lives = 3;
        agarSize = 1;
    }

    public void setSpeed(int speed) {
        int totalSpeed = speed;
        super.setSpeed(totalSpeed);
    }

    public void loseLife() {
        lives = lives - 1;
        resetAgarSize();
        if (lives < 1) {
            setEliminated();
        }
    }

    private void resetAgarSize() {
        agarSize = 1;
    }

    public int getAgarSize() {
        return agarSize;
    }

    public int getLives() {
        return lives;
    }

    private void setEliminated() {
        eliminated = true;
    }

    public boolean isEliminated() {
        return eliminated;
    }



}
