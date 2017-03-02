package seng302;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by tjg73 on 2/03/17.
 */
public class Race {

    private ArrayList<Boat> racingBoats = new ArrayList<>();

    /**
     * Display the current Boats names that are in the race to std output
     */
    public void displayStarters() {
        if (racingBoats.size() > 0) {
            System.out.println("#############################################" + "\n" + "\n" + "Starting boats in the race:");
            for (Boat boat : racingBoats) {
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
    public void getRacingBoats(Regatta regatta){
        this.racingBoats.add(newBoat);
    }


    /**
     * Randomly selects a race winner
     */
    public void returnFinishOrder() {
        // Clone it, so we don't change the underlying original Boat list.
        ArrayList<Boat> clonedBoats = new ArrayList<>(racingBoats);
        Collections.shuffle(clonedBoats);
        System.out.println("\n" + "#############################################" + "\n" + "\n" + "The race finish order is:");
        for (int i = 0; i < clonedBoats.size(); i++) {
            System.out.println(i + 1 + ": " + clonedBoats.get(i).getBoatName());
        }
    }

}
