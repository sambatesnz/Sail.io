package seng302;

import java.util.*;

/**
 * Class containing static boat information
 */
public final class BoatInfo {

    private BoatInfo(){
    }

    private static List<String> boatNames = new ArrayList<>(Arrays.asList("HMS Endeavour", "The Black Pearl", "Santa Maria", "Napoleon",
            "La Pinta","HMAV Bounty","Mayflower", "Golden Hind", "Cutty Sark", "Mary Celeste", "Queen Anne's Revenge",
            "CSS Alabama", "SS Great Britain","Boat 14",
            "Boat 15",
            "Boat 16",
            "Boat 17",
            "Boat 18",
            "Boat 19",
            "Boat 20"));

    public static List<String> getBoatNames(){
        return boatNames;
    }


    public static Map<String, List<String>> getBoatMap(){
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
}
