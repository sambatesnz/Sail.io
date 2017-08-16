package seng302.PacketParsing;

import seng302.Client.Messages.Message;
import seng302.DataGeneration.IServerData;
import seng302.Server.Delegator;
import seng302.UserInputController.BoatAction;

import java.util.Calendar;
import java.util.Date;

/**
 * Tries to update the race so that it is a practice race
 */
public class PracticeRaceMessageReceiver extends ServerSideMessageFactory {
    private byte meaning;

    public static final byte START = 0;
    public static final byte END = 1;

    public PracticeRaceMessageReceiver(byte[] packet) {
        super(packet);
        this.meaning = getMessageBody()[0];
    }

    @Override
    public void updateRace(IServerData race) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        int oneMinInMillis = 60000;
        if (this.meaning == START) {
            System.out.println("==============================UPDATING THE RACE TIME TO 1 MIN==============================");
            race.getRace().setStartingTime(new Date(t + oneMinInMillis));
        } else {
            System.out.println("==============================UPDATING THE RACE TIME TO 3 MIN==============================");
            race.getRace().setStartingTime(new Date(t + oneMinInMillis*3));
            Message.resetData();
        }
//        race.getRace().setRaceTime(new Date(15 * 60));
    }
}
