package seng302;

import org.junit.Test;
import seng302.utility.Boat;

import static org.junit.Assert.assertTrue;


/**
 * Created by osr13 on 9/03/17.
 */
public class BoatTest {

    @Test
    public void testBoatSpeed() {
        Boat b1 = new Boat("Kevin", 43);
        assertTrue(b1.getBoatSpeed() == 43);
    }

    @Test
    public void testBoatName() {
        Boat b1 = new Boat("Kevin", 43);
        assertTrue(b1.getBoatName().equals("Kevin"));
    }
}