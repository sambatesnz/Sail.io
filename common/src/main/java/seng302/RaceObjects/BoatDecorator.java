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


}
