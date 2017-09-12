package seng302;

import org.junit.Test;
import seng302.PacketGeneration.PacketGenerationUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void CastTest() {
        int sog = 5;
        byte[] output;
        byte[] ans = new byte[4];
        output = PacketGenerationUtils.shortToTwoBytes(sog);
        System.arraycopy(output, 0, ans, 0, 2);
        int output_num = ByteBuffer.wrap(ans).order(ByteOrder.LITTLE_ENDIAN).getInt();
        assertEquals(output_num, sog);
    }

}
