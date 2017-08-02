package seng302.UserInputController;

import org.junit.Test;
import seng302.PacketGeneration.PacketUtils;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests Boat Action Messages
 **/
public class BoatActionMessageTest {

    private int boatAction;
    private int boatSourceId;

    private byte[] message;

    public BoatActionMessageTest(){
        Random generator = new Random();
        this.boatAction = generator.nextInt(6) + 1;  //So between 1 and 6 inclusive
        this.boatSourceId = generator.nextInt(20) + 101;

        BoatActionMessage boatActionMessage = new BoatActionMessage(boatAction, boatSourceId);
        this.message = boatActionMessage.getBody();
    }

    @Test
    public void boatAction() throws Exception {
        byte[] actualMessage = new byte[8];
        int actualBoatAction = PacketUtils.getIntFromByteArray(message, BoatActionMessageUtility.BOAT_ACTION.getIndex(), actualMessage,  BoatActionMessageUtility.BOAT_ACTION.getSize());
        assertEquals(boatAction, actualBoatAction);
    }

    @Test
    public void boatSourceId() throws Exception {
        byte[] actualMessage = new byte[8];
        int actualBoatAction = PacketUtils.getIntFromByteArray(message, BoatActionMessageUtility.BOAT_SOURCE_ID.getIndex(), actualMessage, BoatActionMessageUtility.BOAT_SOURCE_ID.getSize());
        assertEquals(boatSourceId, actualBoatAction);
    }
}
