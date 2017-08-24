package seng302;

import org.junit.Before;
import org.junit.Test;
import seng302.Helper.BoatGeneratorExtension;
import seng302.RaceObjects.Boat;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Boat Manager class
 */
public class BoatManagerTest {

    private BoatManager boatManager;
    private BoatGeneratorExtension boatGenerator;

    @Before
    public void setup() {
        boatManager = new BoatManager();
        boatGenerator = new BoatGeneratorExtension();

    }

    @Test
    public void addUnfinishedBoat() throws Exception {
        Boat boat = boatGenerator.generateBoat(101);
        boatManager.addFinishedBoat(boat);

        boolean hasBoatFinished = boatManager.hasABoatFinished();

        assertEquals(false, hasBoatFinished);
    }



    @Test
    public void addFinishedBoat() throws Exception {
        Boat finishedBoat = boatGenerator.generateBoat();
        finishedBoat.setFinished(true);
        boatManager.addFinishedBoat(finishedBoat);

        Boat actualBoat = boatManager.getFinishedBoat();

        assertEquals(finishedBoat.getSourceId(), actualBoat.getSourceId());
    }

    @Test
    public void addMultipleBoats() throws Exception {
        for (int i = 0; i < 5; i++) {
            Boat boat = boatGenerator.generateBoat();
            System.out.println(boat.getSourceId());
            boat.setFinished(true);
            boatManager.addFinishedBoat(boat);
        }




    }
}