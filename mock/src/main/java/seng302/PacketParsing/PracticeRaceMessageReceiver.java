package seng302.PacketParsing;

import seng302.Client.Messages.Message;
import seng302.DataGeneration.IServerData;

import java.util.Calendar;
import java.util.Date;

/**
 * Tries to update the race so that it is a practice race
 */
public class PracticeRaceMessageReceiver extends ServerSideMessageFactory {
    private byte meaning;
    private int boatSourceId;

    public static final byte START = 0;
    public static final byte END = 1;

    public PracticeRaceMessageReceiver(byte[] packet) {
        super(packet);
        byte[] body = getMessageBody();
        this.meaning = body[0];
        this.boatSourceId = PacketParserUtils.byteArrayToInt(body, 1, 4);
    }

    @Override
    public void updateRace(IServerData race) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        int oneMinInMillis = 60000;
        if (this.meaning == START) {
            race.getRace().setStartingTime(new Date(t + oneMinInMillis + 1000*5));
            race.getRace().setPracticeRace(true);
        } else if (this.meaning == END) {
            race.getRace().setStartingTime(new Date(t + oneMinInMillis*3));
            race.getRace().removeBoat(boatSourceId);
            race.getRace().setPracticeRace(false);
            Message.resetData();
        }
    }

    public byte getMeaning() {
        return meaning;
    }
}
