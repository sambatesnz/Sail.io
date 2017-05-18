package seng302.packetGeneration.BoatLocationGeneration;

import org.junit.Ignore;
import org.junit.Test;
import seng302.packetGeneration.PacketUtils;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for BoatLocationMessage class
 * Works by randomly generating a boat location message
 * Checks if the values can be converted back into the expected type and are of the same value
 *
 */
public class BoatLocationMessageTest {

    private int versionNumber;
    private long time;
    private int sourceId;
    private int sequenceNum;
    private int deviceType;
    private double latitude;
    private double longitude;
    private int altitude;
    private short heading;
    private int pitch;
    private int roll;
    private short boatSpeed;
    private short cog;
    private short sog;
    private short apparentWindSpeed;
    private short apparantWindAngle;
    private short trueWindSpeed;
    private short trueWindDirection;
    private short trueWindAngle;
    private short currentDrift;
    private short currentSet;
    private short rudderAngle;

    private byte[] message;

    private BoatLocationMessage boatLocationMessage;

    public BoatLocationMessageTest(){
        Random generator = new Random();
        this.versionNumber = generator.nextInt(twoToThe(BoatLocationUtility.MESSAGE_VERSION)); //Max six is one integer
        this.time = System.currentTimeMillis();
        this.sourceId = generator.nextInt();
        this.sequenceNum = generator.nextInt();
        this.deviceType = generator.nextInt(13); //There 13 different possible device types
        this.latitude = generator.nextDouble();
        this.longitude = generator.nextDouble();
        this.altitude = generator.nextInt();
        this.heading =  generateShort(generator);
        this.pitch = generator.nextInt();
        this.roll = generator.nextInt();
        this.boatSpeed = generateShort(generator);
        this.cog = generateShort(generator);
        this.sog = generateShort(generator);
        this.apparentWindSpeed = generateShort(generator);
        this.apparantWindAngle = generateShort(generator);
        this.trueWindSpeed = generateShort(generator);
        this.trueWindDirection = generateShort(generator);
        this.trueWindAngle = generateShort(generator);
        this.currentDrift = generateShort(generator);
        this.currentSet = generateShort(generator);
        this.rudderAngle = generateShort(generator);

        this.boatLocationMessage= new BoatLocationMessage(versionNumber, time, sourceId, sequenceNum,
                deviceType, latitude, longitude, altitude, heading, pitch, roll, boatSpeed, cog,
        sog, apparentWindSpeed, apparantWindAngle, trueWindSpeed, trueWindDirection, trueWindAngle, currentDrift, currentSet, rudderAngle);

        this.message = boatLocationMessage.getBody();
    }

    /**
     * Finds 2 to the power of the size of a section of a packet
     * eg version Number is 1 bytes so it can take a max value of 2^8
     * @param value the utility value you want to find the power of
     * @return max integer that will store the amount of bytes
     */
    private int twoToThe(BoatLocationUtility value){
        return (int) Math.pow(2, value.getSize() * 8);
    }

    private short generateShort(Random generator){
        return (short) generator.nextInt(Short.MAX_VALUE + 1);
    }

    @Test
    public void testMessagePacketSize() throws Exception {
        int packetSize = 56; //As defined by specification
        int actualPacketSize = this.message.length;
        assertEquals(packetSize, actualPacketSize);
    }

