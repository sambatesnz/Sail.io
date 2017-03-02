package seng302;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by tjg73 on 2/03/17.
 */
public class Race {

    private ArrayList<Boat> racingBoats = new ArrayList<>();
    private ArrayList<Event> raceEvents = new ArrayList<>();

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
     * Adds the boats in the current race from the regatta
     * @param numberOfBoats Number of boats to be added from the current regatta to the race
     * @param competitors List of current boats in the regatta
     */
    public void addRacingBoats(int numberOfBoats, ArrayList<Boat> competitors){
        Random random = new Random();
        if (numberOfBoats <= competitors.size()){
            for (int i = 0; i < numberOfBoats; i++){
                int nextBoatToAdd = random.nextInt(competitors.size());
                racingBoats.add(competitors.get(nextBoatToAdd));
                competitors.remove(nextBoatToAdd);
            }
        }
    }

    /**
     * Randomly selects a race winner, from the boats competing in the race.
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

//    public void () {
//
//    }

}
