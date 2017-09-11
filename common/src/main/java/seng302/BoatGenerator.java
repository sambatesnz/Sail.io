package seng302;

import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Mark;

import java.util.*;

/**
 * Generates boats dynamically while tracking their source id
 */
public class BoatGenerator {

    private static int LOWEST_SOURCE_ID = 101;
    private static int HIGHEST_SOURCE_ID = 120;
    private int sourceId;
    private int numberOfBoats = 0;
    private HashSet addedBoats;

    private Stack<Boat> availableBoats;
    private HashSet<Boat> usedBoats;


    public BoatGenerator() {
        this.sourceId = LOWEST_SOURCE_ID;
        addedBoats = new HashSet();
        availableBoats = generateAllBoats();
        usedBoats = new HashSet();
    }

    private Stack<Boat> generateAllBoats(){
        Map<String, List<String>> dataMap = BoatInfo.getBoatMap();
        Stack<Boat> boats = new Stack<>();
        int sourceId = HIGHEST_SOURCE_ID;
        for (int i = 19; i>-1 ; i--) { //So we put nice boat names on top
            String boatName = BoatInfo.getBoatNames().get(i);
            List<String> boatData = dataMap.get(boatName);
            Boat boat = new Boat(boatName, boatData.get(1), sourceId, boatData.get(0));
            boat.setHeading(180);
            Mark mark = new Mark(57.671335, 11.8271330 + numberOfBoats/1000.0);
            boat.setMark(mark);
            boats.add(boat);
            sourceId--;
        }
        return boats;
    }

    public Boat generateBoat() throws Exception {
        Boat boat;
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
    public boolean makeAvailable(Boat boat) {
        availableBoats.push(boat);
        return usedBoats.remove(boat);
    }
}
