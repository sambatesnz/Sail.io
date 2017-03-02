package seng302;

import java.util.ArrayList;

/**
 * Created by tjg73 on 2/03/17.
 */
public class Race {

    private ArrayList<Boat> currentBoats = new ArrayList<>();

    /**
     * Display the current Boats names that are in the race to std output
     */
    public void displayNames() {
        if (currentBoats.size() > 0) {
            System.out.println("Starting boats in the race:");
            for (Boat boat : currentBoats) {
                System.out.println(boat.getBoatName());
            }
        }else {
            System.out.println("No boats in race");
        }
    }

    /**
     * Adds a boat to the race
     * @param newBoat Boat to add to race
     */
    public void addBoat(Boat newBoat){
        this.currentBoats.add(newBoat);
    }
}
