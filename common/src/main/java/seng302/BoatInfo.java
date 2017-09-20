package seng302;

import seng302.RaceObjects.Boat;
import seng302.RaceObjects.BoatInterface;
import seng302.RaceObjects.Mark;

import java.util.*;

/**
 * Class containing static boat information
 */
final class BoatInfo {
    private static List<String> boatNames = new ArrayList<>(Arrays.asList("HMS Endeavour", "The Black Pearl", "Santa Maria", "Napoleon",
            "La Pinta","HMAV Bounty","Mayflower", "Golden Hind", "Cutty Sark", "Mary Celeste", "Queen Anne's Revenge",
            "CSS Alabama", "SS Great Britain","Boat 14",
            "Boat 15",
            "Boat 16",
            "Boat 17",
            "Boat 18",
            "Boat 19",
            "Boat 20"));

    private BoatInfo(){}

    /**
     * Creates a stack of boats
     * @return stack of boats
     */
    static Stack<BoatInterface> createBoats(int lowestSourceId, int amount) {
        int sourceId = lowestSourceId + amount-1;
        Map<String, List<String>> dataMap = getBoatMap();
        Stack<BoatInterface> boats = new Stack();
        int numberOfBoats = boatNames.size();
        for (int i = amount-1; i>-1 ; i--) { //So we put nice boat names on top
            String boatName = boatNames.get(i);
            List<String> boatData = dataMap.get(boatName);
            Boat boat = new Boat(boatName, boatData.get(1), sourceId, boatData.get(0));
            boat.setHeading(180);
            Mark mark = new Mark(getRandomDouble(57.6727450, 57.6714450),
                    getRandomDouble(11.8316340, 11.8255920));
            boat.setMark(mark);
            boats.add(boat);
            sourceId--;
        }
        return boats;
    }


    private static Map<String, List<String>> getBoatMap(){
        Map<String, List<String>> dataMap = new HashMap<>();
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
        dataMap.put("Boat 14", new ArrayList<>(Arrays.asList("Boat 14 Land", "B14")));
        dataMap.put("Boat 15", new ArrayList<>(Arrays.asList("Boat 15 Land", "B15")));
        dataMap.put("Boat 16", new ArrayList<>(Arrays.asList("Boat 16 Land", "B16")));
        dataMap.put("Boat 17", new ArrayList<>(Arrays.asList("Boat 17 Land", "B17")));
        dataMap.put("Boat 18", new ArrayList<>(Arrays.asList("Boat 18 Land", "B18")));
        dataMap.put("Boat 19", new ArrayList<>(Arrays.asList("Boat 19 Land", "B19")));
        dataMap.put("Boat 20", new ArrayList<>(Arrays.asList("Boat 20 Land", "B20")));
        return dataMap;
    }

    private static double getRandomDouble(double max, double min) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
