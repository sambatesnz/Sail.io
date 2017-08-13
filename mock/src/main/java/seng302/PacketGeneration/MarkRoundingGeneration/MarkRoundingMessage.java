package seng302.PacketGeneration.MarkRoundingGeneration;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Created by tjg73 on 13/08/17.
 */
public class MarkRoundingMessage extends BinaryMessage {

    private byte[] versionNumber;
    private byte[] time;
    private byte[] ackNum;
    private byte[] raceID;
    private byte[] sourceID;
    private byte[] boatStatus;
    private byte[] roundingSide;
    private byte[] markType;
    private byte[] markID;

    private static final int MESSAGE_SIZE = 21;
    private byte[] markRoundingMessage;

    public MarkRoundingMessage(int versionNumber, long time, int ackNum, int raceID, int sourceID, int boatStatus,
                               int roundingSide, int markType, int markId){
        this.markRoundingMessage = new byte[MESSAGE_SIZE];

        this.versionNumber = PacketGenerationUtils.intToOneByte(versionNumber);
        this.time = PacketGenerationUtils.longToSixBytes(time);
        this.ackNum = PacketGenerationUtils.intToTwoBytes(ackNum);
        this.raceID = PacketGenerationUtils.intToFourBytes(raceID);
        this.sourceID = PacketGenerationUtils.intToFourBytes(sourceID);
        this.boatStatus = PacketGenerationUtils.intToOneByte(boatStatus);
        this.roundingSide = PacketGenerationUtils.intToOneByte(roundingSide);
        this.markType = PacketGenerationUtils.intToOneByte(markType);
        this.markID = PacketGenerationUtils.intToOneByte(markId);
    }

    @Override
    public byte[] getBody() {
        int FIRST_INDEX = 0;

        System.arraycopy(versionNumber, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.MESSAGE_VERSION.getIndex(), MarkRoundingUtility.MESSAGE_VERSION.getSize());
        System.arraycopy(time, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.TIME.getIndex(), MarkRoundingUtility.TIME.getSize());
        System.arraycopy(ackNum, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.ACK_NUMBER.getIndex(), MarkRoundingUtility.ACK_NUMBER.getSize());
        System.arraycopy(raceID, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.RACE_ID.getIndex(), MarkRoundingUtility.RACE_ID.getSize());
        System.arraycopy(sourceID, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.SOURCE_ID.getIndex(), MarkRoundingUtility.SOURCE_ID.getSize());
        System.arraycopy(boatStatus, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.BOAT_STATUS.getIndex(), MarkRoundingUtility.BOAT_STATUS.getSize());
        System.arraycopy(roundingSide, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.ROUNDING_SIDE.getIndex(), MarkRoundingUtility.ROUNDING_SIDE.getSize());
        System.arraycopy(markType, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.MARK_TYPE.getIndex(), MarkRoundingUtility.MARK_TYPE.getSize());
        System.arraycopy(markID, FIRST_INDEX, markRoundingMessage, MarkRoundingUtility.MARk_ID.getIndex(), MarkRoundingUtility.MARk_ID.getSize());

        return markRoundingMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.MARK_ROUNDING.getMessageType();
    }
}
