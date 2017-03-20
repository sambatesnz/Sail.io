package seng302.Model;

/**
 * Created by osr13 on 2/03/17.
 */
public class Boat {
    private String boatName;
    private float boatSpeed = 0;


    /**
     * Basic Constructor for Boat objects.
     * @param name
     * @param speed
     */
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

    public String toString(){
        return this.boatName;
    }

}
