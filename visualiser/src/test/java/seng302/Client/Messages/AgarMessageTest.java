package seng302.Client.Messages;

import org.junit.Test;
import seng302.PacketGeneration.AgarPackets.ServerAgarMessage;
import seng302.PacketGeneration.BinaryMessage;

import static org.junit.Assert.assertEquals;

/**
 * Tests for agar message parsing
 */
public class AgarMessageTest {

    @Test
    public void AgarMessageParsing() throws Exception {
        for (int i = 0; i < 1000; i++) {
            int actualBoatSourceId = i;
            int actualLives = 3;
            int actualBoatSize = 500;
            BinaryMessage message = new ServerAgarMessage(actualBoatSourceId, actualLives, actualBoatSize);
            AgarMessage messageParser = new AgarMessage(message.getBody());
            assertEquals(actualBoatSourceId, messageParser.getBoatSourceId());
            assertEquals(actualLives, messageParser.getLives());
            assertEquals(actualBoatSize, messageParser.getBoatSize());
        }

    }

}