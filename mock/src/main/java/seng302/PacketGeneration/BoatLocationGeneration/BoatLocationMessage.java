package seng302.PacketGeneration.BoatLocationGeneration;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * For the Creation of the Boat Location Message Packets
 * Packet body size should be 56 bytes
 *
 */
public class BoatLocationMessage extends BinaryMessage {

    private byte[] versionNumber;
    private byte[] time;
    private byte[] sourceId;
    private byte[] sequenceNumber;
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
    private byte[] apparentWindSpeed;
    private byte[] apparentWindAngle;
    private byte[] trueWindSpeed;
    private byte[] trueWindDirection;
    private byte[] trueWindAngle;
    private byte[] sailStatus;
    private byte[] currentSet;
    private byte[] rudderAngle;

    public static int CURRENT_VERSION_NUMBER = 1;
    private static int MESSAGE_SIZE = 56;
    private byte[] boatLocationMessage;


    public BoatLocationMessage(int versionNumber, long time, int sourceId,
                               int sequenceNumber, int deviceType,
                               double latitude, double longitude, int altitude,
                               short heading, int pitch, int roll, int boatSpeed,
                               short cog, int sog,
                               short apparentWindSpeed, short apparentWindAngle,
                               short trueWindSpeed, short trueWindDirection, short trueWindAngle,
                               short sailStatus, short currentSet, short rudderAngle) {

        this.boatLocationMessage = new byte[MESSAGE_SIZE];

        this.versionNumber = PacketGenerationUtils.intToOneByte(versionNumber);
        this.time = PacketGenerationUtils.longToSixBytes(time);
        this.sourceId = PacketGenerationUtils.intToFourBytes(sourceId);
        this.sequenceNumber = PacketGenerationUtils.intToFourBytes(sequenceNumber);
        this.deviceType = PacketGenerationUtils.intToOneByte(deviceType);
        this.latitude = PacketGenerationUtils.locationToFourBytes(latitude);
        this.longitude = PacketGenerationUtils.locationToFourBytes(longitude);
        this.altitude = PacketGenerationUtils.intToFourBytes(altitude);
        this.heading = PacketGenerationUtils.headingToTwoBytes(heading);
        //Roll and pitch will go here if implemented
        this.boatSpeed = PacketGenerationUtils.speedToTwoBytes(boatSpeed);

        this.cog = PacketGenerationUtils.shortToTwoBytes(cog);
        this.sog = PacketGenerationUtils.shortToTwoBytes(sog);
        this.apparentWindSpeed = PacketGenerationUtils.shortToTwoBytes(apparentWindSpeed);
        this.apparentWindAngle = PacketGenerationUtils.shortToTwoBytes(apparentWindAngle);
        this.trueWindSpeed = PacketGenerationUtils.shortToTwoBytes(trueWindSpeed );
        this.trueWindDirection = PacketGenerationUtils.shortToTwoBytes(trueWindDirection );
        this.trueWindAngle = PacketGenerationUtils.shortToTwoBytes(trueWindAngle );
        this.sailStatus = PacketGenerationUtils.shortToTwoBytes(sailStatus );
        this.currentSet = PacketGenerationUtils.shortToTwoBytes(currentSet );
        this.rudderAngle = PacketGenerationUtils.shortToTwoBytes(rudderAngle );

    }


    @Override
    public byte[] getBody() {
        int firstIndex = 0;
        System.arraycopy(versionNumber, firstIndex, boatLocationMessage, BoatLocationUtility.MESSAGE_VERSION.getIndex(), BoatLocationUtility.MESSAGE_VERSION.getSize());
        System.arraycopy(time, firstIndex, boatLocationMessage, BoatLocationUtility.TIME.getIndex(), BoatLocationUtility.TIME.getSize());
        System.arraycopy(sourceId, firstIndex, boatLocationMessage, BoatLocationUtility.SOURCE_ID.getIndex(), BoatLocationUtility.SOURCE_ID.getSize());
        System.arraycopy(sequenceNumber, firstIndex, boatLocationMessage, BoatLocationUtility.SEQUENCE_NUMBER.getIndex(), BoatLocationUtility.SEQUENCE_NUMBER.getSize());
        System.arraycopy(deviceType, firstIndex, boatLocationMessage, BoatLocationUtility.DEVICE_TYPE.getIndex(), BoatLocationUtility.DEVICE_TYPE.getSize());
        System.arraycopy(latitude, firstIndex ,boatLocationMessage, BoatLocationUtility.LATITUDE.getIndex(), BoatLocationUtility.LATITUDE.getSize());
        System.arraycopy(longitude, firstIndex ,boatLocationMessage, BoatLocationUtility.LONGITUDE.getIndex(), BoatLocationUtility.LONGITUDE.getSize());
        System.arraycopy(altitude, firstIndex, boatLocationMessage, BoatLocationUtility.ALTITUDE.getIndex(), BoatLocationUtility.ALTITUDE.getSize());
        System.arraycopy(heading, firstIndex, boatLocationMessage, BoatLocationUtility.HEADING.getIndex(), BoatLocationUtility.HEADING.getSize());
        System.arraycopy(boatSpeed, firstIndex, boatLocationMessage, BoatLocationUtility.BOAT_SPEED.getIndex(), BoatLocationUtility.BOAT_SPEED.getSize());
        System.arraycopy(cog, firstIndex, boatLocationMessage, BoatLocationUtility.COG.getIndex(), BoatLocationUtility.COG.getSize());
        System.arraycopy(sog, firstIndex, boatLocationMessage, BoatLocationUtility.SOG.getIndex(), BoatLocationUtility.SOG.getSize());
        System.arraycopy(apparentWindSpeed, firstIndex, boatLocationMessage, BoatLocationUtility.APPARENT_WIND_SPEED.getIndex(), BoatLocationUtility.APPARENT_WIND_SPEED.getSize());
        System.arraycopy(apparentWindAngle, firstIndex, boatLocationMessage, BoatLocationUtility.APPARENT_WIND_ANGLE.getIndex(), BoatLocationUtility.APPARENT_WIND_ANGLE.getSize());
        System.arraycopy(trueWindSpeed, firstIndex, boatLocationMessage, BoatLocationUtility.TRUE_WIND_SPEED.getIndex(), BoatLocationUtility.TRUE_WIND_SPEED.getSize());
        System.arraycopy(trueWindDirection, firstIndex, boatLocationMessage, BoatLocationUtility.TRUE_WIND_DIRECTION.getIndex(), BoatLocationUtility.TRUE_WIND_DIRECTION.getSize());
        System.arraycopy(trueWindAngle, firstIndex, boatLocationMessage, BoatLocationUtility.TRUE_WIND_ANGLE.getIndex(), BoatLocationUtility.TRUE_WIND_ANGLE.getSize());
        System.arraycopy(sailStatus, firstIndex, boatLocationMessage, BoatLocationUtility.SAIL_STATUS.getIndex(), BoatLocationUtility.SAIL_STATUS.getSize());
        System.arraycopy(currentSet, firstIndex, boatLocationMessage, BoatLocationUtility.CURRENT_SET.getIndex(), BoatLocationUtility.CURRENT_SET.getSize());
        System.arraycopy(rudderAngle, firstIndex, boatLocationMessage, BoatLocationUtility.RUDDER_ANGLE.getIndex(), BoatLocationUtility.RUDDER_ANGLE.getSize());
        return boatLocationMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.BOAT_LOCATION.getMessageType();
    }
}
