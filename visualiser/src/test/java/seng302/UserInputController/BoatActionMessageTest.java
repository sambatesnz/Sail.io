package seng302.UserInputController;

import org.junit.Test;
import seng302.PacketGeneration.PacketUtils;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by tjg73 on 14/07/17.
 */
public class BoatActionMessageTest {

    private int boatAction;

    private byte[] message;

    private BoatActionMessage boatActionMessage;

    public BoatActionMessageTest(){
        Random generator = new Random();
        this.boatAction = generator.nextInt(6) + 1;  //So between 1 and 6 inclusive

        this.boatActionMessage = new BoatActionMessage(boatAction);
        this.message = boatActionMessage.getBody();
    }

    @Test
    public void boatAction() throws Exception {
        byte[] actualMessage = new byte[8];
        int actualBoatAction = PacketUtils.getIntFromByteArray(message, 0, actualMessage, 1);
        assertEquals(boatAction, actualBoatAction);
    }
}
