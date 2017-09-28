package seng302.RaceObjects;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by osr13 on 28/09/17.
 */
public class AgarBoatTest {

    GenericBoat ab;

    @Before
    public void setup() {
        ab = new AgarBoat(new Boat("Kevin", "KEV", 110, "Kevistan"));
        ab.setBaseSpeed();
    }


    @Test
    public void boatSpeedDecreaseAsSizeIncreases() {
        System.out.println("Theoretical Max = " + 300*20);
        System.out.println(ab.getBaseSpeed());
        ab.setAgarSize(200);
        System.out.println(ab.getBaseSpeed());
        ab.setAgarSize(1000);
        System.out.println(ab.getBaseSpeed());
        ab.setAgarSize(6000);
        System.out.println(ab.getBaseSpeed());

    }


}
