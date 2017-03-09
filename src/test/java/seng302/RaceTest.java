package seng302;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tjg73 on 9/03/17.
 */
public class RaceTest {

    @Test
    public void testAddingBoatsToRace() {
        Race testRace = new Race();

        ArrayList<Boat> testBoats = new ArrayList<>();

        testBoats.add(new Boat("ORACLE TEAM USA", 50));
        testBoats.add(new Boat("Artemis Racing", 51));
        testBoats.add(new Boat("Emirates Team New Zealand", 60));

        testRace.addRacingBoats(2, testBoats);

        assertEquals(2, testRace.getRacingBoats().size());
    }

    @Test
    public void testSlowestBoatSpeedInRace() {
        Race testRace = new Race();

        ArrayList<Boat> testBoats = new ArrayList<>();

        testBoats.add(new Boat("ORACLE TEAM USA", 50));
        testBoats.add(new Boat("Artemis Racing", 51));
        testBoats.add(new Boat("Emirates Team New Zealand", 60));

        testRace.addRacingBoats(testBoats.size(), testBoats);

        assertEquals(50, testRace.getSlowestBoatSpeed(), 1e-9);
    }

}
