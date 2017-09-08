package seng302.RaceObjects;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * Created by msi52 on 26/07/17.
 */
public class BoatTest {

    private Boat boat;
    @Before
    public void setup() {
        boat = new Boat("Test Boat", "TestyMcTestFace", 125, "Great Britain");
    }

    @Test
    public void testUpwindHeading() {
        boat.setHeading(145);
        boat.updateHeading(100, true);

        assertEquals(142.0, boat.getHeading());
    }

    @Test
    public void testDownwindHeading() {
        boat.setHeading(150);
        boat.updateHeading(100, false);

        assertEquals(153.0, boat.getHeading());
    }

    @Test
    public void testDisconnectSailsOut() {
        boat.setSailsOut(true);

        assertTrue(boat.isSailsOut());
        assertTrue(boat.isConnected());

        boat.disconnect();

        assertFalse(boat.isSailsOut());
        assertFalse(boat.isConnected());
    }

    @Test
    public void testDisconnectSailsIn() {
        boat.setSailsOut(false);

        assertFalse(boat.isSailsOut());
        assertTrue(boat.isConnected());

        boat.disconnect();

        assertFalse(boat.isSailsOut());
        assertFalse(boat.isConnected());
    }


}
