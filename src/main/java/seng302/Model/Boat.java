package seng302.Model;

/**
 * Created by osr13 on 2/03/17.
 */
public class Boat {
    private String boatName;
    private float boatSpeed = 0;
    private double latCord = 20.0;
    private double longCord = 20.0;


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

    public double getLatCord() {
        return latCord;
    }

    public void setLatCord(double latCord) {
        this.latCord = latCord;
    }

    public double getLongCord() {
        return longCord;
    }

    public void setLongCord(double longCord) {
        this.longCord = longCord;
    }
}
