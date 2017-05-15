package seng302.packetGeneration;

import java.nio.ByteBuffer;

/**
 * Created by tjg73 on 12/05/17.
 */
public class BoatStatusMessage {

    private byte[] sourceBoatID;
    private byte[] status;
    private byte[] legNumber;
    private byte[] numPenaltiesAwarded;
    private byte[] numPenaltiesServed;
    private byte[] estTimeToNextMark;
    private byte[] estTimeToFinish;

    /**
     * Turns a boat object into a the Boat Status section of the Race Status Message Packet
     * The penalties field according to the AC35 specification is ignored as we do no use it yet.
     * It is set to a default value of 0 in both cases
     * @param sourceBoatID Boat's id
     * @param status Boat's status eg. 1=Undefined
     * @param legNumber Number of leg that boat is on
     * @param estTimeToNextMark Estimated time boat will take to reach next mark
     * @param estTimeToFinish Estimated time boat will take to reach finish
     */
    BoatStatusMessage(int sourceBoatID, char status, char legNumber,
                      long estTimeToNextMark, long estTimeToFinish){
        this.sourceBoatID = RaceStatusUtility.intToFourBytes(sourceBoatID);
        this.status = RaceStatusUtility.charToOneByte(status);
        this.legNumber = RaceStatusUtility.charToOneByte(legNumber);
        this.numPenaltiesAwarded = RaceStatusUtility.charToOneByte('0');
        this.numPenaltiesServed = RaceStatusUtility.charToOneByte('0');
        this.estTimeToNextMark = RaceStatusUtility.longToSixBytes(estTimeToNextMark);
        this.estTimeToFinish = RaceStatusUtility.longToSixBytes(estTimeToFinish);
    }

    /**
     * @return the byte[] packet for a boatsStatus
     */
    byte[] getBoatStatusMessage(){
        ByteBuffer boatStatus = RaceStatusUtility.LEBuffer(20);
        boatStatus.put(sourceBoatID);
        boatStatus.put(status);
        boatStatus.put(legNumber);
        boatStatus.put(numPenaltiesAwarded);
        boatStatus.put(numPenaltiesServed);
        boatStatus.put(estTimeToNextMark);
        boatStatus.put(estTimeToFinish);

        return boatStatus.array();
    }

}
