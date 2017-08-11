package seng302.PacketGeneration.RaceRegistrationGeneration;

import org.junit.Test;
import seng302.Client.Messages.RaceRegistrationMessage;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageHeaderGenerations.HeaderByteValues;
import seng302.PacketGeneration.PacketUtils;

import static org.junit.Assert.assertEquals;

/**
 * Tests the RaceRegistrationPackets.
 */
public class RaceRegistrationTest {

    @Test
    public void raceRegistration() throws Exception {
        BinaryMessage rrm = new RaceRegistrationMessage(RaceRegistrationType.PARTICIPATE);
        byte[] actualMessage = new byte[4];
        int regiType = PacketUtils.getIntFromByteArray(rrm.getBody(), 0, actualMessage, 4);
        assertEquals(regiType, RaceRegistrationType.PARTICIPATE.getRegistrationType());
    }
}
