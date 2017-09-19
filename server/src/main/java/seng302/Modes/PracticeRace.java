package seng302.Modes;

import seng302.DataGeneration.RaceManager;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.RaceStatus;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.RaceObjects.Boat;

import java.util.*;

/**
 * Class that simulates the racing of the boats competing in the America's Cup 35
 * This displays a text-based play by play commentary of the race as it happens
 */
public class PracticeRace extends Race {


    public PracticeRace() {
        super();
    }

    /**
     * Checks the race status, and updates the race information accordingly
     */
    @Override
    public void updateRaceInfo() {
        if (raceStatus != RaceStatus.FINISHED) {

            if (clientIDs.size() < 1) {
                raceStatus = RaceStatus.START_TIME_NOT_SET;
                startingTime = getNewStartTime();
            } else if (startingTime.getTime() < new Date().getTime()) {
                raceStatus = RaceStatus.STARTED;
            } else if (startingTime.getTime() < new Date().getTime() + super.ONE_MINUTE_IN_MILLIS) {
                raceStatus = RaceStatus.PREP;
            } else {
                raceStatus = RaceStatus.WARNING;
            }
        }
    }


    @Override
    public void removeBoat(int clientSocketSourceID) {
    }
}
