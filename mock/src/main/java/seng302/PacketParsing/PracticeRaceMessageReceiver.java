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
    private byte[] body;
    private byte meaning;
    private int boatSourceId;

    private static final byte START = 0;
    private static final byte END = 1;

    public PracticeRaceMessageReceiver(byte[] packet) {
        super(packet);
        this.body = getMessageBody();
        this.meaning = body[0];
        this.boatSourceId = parseBoatSourceId();
    }

    private int parseBoatSourceId() {
        int sourceId = PacketParserUtils.byteArrayToInt(body, 1, 4);
        return sourceId;
    }

    @Override
    public void updateRace(IServerData race) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        int oneMinInMillis = 60000;
        if (this.meaning == START) {
            System.out.println("==============================UPDATING THE RACE TIME TO 1 MIN==============================");
            race.getRace().setStartingTime(new Date(t + oneMinInMillis));
        } else if (this.meaning == END) {
            System.out.println("==============================UPDATING THE RACE TIME TO 3 MIN==============================");
            race.getRace().setStartingTime(new Date(t + oneMinInMillis*3));
            race.getRace().removeBoat(boatSourceId);
            Message.resetData();
        }
    }
}
