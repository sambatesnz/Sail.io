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
    private Set<GenericBoat> eliminatedBoats;
    private List<GenericBoat> boatsToSend;

    public BoatManager(){
        this.finishedBoats = new HashSet<>();
        this.eliminatedBoats = new HashSet<>();
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

    /**
     * Adds an eliminated boat
     * @param boat boat you wish to eliminate
     */
    public boolean addEliminatedBoat(GenericBoat boat) {
        boolean eliminatedBoatsHasChanged = false;
        if (boat.isEliminated()) {
            eliminatedBoatsHasChanged = eliminatedBoats.add(boat);
        }
        return eliminatedBoatsHasChanged;
    }


    public boolean hasABoatFinished() {
        return boatsToSend.size() > 0;
    }

    public GenericBoat getFinishedBoat() {
        return boatsToSend.remove(0);
    }

    public Set<GenericBoat> getEliminatedBoats() {
        return eliminatedBoats;
    }
}
