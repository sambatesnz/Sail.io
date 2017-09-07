package seng302.Client.Messages;

import org.junit.Test;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.*;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.RaceObjects.Race;

import static org.junit.Assert.*;

/**
 * Tests the yacht event message class
 */
public class YachtEventMessageTest {

    @Test
    public void YachtEventMessageProperties() throws Exception {
        int expectedSourceId = 101;
        YachtIncidentEvent expectedEvent = YachtIncidentEvent.FINISHED;
        BinaryMessage message = new YachtEventMessage(expectedSourceId, expectedEvent);
        Race race = new Race();
        seng302.Client.Messages.YachtEventMessage yachtEventMessage = new seng302.Client.Messages.YachtEventMessage(message.getBody(), race);

        assertEquals(expectedSourceId, yachtEventMessage.getDestinationSourceId());
        assertEquals(expectedEvent, yachtEventMessage.getEventId());
    }

}