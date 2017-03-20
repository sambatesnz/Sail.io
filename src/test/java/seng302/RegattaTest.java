//package seng302;
//
//import org.junit.Test;
//import seng302.objects.Boat;
//import seng302.objects.Event;
//import seng302.objects.Regatta;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by osr13 on 9/03/17.
// */
//public class RegattaTest {
//
//    @Test
//    public void testAddCompetitorToRegatta() {
//        Regatta kevinsRegatta = new Regatta();
//        Boat b1 = new Boat("Kevin", 43);
//        kevinsRegatta.addCompetitor(b1);
//        ArrayList<Boat> boats = kevinsRegatta.getCompetitors();
//        assertTrue(boats.get(0).equals(b1));
//    }
//
//    @Test
//    public void testAddEventToRegatta() {
//        Regatta kevinsRegatta = new Regatta();
//        Event kevinsEvent = new Event("KevinsMark", 5353, 123, 2332);
//        kevinsRegatta.eventList.add(kevinsEvent);
//        ArrayList<Event> events = kevinsRegatta.getEventList();
//        assertTrue(events.get(0).equals(kevinsEvent));
//    }
//
//
//    @Test
//    public void testAC35Boats() {
//        Regatta kevinsRegatta = new Regatta();
//        kevinsRegatta.isAC35();
//        assertTrue(kevinsRegatta.getCompetitors().get(0).getBoatName().equals("ORACLE TEAM USA"));
//    }
//
//
//
//    @Test
//    public void testAC35Events() {
//        Regatta kevinsRegatta = new Regatta();
//        kevinsRegatta.isAC35();
//        assertTrue(kevinsRegatta.getEventList().get(kevinsRegatta.eventList.size()-1).getEventName().equals("Finish"));
//    }
//}
