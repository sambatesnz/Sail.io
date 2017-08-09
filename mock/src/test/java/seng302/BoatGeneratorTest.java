package seng302;

import org.junit.Test;
import seng302.RaceObjects.Boat;

import static org.junit.Assert.*;

public class BoatGeneratorTest {
    @Test
    public void generateBoat() throws Exception {
        BoatGenerator boatGenerator = new BoatGenerator();
        int expectedSourceId = boatGenerator.getLowestSourceId(); //Lowest source id
        for (int i = 0; i < 20; i++) {
            Boat b = boatGenerator.generateBoat();
            assertEquals(b.getSourceId(), expectedSourceId);
            expectedSourceId++;
        }
    }

}