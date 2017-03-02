package seng302;

import java.util.ArrayList;

/**
 * Created by osr13 on 3/03/17.
 */
public class Regatta {

    ArrayList<Boat> competitors = new ArrayList<>();


    /**
     * If the Regatta is the America's Cup, add the correct boats.
     */
    public void isAC35() {
        Boat b1 = new Boat("ORACLE TEAM USA");
        Boat b2 = new Boat("Artemis Racing");
        Boat b3 = new Boat("Emirates Team New Zealand");
        Boat b4 = new Boat("Groupama Team France");
        Boat b5 = new Boat("Land Rover BAR");
        Boat b6 = new Boat("SoftBank Team Japan");
        addCompetitor(b1);
        addCompetitor(b2);
        addCompetitor(b3);
        addCompetitor(b4);
        addCompetitor(b5);
        addCompetitor(b6);
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
}
