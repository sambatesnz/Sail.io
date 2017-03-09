package seng302;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by osr13 on 9/03/17.
 */
public class RegattaTest {

    @Test
    public void testAddCompetitorToRegatta() {
        Regatta kevinsRegatta = new Regatta();
        Boat b1 = new Boat("Kevin", 43);
        kevinsRegatta.addCompetitor(b1);
        ArrayList<Boat> boats = kevinsRegatta.getCompetitors();
        assertTrue(boats.get(0).equals(b1));
    }

    @Test
    public void testBoatName() {
        Regatta kevinsRegatta = new Regatta();
        Event kevinsEvent = new Event("KevinsMark", 5353, 123, 2332);
        kevinsRegatta.eventList.add(kevinsEvent);
        ArrayList<Event> events = kevinsRegatta.getEventList();
        assertTrue(events.get(0).equals(kevinsEvent));;
    }
}
