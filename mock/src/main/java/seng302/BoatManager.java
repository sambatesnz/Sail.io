package seng302;

import seng302.RaceObjects.Boat;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Manages the state of boats
 */
public class BoatManager {

    private HashSet<Boat> finishedBoats;
    private ArrayList<Boat> boatsToSend;

    public BoatManager(){
        this.finishedBoats = new HashSet<>();
        this.boatsToSend = new ArrayList<>();
    }

    public void addFinishedBoat(Boat boat) {
        Boolean changed = finishedBoats.add(boat);
        if (changed) {
            boatsToSend.add(boat);

        }
    }




}
