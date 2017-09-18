package seng302.PacketGeneration.AgarPackets;

import seng302.Client.Messages.Message;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * implementation of a binary message for agar mode
 */
public class AgarMessage extends BinaryMessage {


    private byte[] lives;
    private byte[] boatSize;
    private byte[] boatSourceId;
    private static int MESSAGE_SIZE = 12;
    private byte[] agarMessage;

    public AgarMessage(int boatSourceId, int lives, int boatSize){
        this.boatSourceId = PacketGenerationUtils.intToFourBytes(boatSourceId);
        this.lives = PacketGenerationUtils.intToFourBytes(lives);
        this.boatSize = PacketGenerationUtils.intToFourBytes(boatSize);
        this.agarMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        int FIRST_INDEX = 0;
        System.arraycopy(boatSourceId, FIRST_INDEX, agarMessage, AgarMessageUtility.BOAT_SOURCE_ID.getIndex(), AgarMessageUtility.BOAT_SOURCE_ID.getSize());
        System.arraycopy(boatSourceId, FIRST_INDEX, agarMessage, AgarMessageUtility.LIVES.getIndex(), AgarMessageUtility.LIVES.getSize());
        System.arraycopy(boatSourceId, FIRST_INDEX, agarMessage, AgarMessageUtility.BOAT_SIZE.getIndex(), AgarMessageUtility.BOAT_SIZE.getSize());
        return agarMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.AGAR.getMessageType();
    }
}
