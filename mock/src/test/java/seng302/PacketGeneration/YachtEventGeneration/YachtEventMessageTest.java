package seng302.PacketGeneration.YachtEventGeneration;

import org.junit.Test;
import seng302.PacketParsing.PacketParserUtils;

import static org.junit.Assert.*;

/**
 * Tests for yacht event messages
 */
public class YachtEventMessageTest {


    @Test
    public void boatId() throws Exception {
        int expectedBoatId = 100;
        YachtEventMessage message = new YachtEventMessage(expectedBoatId, YachtIncidentEvent.DEFAULT);
        byte[] createdMessage = message.getBody();
        int actualBoatId = PacketParserUtils.byteArrayToInt(createdMessage, YachtEventUtility.DEST_SOURCE_ID.getIndex(), YachtEventUtility.DEST_SOURCE_ID.getSize());
        assertEquals(expectedBoatId, actualBoatId);
    }

    @Test
    public void EventId() throws Exception {
        YachtIncidentEvent finished = YachtIncidentEvent.FINISHED;
        int expectedEventId = YachtIncidentEvent.FINISHED.getValue();
        YachtEventMessage message = new YachtEventMessage(100, finished);
        byte[] createdMessage = message.getBody();
        int actualEventId = PacketParserUtils.byteArrayToInt(createdMessage, YachtEventUtility.EVENT_ID.getIndex(), YachtEventUtility.EVENT_ID.getSize());
        assertEquals(expectedEventId, actualEventId);
    }

}