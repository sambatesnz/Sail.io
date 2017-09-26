package seng302.RaceObjects;

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

    public AgarBoat(GenericBoat boat) {
        super(boat);
        lives = 3;
        agarSize = BASE_AGAR_SIZE;
        eliminated = false;
    }
    public void setAgarSize(int size){
        this.agarSize = size;
    }

    public void setSpeed(int speed) {
        int totalSpeed = speed + baseSpeed;
        super.setSpeed(totalSpeed);
    }

    public void disconnect(){
        System.out.println("Actually I am an agar boat! disconnect myself here");
        boat.disconnect();
        boat.haltBoat();
        setEliminated();
        while (!isEliminated()) {
            loseLife();
        }
        setLives(0);
        boat.setLives(0); //its unclear where one should be setting lives
        lives = 0;

        System.out.println(boat.getSourceId() + " elimination status - " + isEliminated());
    }

    public void loseLife() {
        lives = lives - 1;
        resetAgarSize();
        if (lives < 1) {
            System.out.println(boat.getSourceId() + " <- I have been eliminated");
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
        return getAgarSize() * getSpeed();
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
