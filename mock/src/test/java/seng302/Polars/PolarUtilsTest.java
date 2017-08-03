package seng302.Polars;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.RaceObjects.Boat;

/**
 * Created by osr13 on 26/07/17.
 */
public class PolarUtilsTest {

    private Boat testBoat;
    private int windSpeed;
    private double windHeading;

    @Before
    public void setup() {
        testBoat = new Boat("Test", "t", 114, "TestLand");
        testBoat.setSpeed(1000);
        testBoat.setHeading(120);

        windSpeed = 1500;
    }

    @Test
    public void updateSpeedTest() {
        windHeading = 150;
        int expectedSpeed = 1134;
        PolarUtils.updateBoatSpeed(testBoat, windHeading, windSpeed);
        Assert.assertEquals(expectedSpeed, testBoat.getSpeed());
    }

    @Test
    public void updateSpeedUpWindTest() {
        windHeading = 300;
        int expectedSpeed = 1742;
        PolarUtils.updateBoatSpeed(testBoat, windHeading, windSpeed);
        Assert.assertEquals(expectedSpeed, testBoat.getSpeed());
    }

    @Test
    public void updateSpeedDownWindTest() {
        windHeading = 120;
        int expectedSpeed = 0;
        PolarUtils.updateBoatSpeed(testBoat, windHeading, windSpeed);
        Assert.assertEquals(expectedSpeed, testBoat.getSpeed());
    }

    @Test
    public void updateSpeedBoatHeading0Test() {
        windHeading = 150;
        testBoat.setHeading(0);
        int expectedSpeed = 2839;
        PolarUtils.updateBoatSpeed(testBoat, windHeading, windSpeed);
        Assert.assertEquals(expectedSpeed, testBoat.getSpeed());
    }

    @Test
    public void updateSpeedBoatHeading355Test() {
        windHeading = 150;
        testBoat.setHeading(350);
        int expectedSpeed = 2291;
        PolarUtils.updateBoatSpeed(testBoat, windHeading, windSpeed);
        Assert.assertEquals(expectedSpeed, testBoat.getSpeed());
    }
}
