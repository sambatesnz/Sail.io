package seng302.UserInput;

import org.junit.Test;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketParsing.PacketParserUtils;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the practise message class
 */
public class PracticeMessageTest {


    @Test
    public void meaning() throws Exception {
        PracticeMessageMeaning expectedMeaning = PracticeMessageMeaning.END;
        BinaryMessage practiceMessage = new PracticeMessage(expectedMeaning);
        byte[] body = practiceMessage.getBody();
        int actualMeaning  = PacketParserUtils.byteArrayToInt(body, PracticeMessageUtil.MEANING.getIndex(), PracticeMessageUtil.MEANING.getSize());
        assertEquals(expectedMeaning.getValue() , actualMeaning);
    }

    @Test
    public void boatSourceId() throws Exception {
        int expectedBoatSourceId = 50;
        BinaryMessage practiceMessage = new PracticeMessage(PracticeMessageMeaning.END, expectedBoatSourceId);
        byte[] body = practiceMessage.getBody();
        int actualBoatSourceId = PacketParserUtils.byteArrayToInt(body, PracticeMessageUtil.BOAT_SOURCE_ID.getIndex(), PracticeMessageUtil.BOAT_SOURCE_ID.getSize());
        assertEquals(expectedBoatSourceId, actualBoatSourceId);
    }


}