package RaceObjects;

import org.junit.Test;
import seng302.RaceObjects.Boat;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by msi52 on 26/07/17.
 */
public class BoatTest {

    private Boat boat = new Boat("Test Boat", "TestyMcTestFace", 125, "Great Britain");

    @Test
    public void testUpwindHeading() {
        boat.setHeading(145);
        boat.updateHeading(100, true);

        assertEquals(148.0, boat.getHeading());
    }

    @Test
    public void testDownwindHeading() {
        boat.setHeading(150);
        boat.updateHeading(100, false);

        assertEquals(147.0, boat.getHeading());
    }


}
