package seng302;

import java.util.ArrayList;

/**
 * Created by osr13 on 3/03/17.
 */
public class Regatta {

    ArrayList<Boat> competitors = new ArrayList<>();
    ArrayList<Event> eventList = new ArrayList<>();
    private float totalRaceDistance;


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
        eventList.add(new Event("Gate 1, Leeward", 2, 2, 205));
        eventList.add(new Event("Gate 2, Windward", 3, 3, 25));
        eventList.add(new Event("Gate 3, Leeward", 4, 3, 310));
        eventList.add(new Event("Finish", 5, 1, 0));

        for (Event event : eventList) {
            this.totalRaceDistance += event.distToPrevEvent;
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
