package seng302.packetGeneration.BoatLocationGeneration;

import org.junit.Test;
import seng302.packetGeneration.PacketUtils;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for BoatLocationMessageDeprecated class
 */
public class BoatLocationMessageTest {

    short ZERO = 0;

    private int versionNumber;
    private long time;
    private int sourceId;
    private int sequenceNum;
    private int deviceType;
    private double latitude;
    private double longitude;
    private long altitude;
    private short heading;
    private int pitch;
    private int roll;
    private short boatSpeed;
    private short cog;
    private short sog;
    private short apparantWindSpeed;
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
        this.versionNumber = generator.nextInt(twoToThe(BoatLocationUtility.MESSAGE_VERSION));
        this.time = System.currentTimeMillis();
        this.sourceId = generator.nextInt();
        this.sequenceNum = generator.nextInt();
        this.deviceType = generator.nextInt();
        this.latitude = generator.nextDouble();
        this.longitude = generator.nextDouble();
        this.altitude = generator.nextInt();
        this.heading =  generateShort(generator);
        this.pitch = generator.nextInt();
        this.roll = generator.nextInt();
        this.boatSpeed = generateShort(generator);
        this.cog = generateShort(generator);
        this.sog = generateShort(generator);
        this.apparantWindSpeed = generateShort(generator);
        this.apparantWindAngle = generateShort(generator);
        this.trueWindSpeed = generateShort(generator);
        this.trueWindDirection = generateShort(generator);
        this.trueWindAngle = generateShort(generator);
        this.currentDrift = generateShort(generator);
        this.currentSet = generateShort(generator);
        this.rudderAngle = generateShort(generator);

        this.boatLocationMessage= new BoatLocationMessage(versionNumber, time, sourceId, sequenceNum,
                deviceType, latitude, longitude, altitude, heading, pitch, roll, boatSpeed, cog,
        sog, apparantWindAngle, apparantWindAngle, trueWindSpeed, trueWindDirection, trueWindAngle, currentDrift, currentSet, rudderAngle);

        this.message = boatLocationMessage.getBoatLocationMessage();
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


    //This is just here to make it faster to add new tests by copying this as a default
    @Test
    public void t() throws Exception {
        assertTrue(false); //Not implemented yet
    }

    @Test
    public void testMessagePacketSize() throws Exception {
        int packetSize = 56; //As defined by specification
        int actualPacketSize = boatLocationMessage.getBoatLocationMessage().length;
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
    public void testTime() throws Exception{
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
        int seqNumber = 43;
        BoatLocationMessage  boatLocationMessage = new BoatLocationMessage(
                0, 0, 0,
                seqNumber,0,0,
                0,0,ZERO,
                0,0,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO
        );

        byte[] message = boatLocationMessage.getBoatLocationMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.SEQUENCE_NUMBER.getIndex();
        int size = BoatLocationUtility.SEQUENCE_NUMBER.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(seqNumber, actualSourceId);

        sourceId();
    }









//    @Test
//    public void testBoatPositionMessage() throws Exception {
//        Boat boat = new Boat("Boat1", 33.33, Color.BLUE, "USA");
//        BoatLocationMessageDeprecated boatLocationMessage = new BoatLocationMessageDeprecated();
//        byte[] bytes = boatLocationMessage.boatPositionMessage(boat);
//        assertEquals(BoatLocationMessageDeprecated.BOAT_POSITION_LENGTH + BoatLocationMessageDeprecated.HEADER_LENGTH + BoatLocationMessageDeprecated.CRC_LENGTH, bytes.length);
//
//        byte[] bytesArray = new byte[bytes.length - 4];
//        for (int i = 0; i < bytes.length - 4; i++) {
//            bytesArray[i] = bytes[i];
//        }
//        byte[] realCRC = boatLocationMessage.calculateChecksum(bytesArray);
//
//        byte[] CRC = new byte[4];
//        for (int i = 0; i < 4; i++) {
//            CRC[i] = bytes[i + bytes.length - 4];
//        }
//
//        assertArrayEquals(realCRC, CRC); // testing CRC (at end of boatLocationMessage)
//    }


}
