package seng302.PacketParsing;

import org.junit.Test;
import seng302.Client.Messages.RaceRegistrationMessage;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;

import static org.junit.Assert.*;

public class RaceRegistrationMessageCreatorReceiverParserTest {

    @Test
    public void ParticipationMessage() throws Exception{
        RaceRegistrationType expectedType = RaceRegistrationType.PARTICIPATE;
        BinaryMessage rrm = new RaceRegistrationMessage(expectedType);
        byte[] wrappedMessage= ServerMessageGenerationUtils.wrap(rrm.createMessage(), 10);
        RaceRegistrationMessageCreatorReceiver parser = new RaceRegistrationMessageCreatorReceiver(wrappedMessage);
        assertEquals(expectedType, parser.getRegistrationType());
    }

    @Test
    public void ViewMessage() throws Exception{
        RaceRegistrationType expectedType = RaceRegistrationType.VIEW;
        BinaryMessage rrm = new RaceRegistrationMessage(expectedType);
        byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(rrm.createMessage(), 10);
        RaceRegistrationMessageCreatorReceiver parser = new RaceRegistrationMessageCreatorReceiver(wrappedMessage);
        assertEquals(expectedType, parser.getRegistrationType());
    }

}