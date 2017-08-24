package seng302;

import org.junit.Before;
import org.junit.Test;
import seng302.Helper.BoatGeneratorExtension;
import seng302.RaceObjects.Boat;
import java.util.Arrays;
import java.util.HashSet;

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
        Boat boat = boatGenerator.generateBoat();
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
        HashSet inputBoatsSet = new HashSet<>(Arrays.asList(240, 241, 242, 242, 243, 244));

        for (int i = 240; i < 245; i++) {
            Boat boat = boatGenerator.generateBoat(i);
            boat.setFinished(true);
            boatManager.addFinishedBoat(boat);
        }

        HashSet outputBoats = new HashSet<>();
        while (boatManager.hasABoatFinished()) {
            outputBoats.add(boatManager.getFinishedBoat().getSourceId());
        }

        assertEquals(inputBoatsSet, outputBoats);
    }
}