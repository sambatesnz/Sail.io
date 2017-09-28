package seng302.RaceObjects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Boat to use for Agar mode, which has lives, size, and a base speed.
 */
public class AgarBoat extends BoatDecorator{

    private boolean eliminated;
    private int lives;
    private int agarSize;
    private int baseSpeed;
    private long lastAgarSizeDecreaseTime;

    private static final int BASE_AGAR_SIZE = 100;
    final int BASE_BOAT_SIZE = 32;

    public AgarBoat(GenericBoat boat) {
        super(boat);
        lives = 3;
        agarSize = BASE_AGAR_SIZE;
        eliminated = false;
    }

    public void setAgarSize(int size) {
        this.agarSize = size;
        updateBoatCollisionSize();
        setBaseSpeed();
    }

    public void updateBoatCollisionSize() {
        double size = BASE_BOAT_SIZE * Math.sqrt((double) agarSize / 100);
        boat.setSize(size);
    }

    public void setSpeed(int speed) {
        int totalSpeed = speed + baseSpeed;
        if (lives < 1) super.setSpeed(0);
        super.setSpeed(totalSpeed);
    }

    public void disconnect(){
        boat.disconnect();
        boat.haltBoat();
        setEliminated();
        while (!isEliminated()) {
            loseLife();
        }
        setLives(0);
        boat.setLives(0); //its unclear where one should be setting lives
        lives = 0;
    }

    public void loseLife() {
        lives = lives - 1;
        resetAgarSize();
        if (lives < 1) {
            setEliminated();
        }
    }

    public void resetAgarSize() {
        agarSize = BASE_AGAR_SIZE;
        updateBoatCollisionSize();
        setBaseSpeed();
        setSpeed(0);
    }

    public int getAgarSize() {
        return agarSize;
    }

    public int getLives() {
        return lives;
    }

    private void setEliminated() {
        eliminated = true;
        baseSpeed = 0;
}

    public boolean isEliminated() {
        return eliminated;
    }

    public double getCollisionFactor(){
        return getAgarSize() * (sqrt(pow(getSpeed(), 3)));
    }

    public long getLastAgarSizeDecreaseTime() {
        return lastAgarSizeDecreaseTime;
    }

    public void setLastAgarSizeDecreaseTime(long time) {
        this.lastAgarSizeDecreaseTime = time;
    }

    public void setBaseSpeed() {
        if (lives > 0) baseSpeed = calculateBaseSpeed(agarSize);
    }

    private static int calculateBaseSpeed(int boatSize) {
        double size = (double) boatSize/800;
        double baseSpeed = Math.log(size) * -10000;
        return (int) baseSpeed;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }
}
