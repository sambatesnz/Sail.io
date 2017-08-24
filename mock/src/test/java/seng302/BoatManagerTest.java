package seng302;

import org.junit.Before;
import org.junit.Test;
import seng302.RaceObjects.Boat;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Boat Manager class
 */
public class BoatManagerTest {

    private BoatManager boatManager;
    private BoatGenerator boatGenerator;

    @Before
    public void setup() {
        boatManager = new BoatManager();
        boatGenerator = new BoatGenerator();
    }

    @Test
    public void addUnfinishedBoat() throws Exception {
        Boat boat = boatGenerator.generateBoat();
        boatManager.addFinishedBoat(boat);

        boolean hasBoatFinished = boatManager.hasABoatFinished();

        assertEquals(false, hasBoatFinished);
    }

    @Test
    public void addFinishedBoat() throws Exception {
        Boat boat = boatGenerator.generateBoat();
        boat.setFinished(true);
        boatManager.addFinishedBoat(boat);

        boolean hasBoatFinished = boatManager.hasABoatFinished();

        assertEquals(true, hasBoatFinished);
    }

    @Test
    public void addMultipleBoats() throws Exception {
        for (int i = 0; i < 5; i++) {
            Boat boat = boatGenerator.generateBoat();
            boat.setFinished(true);
            boatManager.addFinishedBoat(boat);
        }

        boolean hasBoatFinished = boatManager.hasABoatFinished();


    }
}