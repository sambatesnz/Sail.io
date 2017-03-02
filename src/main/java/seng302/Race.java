package seng302;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
            System.out.println("#############################################" + "\n" + "\n" + "Starting boats in the race:");
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


    /**
     * Randomly selects a race winner
     */
    public int generateRandomWinnerIndex() {
        Random winner = new Random();
        int winnerIndex = winner.nextInt(2);
        return winnerIndex;
    }

    /**
     * Randomly selects a race winner
     */
    public void returnFinishOrder() {
        // Clone it, so we don't change the underlying original Boat list.
        ArrayList<Boat> clonedBoats = new ArrayList<Boat>(currentBoats);
        Collections.shuffle(clonedBoats);
        System.out.println("\n" + "#############################################" + "\n" + "\n" + "The race finish order is:");
        for (int i = 0; i < clonedBoats.size(); i++) {
            System.out.println(i + 1 + ": " + clonedBoats.get(i).getBoatName());
        }
    }

}
