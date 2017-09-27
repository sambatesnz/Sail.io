package seng302.PacketGeneration.ServerMessageGeneration;

import org.junit.Before;
import org.junit.Test;
import seng302.Client.Messages.RaceRegistrationMessage;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketParsing.PacketParserUtils;

import static org.junit.Assert.assertEquals;

/**
 * Tests for message server message generation
 */
public class ServerMessageGenerationUtilsTest {

    private byte[] message;

    @Before
    public void setup(){
        BinaryMessage registrationMessage = new RaceRegistrationMessage(RaceRegistrationType.PARTICIPATE);
        message = registrationMessage.createMessage();
    }

    @Test
    public void wrap() throws Exception{
        int expected = 47262;
        byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(message, expected);
        int actual = PacketParserUtils.byteArrayToInt(wrappedMessage, 0, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void getHeaderId() throws Exception {
        int expected = 47262;
        byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(message, expected);
        int actual = ServerMessageGenerationUtils.unwrapHeader(wrappedMessage);
        assertEquals(expected, actual);
    }

    @Test
    public void unwrapBody() throws Exception {
        int expected = 47262;
        byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(message, expected);
        byte[] unwrapped = ServerMessageGenerationUtils.unwrapBody(wrappedMessage);
        //Checks every value in the array because message and unwrapped are different object references
        for (int i = 0; i < unwrapped.length ; i++) {
            assertEquals(message[i], unwrapped[i]);
        }
    }

}