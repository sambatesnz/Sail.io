package seng302;

import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Mark;

import java.util.*;

/**
 * Generates boats dynamically while tracking their source id
 */
public class BoatGenerator {

    private static int LOWEST_SOURCE_ID = 101;
    private int sourceId;
    private int numberOfBoats = 0;
    private List<String> boatNames = new ArrayList<>(Arrays.asList("HMS Endeavour", "The Black Pearl", "Santa Maria", "Napoleon",
            "La Pinta","HMAV Bounty","Mayflower", "Golden Hind", "Cutty Sark", "Mary Celeste", "Queen Anne's Revenge",
            "CSS Alabama", "SS Great Britain"));
    private Map<String, List<String>> dataMap = new HashMap<>();

    public BoatGenerator () {
        this.sourceId = LOWEST_SOURCE_ID;
        dataMap.put("HMS Endeavour", new ArrayList<>(Arrays.asList("Great Britain", "END")));
        dataMap.put("The Black Pearl", new ArrayList<>(Arrays.asList("Origin Unknown", "TBP")));
        dataMap.put("Santa Maria", new ArrayList<>(Arrays.asList("Spain", "SAN")));
        dataMap.put("Napoleon", new ArrayList<>(Arrays.asList("France", "NAP")));
        dataMap.put("La Pinta", new ArrayList<>(Arrays.asList("Spain", "PIN")));
        dataMap.put("HMAV Bounty", new ArrayList<>(Arrays.asList("Great Britain", "BTY")));
        dataMap.put("Mayflower", new ArrayList<>(Arrays.asList("Great Britain", "MFL")));
        dataMap.put("Golden Hind", new ArrayList<>(Arrays.asList("Great Britain", "GHD")));
        dataMap.put("Cutty Sark", new ArrayList<>(Arrays.asList("Great Britain", "CSK")));
        dataMap.put("Mary Celeste", new ArrayList<>(Arrays.asList("United States of America", "MCL")));
        dataMap.put("Queen Anne's Revenge", new ArrayList<>(Arrays.asList("PirateLand", "QAR")));
        dataMap.put("CSS Alabama", new ArrayList<>(Arrays.asList("United States of America", "ALB")));
        dataMap.put("SS Great Britain", new ArrayList<>(Arrays.asList("Great Britain", "SGB")));
    }

    public Boat generateBoat(){
        String boatName = boatNames.get(numberOfBoats % boatNames.size());
        List<String> boatData = dataMap.get(boatName);
        Boat boat = new Boat(boatName, boatData.get(1), sourceId, boatData.get(0));
        Mark mark = new Mark(57.670335, 11.8279330);
        boat.setMark(mark);
        sourceId++;
        numberOfBoats++;
        return boat;
    }

    public int getLowestSourceId(){
        return LOWEST_SOURCE_ID;
    }

}
