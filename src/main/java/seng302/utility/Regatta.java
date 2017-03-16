package seng302.utility;

import java.util.ArrayList;

/**
 * Created by osr13 on 3/03/17.
 */
public class Regatta {

    private ArrayList<Boat> competitors = new ArrayList<>();
    public ArrayList<Event> eventList = new ArrayList<>();
    public static float totalRaceDistance = 0;


    /**
     * If the Regatta is the America's Cup, add the correct boats/events.
     */
    public void isAC35() {
        addCompetitor(new Boat("ORACLE TEAM USA", 50));
        addCompetitor(new Boat("Artemis Racing", 55));
        addCompetitor(new Boat("Emirates Team New Zealand", 60));
        addCompetitor(new Boat("Groupama Team France", 65));
        addCompetitor(new Boat("Land Rover BAR", 70));
        addCompetitor(new Boat("SoftBank Team Japan", 75));

        eventList.add(new Event("Start", 0, 0, 100));
        eventList.add(new Event("Mark 1", 1, 1,25));
        eventList.add(new Event("Leeward Gate (1st Pass)", 2, 2, 205));
        eventList.add(new Event("Windward Gate", 3, 3, 25));
        eventList.add(new Event("Leeward Gate (2nd Pass)", 4, 3, 310));
        eventList.add(new Event("Finish", 5, 1, 0));

        for (Event event : eventList) {
            totalRaceDistance += event.distToPrevEvent;
        }
    }

    /**
     * Adds a competitor to the competitor list.
     * @param newBoat Boat to add to regatta
     */
    public void addCompetitor(Boat newBoat){
        this.competitors.add(newBoat);
    }

    public ArrayList<Boat> getCompetitors() {
        return competitors;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }
}
