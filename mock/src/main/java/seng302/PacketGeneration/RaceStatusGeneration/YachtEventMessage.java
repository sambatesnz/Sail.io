package seng302.PacketGeneration.RaceStatusGeneration;

import com.sun.xml.internal.ws.api.message.Packet;
import seng302.PacketGeneration.PacketGenerationUtils;

import java.nio.ByteBuffer;

/**
 * Created by msi52 on 1/09/17.
 */
public class YachtEventMessage {

    private byte[] messageVersionNumber;
    private byte[] time;
    private byte[] ackNumber;
    private byte[] raceId;
    private byte[] destinationSourceId; // boat source id
    private byte[] incidentId;
    private byte[] eventId;

    /**
     * Turns a boat object into a the Boat Status section of the Race Status Message Packet
     * The penalties field according to the AC35 specification is ignored as we do no use it yet.
     * It is set to a default value of 0 in both cases
     * @param sourceBoatID Boat's id
     * @param status Boat's status eg. 1=Undefined
     * @param legNumber Number of leg that boat is on
     */
    YachtEventMessage(int sourceBoatID, char status, char legNumber,
                      long time, int ackNumber, int raceId, int incidentId, int eventId){
        this.messageVersionNumber = PacketGenerationUtils.intToOneByte(2);
        this.time = PacketGenerationUtils.longToSixBytes(time);
        this.ackNumber = PacketGenerationUtils.intToTwoBytes(ackNumber);
        this.raceId = PacketGenerationUtils.intToFourBytes(raceId);
        this.destinationSourceId = PacketGenerationUtils.intToFourBytes(sourceBoatID);
        this.incidentId = PacketGenerationUtils.intToFourBytes(incidentId);
        this.eventId = PacketGenerationUtils.intToOneByte(eventId);
    }

    /**
     * @return the byte[] packet for a boatsStatus
     */
    byte[] getYachtEventMessage(){
        ByteBuffer yachtEventStatus = PacketGenerationUtils.LEBuffer(22);

        yachtEventStatus.put(messageVersionNumber);
        yachtEventStatus.put(time);
        yachtEventStatus.put(ackNumber);
        yachtEventStatus.put(raceId);
        yachtEventStatus.put(destinationSourceId);
        yachtEventStatus.put(incidentId);
        yachtEventStatus.put(eventId);

        return yachtEventStatus.array();
    }

}
