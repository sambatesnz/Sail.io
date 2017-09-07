package seng302.PacketGeneration.YachtEventGeneration;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Contains yacht event messages as defined by the AC35 spec
 */
public class YachtEventMessage extends BinaryMessage {

    private byte[] yachtEventMessage;

    private byte[] messageVersionNumber;
    private byte[] time;
    private byte[] ackNumber;
    private byte[] raceId;
    private byte[] destSourceId;
    private byte[] incidentId;
    private byte[] eventId;

    private static final int MESSAGE_SIZE = 22;
    private static final int MESSAGE_VERSION_NUMBER = 2;

    /**
     * Generates a yacht event based on a boats source id and the event that occurred
     * @param boatSourceId source id of the boat
     * @param event event that occurred
     */
    public YachtEventMessage(int boatSourceId, YachtIncidentEvent event){
        this.yachtEventMessage = new byte[22];

        this.messageVersionNumber = PacketGenerationUtils.intToOneByte(2);
        this.time = PacketGenerationUtils.longToSixBytes(System.currentTimeMillis());
        this.ackNumber = PacketGenerationUtils.intToTwoBytes(1);
        this.raceId = PacketGenerationUtils.intToFourBytes(0);
        this.destSourceId = PacketGenerationUtils.intToFourBytes(boatSourceId);
        this.incidentId = PacketGenerationUtils.intToFourBytes(0);
        this.eventId = PacketGenerationUtils.intToOneByte(event.getValue());
    }

    @Override
    public byte[] getBody() {
        System.arraycopy(messageVersionNumber, 0, yachtEventMessage, YachtEventUtility.VERSION_NUMBER.getIndex(), YachtEventUtility.VERSION_NUMBER.getSize());
        System.arraycopy(time, 0, yachtEventMessage, YachtEventUtility.TIME.getIndex(), YachtEventUtility.TIME.getSize());
        System.arraycopy(ackNumber, 0, yachtEventMessage, YachtEventUtility.ACKNUMBER.getIndex(), YachtEventUtility.ACKNUMBER.getSize());
        System.arraycopy(raceId, 0, yachtEventMessage, YachtEventUtility.RACE_ID.getIndex(), YachtEventUtility.RACE_ID.getSize());
        System.arraycopy(destSourceId, 0, yachtEventMessage, YachtEventUtility.DEST_SOURCE_ID.getIndex(), YachtEventUtility.DEST_SOURCE_ID.getSize());
        System.arraycopy(incidentId, 0, yachtEventMessage, YachtEventUtility.INCIDENT_ID.getIndex(), YachtEventUtility.INCIDENT_ID.getSize());
        System.arraycopy(eventId, 0, yachtEventMessage, YachtEventUtility.EVENT_ID.getIndex(), YachtEventUtility.EVENT_ID.getSize());
        return yachtEventMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.YACHT_EVENT.getMessageType();
    }
}
