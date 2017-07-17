package seng302;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by msi52 on 17/07/17.
 */
public class RaceTest {

    private Race race = new Race();
    private int TEN_KNOTS = 5145;
    private int FORTY_KNOTS = 20577;
    private int FIVE_KNOTS = 2573;


    @Test
    public void WindSpeedTest() {

        int lowestWindVal = FORTY_KNOTS;
        int greatestWindVal = FIVE_KNOTS;

        for (int i = 0; i < 30; i++) {
            int currentWindVal = race.retrieveWindSpeed();

            if (currentWindVal < lowestWindVal) {
                lowestWindVal = currentWindVal;
            } else if (currentWindVal > greatestWindVal) {
                greatestWindVal = currentWindVal;
            }
        }

        assertEquals(greatestWindVal - lowestWindVal <= TEN_KNOTS, true);
    }

}
