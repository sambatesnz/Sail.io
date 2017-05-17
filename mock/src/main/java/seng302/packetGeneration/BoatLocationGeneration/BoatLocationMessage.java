package seng302.packetGeneration.BoatLocationGeneration;

import seng302.packetGeneration.PacketGenerationUtils;

/**
 * For the Creation of the Boat Location Message Packets
 * As defined in page 19 of https://docs.google.com/a/acracemgt.com/viewer?a=v&pid=sites&srcid=YWNyYWNlbWd0LmNvbXxub3RpY2Vib2FyZHxneDoyYTExNTQ4Yzg3ZGRmNTQ1
 * Packet body size should be 56 bytes
 *
 */
public class BoatLocationMessage {

    private byte[] versionNumber;
    private byte[] time;
    private byte[] sourceId;
    private byte[] sequenceNum;
    private byte[] deviceType;
    private byte[] latitude;
    private byte[] longitude;
    private byte[] altitude;
    private byte[] heading;
    private byte[] pitch;
    private byte[] roll;
    private byte[] boatSpeed;
    private byte[] cog;
    private byte[] sog;
    private byte[] apparantWindSpeed;
    private byte[] apparantWindAngle;
    private byte[] trueWindSpeed;
    private byte[] trueWindDirection;
    private byte[] trueWindAngle;
    private byte[] currentDrift;
    private byte[] currentSet;
    private byte[] rudderAngle;

    public static int CURRENT_VERSION_NUMBER = 1;
    private static int MESSAGE_SIZE = 56;
    private byte[] boatLocationMessage;

    public BoatLocationMessage(int versionNumber, long time, int sourceId,
                               int sequenceNum, int deviceType,
                               long latitude, long longitude, long altitude,
                               short heading, int pitch, int roll, short boatSpeed,
                               short cog, short sog,
                               short apparantWindSpeed, short apparantWindAngle,
                               short trueWindSpeed, short trueWindDirection, short trueWindAngle,
                               short currentDrift, short currentSet, short rudderAngle) {

        this.boatLocationMessage = new byte[MESSAGE_SIZE];

        this.versionNumber = PacketGenerationUtils.intToOneByte(versionNumber);
        this.time = PacketGenerationUtils.longToSixBytes(time);

    }


    public byte[] getBoatLocationMessage() {
        int firstIndex = 0;
        System.arraycopy(versionNumber, firstIndex, boatLocationMessage, BoatLocationUtility.MESSAGE_VERSION.getIndex(), BoatLocationUtility.MESSAGE_VERSION.getSize());
        System.arraycopy(time, firstIndex, boatLocationMessage, BoatLocationUtility.TIME_POS.getIndex(), BoatLocationUtility.TIME_POS.getSize());
        return boatLocationMessage;
    }
}
