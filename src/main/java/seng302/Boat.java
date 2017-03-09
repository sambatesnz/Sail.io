package seng302;

/**
 * Created by osr13 on 2/03/17.
 */
public class Boat {
    private String boatName;
    private int raceEvent = 0;
    private float boatSpeed = 0;


    public Boat(String name, float speed) {
        this.boatName = name;
        this.boatSpeed = speed;

    }

    public String getBoatName() {
        return boatName;
    }

    public float getBoatSpeed() {
        return boatSpeed;
    }

}
