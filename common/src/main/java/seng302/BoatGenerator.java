package seng302;

import seng302.RaceObjects.GenericBoat;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Stack;

/**
 * Generates boats dynamically while tracking their source id
 */
public class BoatGenerator {

    private static int LOWEST_SOURCE_ID = 101;
    private static int HIGHEST_SOURCE_ID = 120;
    private static int MAX_AMOUNT_OF_BOATS =20;
    private int sourceId;
    private int numberOfBoats = 0;
    private HashSet addedBoats;

    private Stack<GenericBoat> availableBoats;
    private HashSet<GenericBoat> usedBoats;


    /**
     * Constructor for the boat generator
     */
    public BoatGenerator() {
        this.sourceId = LOWEST_SOURCE_ID;
        addedBoats = new HashSet();
        availableBoats = BoatInfo.createBoats(LOWEST_SOURCE_ID, MAX_AMOUNT_OF_BOATS);
        usedBoats = new HashSet();
    }


    /**
     * Tries to generate a boat
     * @return generated boat
     * @throws Exception if it cannot generate a boat
     */
    public GenericBoat generateBoat() throws Exception {
        GenericBoat boat;
        try {
            boat = availableBoats.pop();
            usedBoats.add(boat);
        } catch (EmptyStackException e){
            throw new Exception("Max Amount of boats generated");
        }
        return boat;
    }

    public int getLowestSourceId(){
        return LOWEST_SOURCE_ID;
    }

    /**
     * Makes a boat available for use
     * Used when someone disconnects
     * @param boat boat you wish to make available
     * @return Whether it has been successfully made available
     */
    public boolean makeAvailable(GenericBoat boat) {
        availableBoats.push(boat);
        return usedBoats.remove(boat);
    }
}