    @Test
    public void messageVersionNumber() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.MESSAGE_VERSION.getIndex();
        int size = BoatLocationUtility.MESSAGE_VERSION.getSize();
        int actualVersionNumber = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(versionNumber, actualVersionNumber);
    }

    @Test
    public void time() throws Exception{
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.TIME.getIndex();
        int size = BoatLocationUtility.TIME.getSize();
        long actualVersionNumber = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(time, actualVersionNumber);
    }

    @Test
    public void sourceId() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.SOURCE_ID.getIndex();
        int size = BoatLocationUtility.SOURCE_ID.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(sourceId, actualSourceId);
    }

    @Test
    public void sequenceNumber() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.SEQUENCE_NUMBER.getIndex();
        int size = BoatLocationUtility.SEQUENCE_NUMBER.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(sequenceNum, actualSourceId);
    }

    @Test
    public void deviceType() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.DEVICE_TYPE.getIndex();
        int size = BoatLocationUtility.DEVICE_TYPE.getSize();
        int actualDeviceType = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(deviceType, actualDeviceType);
    }

    @Test
    public void latitude() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.LATITUDE.getIndex();
        int size = BoatLocationUtility.LATITUDE.getSize();
        int actualLatitude = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        double convertedLatitude = PacketUtils.locationToInt(actualLatitude);
        assertEquals(latitude, convertedLatitude, 0.000001);
    }

    @Test
    public void longitude() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.LONGITUDE.getIndex();
        int size = BoatLocationUtility.LONGITUDE.getSize();
        int actualLongitude = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        double convertedLongitude = PacketUtils.locationToInt(actualLongitude);
        assertEquals(longitude, convertedLongitude, 0.000001);
    }

    @Test
    public void altitude() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.ALTITUDE.getIndex();
        int size = BoatLocationUtility.ALTITUDE.getSize();
        int actualAltitude = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(altitude, actualAltitude);
    }

    @Test
    public void heading() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.HEADING.getIndex();
        int size = BoatLocationUtility.HEADING.getSize();
        short actualHeading = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(heading, actualHeading);
    }

    @Ignore @Test //Ignored because Not implemented yet
    public void pitch() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.PITCH.getIndex();
        int size = BoatLocationUtility.PITCH.getSize();
        int actualPitch = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(pitch, actualPitch);
    }

    @Ignore @Test //Ignored because Not implemented yet
    public void roll() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.ROLL.getIndex();
        int size = BoatLocationUtility.ROLL.getSize();
        int actualRoll = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(roll, actualRoll );
    }

    @Test
    public void boatSpeed() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.BOAT_SPEED.getIndex();
        int size = BoatLocationUtility.BOAT_SPEED.getSize();
        short actualHeading = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(boatSpeed, actualHeading);
    }

    @Test
    public void cog() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.COG.getIndex();
        int size = BoatLocationUtility.COG.getSize();
        short actualCog = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(cog, actualCog);
    }

    @Test
    public void sog() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.SOG.getIndex();
        int size = BoatLocationUtility.SOG.getSize();
        short acutalSog = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(sog, acutalSog);
    }

    @Test
    public void apparentWindSpeed() throws Exception {
        byte[] actualMessage = new byte[4];
        int sourceIndex = BoatLocationUtility.APPARENT_WIND_SPEED.getIndex();
        int size = BoatLocationUtility.APPARENT_WIND_SPEED.getSize();
        System.out.println(sourceIndex);
        System.out.println(size);
        int actualApparentWindSpeed = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(apparentWindSpeed, actualApparentWindSpeed);
    }

    @Test
    public void apparentWindAngle() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.APPARENT_WIND_ANGLE.getIndex();
        int size = BoatLocationUtility.APPARENT_WIND_ANGLE.getSize();
        short actualApparantWindAngle= (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(apparantWindAngle, actualApparantWindAngle);
    }

    @Test
    public void trueWindSpeed() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.TRUE_WIND_SPEED.getIndex();
        int size = BoatLocationUtility.TRUE_WIND_SPEED.getSize();
        short actualTrueWindSpeed = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(trueWindSpeed, actualTrueWindSpeed);
    }

    @Test
    public void trueWindDirection() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.TRUE_WIND_DIRECTION.getIndex();
        int size = BoatLocationUtility.TRUE_WIND_DIRECTION.getSize();
        short actualTrueWindDirection = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(trueWindDirection, actualTrueWindDirection);
    }

    @Test
    public void trueWindAngle() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.TRUE_WIND_ANGLE.getIndex();
        int size = BoatLocationUtility.TRUE_WIND_ANGLE.getSize();
        short actualTrueWindAngle = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(trueWindAngle, actualTrueWindAngle);
    }

    @Test
    public void currentDrift() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.CURRENT_DRIFT.getIndex();
        int size = BoatLocationUtility.CURRENT_DRIFT.getSize();
        short actualCurrentDrift = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(currentDrift, actualCurrentDrift);
    }

    @Test
    public void currentSet() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.CURRENT_SET.getIndex();
        int size = BoatLocationUtility.CURRENT_SET.getSize();
        short actualCurrentSet = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(currentSet, actualCurrentSet);
    }

    @Test
    public void rudderAngle() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.RUDDER_ANGLE.getIndex();
        int size = BoatLocationUtility.RUDDER_ANGLE.getSize();
        short actualRudderAngle = (short) PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(rudderAngle, actualRudderAngle);
    }

}
