package seng302.PacketParsing;

import seng302.DataGeneration.IServerData;
import seng302.Server.Delegator;
import seng302.UserInputController.BoatAction;

import java.util.Calendar;
import java.util.Date;

/**
 * Tries to update the race so that it is a practice race
 */
public class PracticeRaceMessageReceiver extends ServerSideMessageFactory {

    public PracticeRaceMessageReceiver(byte[] packet) {
        super(packet);
    }

    @Override
    public void updateRace(IServerData race) {
        System.out.println("==============================UPDATING THE RACE TIME==============================");
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        int oneMinInMillis = 60000;
        race.getRace().setStartingTime(new Date(t + oneMinInMillis));
        race.getRace().setRaceTime(new Date(15 * 60));
    }
}
