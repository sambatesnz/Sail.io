import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.BoatGenerator;
import seng302.Modes.AgarRace;
import seng302.Modes.Race;
import seng302.RaceObjects.AgarBoat;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.BoatInterface;

/**
 * Created by osr13 on 21/09/17.
 */
public class BoatBaseSpeedTest {
    private BoatGenerator boatGenerator = new BoatGenerator();
    private Race race = new AgarRace();
    private BoatInterface b;


    @Before
    public void setUp() throws Exception {
        b = new AgarBoat(boatGenerator.generateBoat());
        b.setAgarSize(100);
    }

    @Test
    public void testBaseSpeedIncreases() throws Exception {
        double size = (double) b.getAgarSize()/800;
        int baseSpeed = (int) (Math.log(size) * -10000);
        b.setBaseSpeed();
        System.out.println(baseSpeed+ ", "+ b.getBaseSpeed());
        Assert.assertTrue(baseSpeed == b.getBaseSpeed());
    }

    @Test
    public void testSpeedDecreasesWithSizeDecrement() throws Exception {
        b.setAgarSize(500);
        b.setBaseSpeed();
        int speedBefore = b.getBaseSpeed();

        b.setAgarSize(250);
        b.setBaseSpeed();
        int speedAfter = b.getBaseSpeed();

        System.out.println(speedBefore + ", "+ speedAfter);
        Assert.assertTrue(speedAfter > speedBefore);
    }

}
