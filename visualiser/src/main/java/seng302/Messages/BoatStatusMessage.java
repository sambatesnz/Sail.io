package seng302.Messages;


import seng302.Boat;
import java.util.HashMap;

/**
 * Class that holds and can update the details of boats within a yacht race,
 * which is contained in the race status packets.
 */

public class BoatStatusMessage {
    private long sourceID;
    private int status;
    private int legNumber;
    private long estTimeToNextMark;
    private long estTimeToFinish;

    private HashMap<Integer, Boat> boatDict;;

    /**
     * Constructor for the class. Takes the data extracted from the packet and prepares it
     * to alter the details of the boat given by source ID.
     * @param sourceID The identifier for the boat
     * @param status The status of the boat
     * @param legNumber The index of the leg the boat is currently in
     * @param estTimeToNextMark The estimated time for the boat to reach the next mark (in ms)
     * @param estTimeToFinish The estimated time for the boat to finish (in ms)
     */
    public BoatStatusMessage(long sourceID, int status, int legNumber,
                             long estTimeToNextMark, long estTimeToFinish) {
        this.sourceID = sourceID;
        this.status = status;
        this.legNumber = legNumber;
        this.estTimeToNextMark = estTimeToNextMark;
        this.estTimeToFinish = estTimeToFinish;
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
        boatDict.get(sourceID).setStatus(status);
        boatDict.get(sourceID).setCurrentLegIndex(legNumber);
        boatDict.get(sourceID).setTimeToNextMark(estTimeToNextMark);
        boatDict.get(sourceID).setTimeToFinish(estTimeToFinish);
    }
}
