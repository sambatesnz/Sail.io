package seng302.PacketParsing;

import org.junit.Test;
import seng302.Client.Messages.RaceRegistrationMessage;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.PacketUtils;
import seng302.Race;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RaceRegistrationMessageParserTest {

    @Test
    public void ParticipationMessage() throws Exception{
        RaceRegistrationType expectedType = RaceRegistrationType.PARTICIPATE;
        BinaryMessage rrm = new RaceRegistrationMessage(expectedType);
        RaceRegistrationMessageParser parser = new RaceRegistrationMessageParser(rrm.createMessage());
       assertEquals(expectedType, parser.getRegistrationType());
    }

    @Test
    public void ViewMessage() throws Exception{
        RaceRegistrationType expectedType = RaceRegistrationType.VIEW;
        BinaryMessage rrm = new RaceRegistrationMessage(expectedType);
        RaceRegistrationMessageParser parser = new RaceRegistrationMessageParser(rrm.createMessage());
        assertEquals(expectedType, parser.getRegistrationType());
    }

}