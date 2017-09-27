package seng302.PacketGeneration.AgarPackets;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * implementation of a binary message for agar mode
 */
public class ServerAgarMessage extends BinaryMessage {


    private byte[] lives;
    private byte[] boatSize;
    private byte[] boatSourceId;
    private static int MESSAGE_SIZE = 12;
    private byte[] agarMessage;

    public ServerAgarMessage(int boatSourceId, int lives, int boatSize){
        this.boatSourceId = PacketGenerationUtils.intToFourBytes(boatSourceId);
        this.lives = PacketGenerationUtils.intToFourBytes(lives);
        this.boatSize = PacketGenerationUtils.intToFourBytes(boatSize);
        this.agarMessage = new byte[MESSAGE_SIZE];
        System.out.println("boat with source id" + boatSourceId+ " has lives: " + lives);

    }

    @Override
    public byte[] getBody() {
        int FIRST_INDEX = 0;
        System.arraycopy(boatSourceId, FIRST_INDEX, agarMessage, AgarServerMessageUtility.BOAT_SOURCE_ID.getIndex(), AgarServerMessageUtility.BOAT_SOURCE_ID.getSize());
        System.arraycopy(lives, FIRST_INDEX, agarMessage, AgarServerMessageUtility.LIVES.getIndex(), AgarServerMessageUtility.LIVES.getSize());
        System.arraycopy(boatSize, FIRST_INDEX, agarMessage, AgarServerMessageUtility.BOAT_SIZE.getIndex(), AgarServerMessageUtility.BOAT_SIZE.getSize());
        return agarMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.AGAR.getMessageType();
    }
}
