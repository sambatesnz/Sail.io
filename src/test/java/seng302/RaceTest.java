//package seng302;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.paint.Color;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Tests the Race class
// */
//public class RaceTest {
//    @Test
//    public void testRaceFinishers() throws Exception{
//        Race race = new Race();
//
//        ArrayList<Boat> contestants = new ArrayList<>();
//        contestants.add(new Boat("Hummer", 5.8, Color.RED, "H1"));
//        contestants.add(new Boat("Swedish Racing", 7.1, Color.BLUE, "VLV"));
//        contestants.add(new Boat("All Blacks", 11.2, Color.BLACK, "NZL"));
//        contestants.add(new Boat("Le Baguettes", 6.7, Color.WHEAT, "BRD"));
//        contestants.add(new Boat("Mini Cooper", 7.6, Color.AQUAMARINE, "TEA"));
//        contestants.add(new Boat("Toyota Hilux", 9.3, Color.DARKSALMON, "MMM"));
//
//        race.setFinishedBoats(contestants);
//
//        ArrayList<String> stringList = new ArrayList<>();
//        stringList.add("Race Results");
//        stringList.add("1: Hummer");
//        stringList.add("2: Swedish Racing");
//        stringList.add("3: All Blacks");
//        stringList.add("4: Le Baguettes");
//        stringList.add("5: Mini Cooper");
//        stringList.add("6: Toyota Hilux");
//
//        ObservableList observableList = FXCollections.observableList(stringList);
//
//        assertEquals(race.getPositionStrings(), observableList);
//    }
//
//    @Test
//    public void testRaceCurrentOrder() throws Exception {
//        Race race = new Race();
//
//        ObservableList<Boat> contestants = FXCollections.observableArrayList();
//        contestants.add(new Boat("Hummer", 5.8, Color.RED, "H1"));
//        contestants.add(new Boat("Swedish Racing", 7.1, Color.BLUE, "VLV"));
//        contestants.add(new Boat("All Blacks", 11.2, Color.BLACK, "NZL"));
//        contestants.add(new Boat("Le Baguettes", 6.7, Color.WHEAT, "BRD"));
//        contestants.add(new Boat("Mini Cooper", 7.6, Color.AQUAMARINE, "TEA"));
//        contestants.add(new Boat("Toyota Hilux", 9.3, Color.DARKSALMON, "MMM"));
//
//        race.setCurrentOrder(contestants);
//
//        assertEquals(3, race.getCurrentOrder().get(2).getPosition());
//    }
//}
