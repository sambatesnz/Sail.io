//package seng302;
//
//import org.junit.Test;
//import Boat;
//import seng302.objects.Event;
//import Race;
//import seng302.objects.Regatta;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by tjg73 on 9/03/17.
// */
//public class RaceTest {
//
//    @Test
//    public void testAddingBoatsToRace() {
//        Race testRace = new Race();
//
//        ArrayList<Boat> testBoats = new ArrayList<>();
//
//        testBoats.add(new Boat("ORACLE TEAM USA", 50));
//        testBoats.add(new Boat("Artemis Racing", 51));
//        testBoats.add(new Boat("Emirates Team New Zealand", 60));
//
//        testRace.addRacingBoats(2, testBoats);
//
//        assertEquals(2, testRace.getRacingBoats().size());
//    }
//
//    @Test
//    public void testSlowestBoatSpeedInRace() {
//        Race testRace = new Race();
//
//        ArrayList<Boat> testBoats = new ArrayList<>();
//
//        testBoats.add(new Boat("ORACLE TEAM USA", 50));
//        testBoats.add(new Boat("Artemis Racing", 51));
//        testBoats.add(new Boat("Emirates Team New Zealand", 60));
//
//        testRace.addRacingBoats(testBoats.size(), testBoats);
//
//        assertEquals(50, testRace.getSlowestBoatSpeed(), 1e-9);
//    }
//
//    @Test
//    public void testRaceAddEvent() {
//        Race kevinsRace = new Race();
//        Event kevinsEvent = new Event("KevEvent", 1, 2,3);
//        ArrayList<Event> kevsEvents = new ArrayList<>();
//        kevsEvents.add(kevinsEvent);
//        kevinsRace.addEvents(kevsEvents);
//        assertTrue(kevinsRace.getRaceEvents().get(0).equals(kevinsEvent));
//    }
//
//    @Test
//    public void testFinishOrder() {
//        Race testRace = new Race();
//        Regatta testRegatta = new Regatta();
//
//        ArrayList<String> finishOrder = new ArrayList<>();
//
//        finishOrder.add("SoftBank Team Japan");
//        finishOrder.add("Land Rover BAR");
//        finishOrder.add("Groupama Team France");
//        finishOrder.add("Emirates Team New Zealand");
//        finishOrder.add("Artemis Racing");
//        finishOrder.add("ORACLE TEAM USA");
//
//        testRegatta.isAC35();
//
//        testRace.addRacingBoats(6, testRegatta.getCompetitors());
//        testRace.addEvents(testRegatta.getEventList());
//        testRace.setRacePlaybackDuration(1);
//        testRace.reportEventPositions();
//
//        assertArrayEquals(finishOrder.toArray(), testRace.getFinishingOrder().toArray());
//    }
//
//}
