package seng302.PacketParsing;

import seng302.Client.Messages.Message;
import seng302.DataGeneration.IServerData;
import seng302.UserInput.PracticeMessageMeaning;

import java.util.Calendar;
import java.util.Date;

/**
 * Tries to update the race so that it is a practice race
 */
public class PracticeRaceMessageReceiver extends ServerSideMessageFactory {
    private PracticeMessageMeaning meaning;
    private int boatSourceId;

    public PracticeRaceMessageReceiver(byte[] packet) {
        super(packet);
        byte[] body = getMessageBody();
        this.meaning = PracticeMessageMeaning.getMeaning((int) body[0]);
        this.boatSourceId = PacketParserUtils.byteArrayToInt(body, 1, 4);
    }

    @Override
    public void updateRace(IServerData race) {
        Calendar date = Calendar.getInstance();
        long currentTime = date.getTimeInMillis();
        int oneMinInMillis = 60000;
        if (this.meaning == PracticeMessageMeaning.START) {
            race.getRace().setStartingTime(new Date(currentTime + oneMinInMillis + 1000*5)); //Add five seconds
            race.getRace().setPracticeRace(true);
        } else if (this.meaning == PracticeMessageMeaning.END) {
            race.getRace().setStartingTime(new Date(currentTime + oneMinInMillis * 3));
            race.getRace().removeBoat(boatSourceId);
            race.getRace().setPracticeRace(false);
        }
    }

    public PracticeMessageMeaning getMeaning() {
        return meaning;
    }
}
