package seng302.Client.Messages;

import seng302.PacketGeneration.YachtEventGeneration.YachtEventUtility;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.PacketParsing.PacketParserUtils;
import seng302.RaceObjects.Race;

/**
 * A class to deal with the yacht event code packets
 */
public class YachtEventMessage extends ClientSideMessageParser {

    private int destinationSourceId;
    private YachtIncidentEvent eventId;

    public YachtEventMessage(byte[] body) {
        super(body);
        this.destinationSourceId = PacketParserUtils.byteArrayToInt(body, YachtEventUtility.DEST_SOURCE_ID.getIndex(), YachtEventUtility.DEST_SOURCE_ID.getSize());
        int eventId = PacketParserUtils.byteArrayToInt(body, YachtEventUtility.EVENT_ID.getIndex(), YachtEventUtility.EVENT_ID.getSize());
        this.eventId = YachtIncidentEvent.getEnum(eventId);
    }

    @Override
    public void updateRace(Race race) {
        if (eventId == YachtIncidentEvent.FINISHED) {
            race.addFinishedBoat(destinationSourceId);
        } else if (eventId == YachtIncidentEvent.BOATCOLLISION) {
            if (destinationSourceId == race.getClientSourceId()) {
                race.addCollision();
            }
        } else if (eventId == YachtIncidentEvent.MARKCOLLISION) {
            if (destinationSourceId == race.getClientSourceId()) {
                race.addCollision();
            }
        }
    }

    public int getDestinationSourceId() {
        return destinationSourceId;
    }

    public YachtIncidentEvent getEventId() {
        return eventId;
    }
}
