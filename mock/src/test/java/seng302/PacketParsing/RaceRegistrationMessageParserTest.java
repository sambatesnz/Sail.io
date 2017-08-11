package seng302.PacketParsing;

import org.junit.Test;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.PacketGeneration.BinaryMessage;

import static org.junit.Assert.*;

public class RaceRegistrationMessageParserTest {

    @Test
    public void ParticipationMessage() throws Exception{
        RaceRegistrationType expectedType = RaceRegistrationType.PARTICIPATE;
        BinaryMessage rrm = new seng302.Client.Messages.RaceRegistrationMessage(expectedType);
        RaceRegistrationMessage parser = new RaceRegistrationMessage(rrm.createMessage());
       assertEquals(expectedType, parser.getRegistrationType());
    }

    @Test
    public void ViewMessage() throws Exception{
        RaceRegistrationType expectedType = RaceRegistrationType.VIEW;
        BinaryMessage rrm = new seng302.Client.Messages.RaceRegistrationMessage(expectedType);
        RaceRegistrationMessage parser = new RaceRegistrationMessage(rrm.createMessage());
        assertEquals(expectedType, parser.getRegistrationType());
    }

}