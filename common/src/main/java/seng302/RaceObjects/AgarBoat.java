package seng302.RaceObjects;

/**
 * Boat to use for Agar mode, which has lives, size, and a base speed.
 */
public class AgarBoat extends BoatDecorator{

    private boolean eliminated = false;
    private int lives;
    private int size;

    public AgarBoat(Boat boat) {
        super(boat);

        lives = 3;
        size = 1;
    }

    public void setSpeed(int speed) {
        int totalSpeed = speed;
        super.setSpeed(totalSpeed);
    }

    public void loseLife() {
        lives = lives - 1;
        if (lives < 1) {
            setEliminated();
        }
    }

    public int getLives() {
        return lives;
    }

    private void setEliminated() {
        eliminated = true;
    }

    private boolean isEliminated() {
        return eliminated;
    }



}
