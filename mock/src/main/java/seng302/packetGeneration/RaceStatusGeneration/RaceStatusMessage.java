package seng302.packetGeneration.RaceStatusGeneration;

import seng302.Boat;
import seng302.MessageType;
import seng302.packetGeneration.BinaryMessage;
import seng302.packetGeneration.PacketGenerationUtils;

import java.util.List;

/**
 * For creating race status packets to be sent by the server
 *
 * The primary concern of this class is to copy byte arrays of info (eg currentTime
 * Into the correct location for the race status message packet
 */
public class RaceStatusMessage extends BinaryMessage{

    private byte[] versionNumber;
    private byte[] currentTime;
    private byte[] raceID;
    private byte[] raceStatus;
    private byte[] startTime;
    private byte[] windDirection;
    private byte[] windSpeed;
    private byte[] numberOfBoatsByte;
    private byte[] raceType;
    private List<byte[]> boats;
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
        this.raceStatus = PacketGenerationUtils.intToFourBytes(raceStatus);
        this.startTime = PacketGenerationUtils.longToSixBytes(startTime);
        this.windDirection = PacketGenerationUtils.shortToTwoBytes(windDirection);
        this.windSpeed = PacketGenerationUtils.shortToTwoBytes(windSpeed);
        this.numberOfBoatsByte = PacketGenerationUtils.charToOneByte(numberOfBoats);
        this.numberOfBoatsInt = Character.getNumericValue(numberOfBoats);
        this.raceType = PacketGenerationUtils.charToOneByte(raceType);
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
        this.raceStatus = PacketGenerationUtils.intToFourBytes(raceStatus);
        this.startTime = PacketGenerationUtils.longToSixBytes(startTime);
        this.windDirection = PacketGenerationUtils.shortToTwoBytes(windDirection);
        this.windSpeed = PacketGenerationUtils.shortToTwoBytes(windSpeed);
        this.numberOfBoatsByte = PacketGenerationUtils.charToOneByte(numberOfBoats);
        this.numberOfBoatsInt = Character.getNumericValue(numberOfBoats);
        this.raceType = PacketGenerationUtils.charToOneByte(raceType);
    }

    /**
     * Gets the Race Status message as a byte Array
     * @return byte array containing the whole Race Status Message
     */
    @Override
    public byte[] getBody() {
        byte[] output = new byte[24 + numberOfBoatsInt * 20];
        //Copy specific bytes into here
        System.arraycopy(versionNumber, 0, output, RaceStatusUtility.MESSAGE_VERSION.getIndex(), RaceStatusUtility.MESSAGE_VERSION.getSize());
        System.arraycopy(currentTime, 0, output, RaceStatusUtility.CURRENT_TIME.getIndex(), RaceStatusUtility.CURRENT_TIME.getSize());
        System.arraycopy(raceID, 0, output, RaceStatusUtility.RACE_ID.getIndex(), RaceStatusUtility.RACE_ID.getSize());
        System.arraycopy(raceStatus, 0, output, RaceStatusUtility.RACE_STATUS.getIndex(), RaceStatusUtility.RACE_STATUS.getSize());
        System.arraycopy(startTime, 0, output, RaceStatusUtility.EXPECTED_START_TIME.getIndex(), RaceStatusUtility.EXPECTED_START_TIME.getSize());
        System.arraycopy(windDirection, 0, output, RaceStatusUtility.WIND_DIRECTION.getIndex(), RaceStatusUtility.WIND_DIRECTION.getSize());
        System.arraycopy(windSpeed, 0, output, RaceStatusUtility.WIND_SPEED.getIndex(), RaceStatusUtility.WIND_SPEED.getSize());
        System.arraycopy(numberOfBoatsByte, 0, output, RaceStatusUtility.NUM_BOATS.getIndex(), RaceStatusUtility.NUM_BOATS.getSize());
        System.arraycopy(raceType, 0, output, RaceStatusUtility.RACE_TYPE.getIndex(), RaceStatusUtility.RACE_TYPE.getSize());
        return output;
    }



    @Override
    protected int getMessageType() {
        return MessageType.RACE_STATUS.getMessageType();
    }
}