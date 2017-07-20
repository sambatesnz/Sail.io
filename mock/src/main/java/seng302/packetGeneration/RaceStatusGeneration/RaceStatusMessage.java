package seng302.packetGeneration.RaceStatusGeneration;

import seng302.Boat;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * For creating race status packets to be sent by the server
 *
 * The primary concern of this class is to copy byte arrays of info (eg currentTime
 * Into the correct location for the race status message packet
 */
public class RaceStatusMessage extends BinaryMessage {

    private byte[] versionNumber;
    private byte[] currentTime;
    private byte[] raceID;
    private byte[] raceStatus;
    private byte[] startTime;
    private byte[] windDirection;
    private byte[] windSpeed;
    private byte[] numberOfBoatsByte;
    private byte[] raceType;
    private byte[] boatsAsBytes;
    private List<Boat> boats;
    private int numberOfBoatsInt;

    public static int CURRENT_VERSION_NUMBER= 2;

    /**
     * Constructor for the Race Status Message, Used when you don't know what version number to give
     * @param currentTime current local time milliseconds
     * @param raceID Id of the race
     * @param raceStatus Status of the race eg Active
     * @param startTime Time the race will start in milliseconds from Jan 1, 1970
     * @param windDirection Direction of wind as an unsigned short
     * @param windSpeed Speed of win in mm/sec
     * @param numberOfBoats Number of boats in the race
     * @param raceType Type of race e.g. Match or Fleet Race
     * @param boats List of boats in the race
     */
    public RaceStatusMessage(long currentTime, int raceID, int raceStatus, long startTime, short windDirection, short windSpeed, char numberOfBoats, char raceType, List<Boat> boats) {
        this.versionNumber = PacketGenerationUtils.intToOneByte(CURRENT_VERSION_NUMBER);
        this.currentTime = PacketGenerationUtils.longToSixBytes(currentTime);
        this.raceID = PacketGenerationUtils.intToFourBytes(raceID);
        this.raceStatus = PacketGenerationUtils.intToOneByte(raceStatus);
        this.startTime = PacketGenerationUtils.longToSixBytes(startTime);
        this.windDirection = PacketGenerationUtils.shortToTwoBytes(windDirection);
        this.windSpeed = PacketGenerationUtils.shortToTwoBytes(windSpeed);
        this.numberOfBoatsByte = PacketGenerationUtils.charToOneByte(numberOfBoats);
        this.numberOfBoatsInt = Character.getNumericValue(numberOfBoats);
        this.raceType = PacketGenerationUtils.charToOneByte(raceType);
        this.boats = boats;

    }

    /**
     * Constructor for the Race Status message.
     * @param versionNumber Version number of the Race Status Message Packet
     * @param currentTime current local time milliseconds
     * @param raceID Id of the race
     * @param raceStatus Status of the race eg Active
     * @param startTime Time the race will start in milliseconds from Jan 1, 1970
     * @param windDirection Direction of wind as an unsigned short
     * @param windSpeed Speed of win in mm/sec
     * @param numberOfBoats Number of boats in the race
     * @param raceType Type of race e.g. Match or Fleet Race
     * @param boats List of boats in the race
     */
    public RaceStatusMessage(int versionNumber, long currentTime, int raceID, int raceStatus, long startTime, short windDirection, short windSpeed, char numberOfBoats, char raceType, List<Boat> boats) {
        this.versionNumber = PacketGenerationUtils.intToOneByte(versionNumber);
        this.currentTime = PacketGenerationUtils.longToSixBytes(currentTime);
        this.raceID = PacketGenerationUtils.intToFourBytes(raceID);
        this.raceStatus = PacketGenerationUtils.intToOneByte(raceStatus);
        this.startTime = PacketGenerationUtils.longToSixBytes(startTime);
        this.windDirection = PacketGenerationUtils.shortToTwoBytes(windDirection);
        this.windSpeed = PacketGenerationUtils.shortToTwoBytes(windSpeed);
        this.numberOfBoatsByte = PacketGenerationUtils.charToOneByte(numberOfBoats);
        this.numberOfBoatsInt = Character.getNumericValue(numberOfBoats);
        this.raceType = PacketGenerationUtils.charToOneByte(raceType);

        this.boats = boats;
    }

    /**
     * Gets the Race Status message as a byte Array
     * @return byte array containing the whole Race Status Message
     */
    @Override
    public byte[] getBody() {
        ByteBuffer body = PacketGenerationUtils.LEBuffer(24 + numberOfBoatsInt * 20);
        //Copy specific bytes into here
        body.put(versionNumber);
        body.put(currentTime);
        body.put(raceID);
        body.put(raceStatus);
        body.put(startTime);
        body.put(windDirection);
        body.put(windSpeed);
        body.put(numberOfBoatsByte);
        body.put(raceType);

        if (boats != null){
            for (Boat boat: boats){
                BoatStatusMessage message = new BoatStatusMessage(boat.getSourceId(), (char)2, (char)boat.getCurrentLegIndex(), 1, 1);
                body.put(message.getBoatStatusMessage());
            }
        }

        return body.array();
    }



    @Override
    protected int getMessageType() {
        return MessageType.RACE_STATUS.getMessageType();
    }
}