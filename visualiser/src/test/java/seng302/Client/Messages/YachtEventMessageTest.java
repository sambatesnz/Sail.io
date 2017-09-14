package seng302.Client.Messages;

import org.junit.Test;
import seng302.BoatGenerator;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.*;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Race;

import java.util.*;

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
        seng302.Client.Messages.YachtEventMessage yachtEventMessage = new seng302.Client.Messages.YachtEventMessage(message.getBody());
        assertEquals(expectedSourceId, yachtEventMessage.getDestinationSourceId());
        assertEquals(expectedEvent, yachtEventMessage.getEventId());
    }

    @Test
    public void YachtEventMessageUpdate() throws Exception {
        Race race = new Race();
        BoatGenerator generator = new BoatGenerator();
        Boat boat1 = generator.generateBoat();
        Boat boat2 = generator.generateBoat();
        Map<Integer, Boat> boatsList = new HashMap<>();
        boatsList.put(boat1.getSourceId(), boat1);
        boatsList.put(boat2.getSourceId(), boat2);
        race.setParticipants(new ArrayList<>(Arrays.asList(boat1.getSourceId(), boat2.getSourceId())));
        race.setBoats(boatsList);

        // THE ABOVE SHOULD BE DONE BEFORE THE RACE

        BinaryMessage message = new YachtEventMessage(boat1.getSourceId(), YachtIncidentEvent.FINISHED);
        seng302.Client.Messages.YachtEventMessage yachtEventMessage = new seng302.Client.Messages.YachtEventMessage(message.getBody());

        yachtEventMessage.updateRace(race);
        int actualAmountOfBoats = 1; //At this point we have only set one boat to finished
        assertEquals(actualAmountOfBoats, race.getBoatsForScoreBoard().size());

        message = new YachtEventMessage(boat2.getSourceId(), YachtIncidentEvent.FINISHED);
        yachtEventMessage = new seng302.Client.Messages.YachtEventMessage(message.getBody());
        yachtEventMessage.updateRace(race);

        actualAmountOfBoats = 2; //At this point we have set two boats to finished
        assertEquals(actualAmountOfBoats, race.getBoatsForScoreBoard().size());

    }

}