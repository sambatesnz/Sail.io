package seng302;

import seng302.RaceObjects.GenericBoat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages the state of boats
 */
public class BoatManager {

    private Set<GenericBoat> finishedBoats;
    private List<GenericBoat> boatsToSend;

    public BoatManager(){
        this.finishedBoats = new HashSet<>();
        this.boatsToSend = new ArrayList<>();
    }

    /**
     * Processes when a boat has finished the race after rounding the final mark
     * @param boat Boat that has finished
     */
    public void addFinishedBoat(GenericBoat boat) {
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

    public GenericBoat getFinishedBoat() {
        return boatsToSend.remove(0);
    }
}
