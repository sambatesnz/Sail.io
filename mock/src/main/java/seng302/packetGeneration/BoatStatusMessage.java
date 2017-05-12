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

    public BoatStatusMessage(int sourceBoatID, char status, char legNumber,
                             long estTimeToNextMark, long estTimeToFinish){
        this.sourceBoatID = RaceStatusUtility.intToFourBytes(sourceBoatID);
        this.status = RaceStatusUtility.charToOneByte(status);
        this.legNumber = RaceStatusUtility.charToOneByte(legNumber);
        this.numPenaltiesAwarded = RaceStatusUtility.charToOneByte('0');
        this.numPenaltiesServed = RaceStatusUtility.charToOneByte('0');
        this.estTimeToNextMark = RaceStatusUtility.longToSixBytes(estTimeToNextMark);
        this.estTimeToFinish = RaceStatusUtility.longToSixBytes(estTimeToFinish);
    }

    public byte[] getBoatStatusMessage(){
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
