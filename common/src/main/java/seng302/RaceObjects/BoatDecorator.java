package seng302.RaceObjects;

/**
 * Decorator for the boat class
 */
public class BoatDecorator implements BoatInterface{

    protected Boat boat;

    public BoatDecorator(Boat boat) {
        this.boat = boat;
    }

    @Override
    public void setSpeed(int speed) {
        this.boat.setSpeed(speed);
    }

    @Override
    public void loseLife() {
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public boolean isEliminated() {
        return false;
    }

    @Override
    public int getAgarSize() {
        return 0;
    }


}
