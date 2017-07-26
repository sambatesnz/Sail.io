package seng302;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class RaceTest {

    private Race race = new Race();
    private static final int TEN_KNOTS = 5145;
    private static final int FORTY_KNOTS = 20577;
    private static final int FIVE_KNOTS = 2573;


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

        assertTrue(greatestWindVal - lowestWindVal <= TEN_KNOTS);
    }

}
