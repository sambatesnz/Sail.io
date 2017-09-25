package seng302.RaceObjects;

/**
 * Boat to use for Agar mode, which has lives, size, and a base speed.
 */
public class AgarBoat extends BoatDecorator {

    private boolean eliminated = false;
    private int lives;
    private int agarSize;
    private int baseSpeed;
    private long lastAgarSizeDecreaseTime;

    private static final int BASE_AGAR_SIZE = 100;
    final int BASE_BOAT_SIZE = 32;


    public AgarBoat(BoatInterface boat) {
        super(boat);

        lives = 3;
        agarSize = BASE_AGAR_SIZE;
    }

    public void setAgarSize(int size) {
        this.agarSize = size;
        updateBoatCollisionSize();
    }

    public void updateBoatCollisionSize() {
        double size = BASE_BOAT_SIZE * Math.sqrt(agarSize / 100 / Math.PI);
        boat.setSize(size);
    }

    public void setSpeed(int speed) {
        int totalSpeed = speed + baseSpeed;
        super.setSpeed(totalSpeed);
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

    public double getCollisionFactor(){
        return getAgarSize() * (getSpeed() / 4);
    }

    public long getLastAgarSizeDecreaseTime() {
        return lastAgarSizeDecreaseTime;
    }

    public void setLastAgarSizeDecreaseTime(long time) {
        this.lastAgarSizeDecreaseTime = time;
    }

    public void setBaseSpeed() {
        baseSpeed = calculateBaseSpeed(agarSize);
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
