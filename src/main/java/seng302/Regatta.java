package seng302;

import java.util.ArrayList;

/**
 * Created by osr13 on 3/03/17.
 */
public class Regatta {

    ArrayList<Boat> competitors = new ArrayList<>();
    ArrayList<Event> eventList = new ArrayList<>();


    /**
     * If the Regatta is the America's Cup, add the correct boats/events.
     */
    public void isAC35() {
        addCompetitor(new Boat("ORACLE TEAM USA"));
        addCompetitor(new Boat("Artemis Racing"));
        addCompetitor(new Boat("Emirates Team New Zealand"));
        addCompetitor(new Boat("Groupama Team France"));
        addCompetitor(new Boat("Land Rover BAR"));
        addCompetitor(new Boat("SoftBank Team Japan"));

        eventList.add(new Event("Start", 0, 0));
        eventList.add(new Event("Mark 1", 1, 1));
        eventList.add(new Event("Gate 1, Leeward", 2, 2));
        eventList.add(new Event("Gate 2, Windward", 3, 3));
        eventList.add(new Event("Gate 3, Leeward", 4, 3));
        eventList.add(new Event("Finish", 5, 1));
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
