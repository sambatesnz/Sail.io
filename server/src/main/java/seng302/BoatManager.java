package seng302;

import seng302.RaceObjects.Boat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages the state of boats
 */
public class BoatManager {

    private Set<Boat> finishedBoats;
    private List<Boat> boatsToSend;

    public BoatManager(){
        this.finishedBoats = new HashSet<>();
        this.boatsToSend = new ArrayList<>();
    }

    /**
     * Processes when a boat has finished the race after rounding the final mark
     * @param boat Boat that has finished
     */
    public void addFinishedBoat(Boat boat) {
        if (boat.isFinished()) {
            Boolean changed = finishedBoats.add(boat);
            if (changed) {
                boatsToSend.add(boat);
            }
        }
    }

    public boolean hasABoatFinished() {
        return boatsToSend.size() > 0;
    }

    public Boat getFinishedBoat() {
        return boatsToSend.remove(0);
    }
}
