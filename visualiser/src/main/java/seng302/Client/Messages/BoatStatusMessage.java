package seng302.Client.Messages;


import seng302.PacketGeneration.RaceStatusGeneration.BoatStatus;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Race;

import java.util.Map;

/**
 * Class that holds and can update the details of boats within a yacht race,
 * which is contained in the race status packets.
 */

public class BoatStatusMessage {
    private int sourceID;
    private BoatStatus status;
    private int legNumber;
    private long estTimeToNextMark;
    private long estTimeToFinish;

    private Map<Integer, GenericBoat> boatDict;

    /**
     * Constructor for the class. Takes the data extracted from the packet and prepares it
     * to alter the details of the boat given by source ID.
     * @param sourceID The identifier for the boat
     * @param status The status of the boat
     * @param legNumber The index of the leg the boat is currently in
     * @param estTimeToNextMark The estimated time for the boat to reach the next mark (in ms)
     * @param estTimeToFinish The estimated time for the boat to finish (in ms)
     * @param race the race that is being raced
     */
    public BoatStatusMessage(long sourceID, BoatStatus status, int legNumber,
                             long estTimeToNextMark, long estTimeToFinish, Race race) {
        this.sourceID = (int)sourceID;
        this.status = status;
        this.legNumber = legNumber;
        this.estTimeToNextMark = estTimeToNextMark;
        this.estTimeToFinish = estTimeToFinish;
        this.boatDict = race.getBoatsMap();
    }

    /**
     * Sets the following details of the boat based on the received information
     * from the race status packet:
     * - Status
     * - Leg number
     * - Estimated time to next mark
     * - Estimated time to finish
     */
    public void setBoatDetails() {
        if (null != boatDict && boatDict.containsKey(sourceID)) {
            GenericBoat boat = boatDict.get(sourceID);
            System.out.println("boat with id " + boat.getSourceId() + " has status of" + status);
            boat.setStatus(status);
            boat.setCurrentLegIndex(legNumber);
            if (boat.getTargetMarkIndex() < legNumber) {
                boat.passMark();
            }
            boat.setTimeToNextMark(estTimeToNextMark);
            boat.setTimeToFinish(estTimeToFinish);
        }
    }


}
