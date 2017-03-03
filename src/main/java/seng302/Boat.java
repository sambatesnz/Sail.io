package seng302;

/**
 * Created by osr13 on 2/03/17.
 */
public class Boat {
    private String boatName;
    private int raceEvent = 0;

    public Boat(String name) {
        boatName = name;
    }

    public String getBoatName() {
        return boatName;
    }

    public void iterateEvent() {
        this.raceEvent++;
    }

    public int getRaceEvent() {
        return raceEvent;
    }

    public void setRaceEvent(int raceEvent) {
        this.raceEvent = raceEvent;
    }
}
