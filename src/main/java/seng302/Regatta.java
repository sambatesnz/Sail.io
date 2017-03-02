package seng302;

import java.util.ArrayList;

/**
 * Created by osr13 on 3/03/17.
 */
public class Regatta {

    ArrayList<Boat> competitors = new ArrayList<>();
    ArrayList<Event> eventList = new ArrayList<>();


    /**
     * If the Regatta is the America's Cup, add the correct boats.
     */
    public void isAC35() {
        Boat b2 = new Boat("Artemis Racing");
        Boat b3 = new Boat("Emirates Team New Zealand");
        Boat b4 = new Boat("Groupama Team France");
        Boat b5 = new Boat("Land Rover BAR");
        Boat b6 = new Boat("SoftBank Team Japan");
        addCompetitor(new Boat("ORACLE TEAM USA"));
        addCompetitor(new Boat("Artemis Racing"));
        addCompetitor(b3);
        addCompetitor(b4);
        addCompetitor(b5);
        addCompetitor(b6);

        eventList.add(new Event("Start", 0));
        eventList.add(new Event("Mark 1", 1));
        eventList.add(new Event("Gate 1, Leeward", 2));
        eventList.add(new Event("Gate 2, Windward", 3));
        eventList.add(new Event("Gate 3, Leeward", 4));
        eventList.add(new Event("Finish", 5));
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
