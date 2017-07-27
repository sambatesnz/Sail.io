package seng302.PacketGeneration;

import org.junit.Test;
import seng302.PacketGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.PacketGeneration.BoatLocationGeneration.BoatLocationUtility;

import java.util.Random;
import java.util.zip.CRC32;

import static org.junit.Assert.assertEquals;

/**
 * Tests the creation of binary message packets
 */
public class BinaryMessageTest {

    private int headerSize = 15;
    private int boatLocationSize = 56;
    private int CRCSize = 4;


    private BoatLocationMessage generateBoatLocationMessage() {
        Random generator = new Random();
        int versionNumber = generator.nextInt(twoToThe(BoatLocationUtility.MESSAGE_VERSION)); //Max six is one integer
        long time = System.currentTimeMillis();
        int sourceId = generator.nextInt();
        int sequenceNum = generator.nextInt();
        int deviceType = generator.nextInt(13); //There 13 different possible device types
        double latitude = generator.nextDouble();
        double longitude = generator.nextDouble();
        int altitude = generator.nextInt();
        short heading =  generateShort(generator);
        int pitch = generator.nextInt();
        int roll = generator.nextInt();
        short boatSpeed = generateShort(generator);
        short cog = generateShort(generator);
        short sog = generateShort(generator);
        short apparentWindSpeed = generateShort(generator);
        short apparantWindAngle = generateShort(generator);
        short trueWindSpeed = generateShort(generator);
        short trueWindDirection = generateShort(generator);
        short trueWindAngle = generateShort(generator);
        short currentDrift = generateShort(generator);
        short currentSet = generateShort(generator);
        short rudderAngle = generateShort(generator);

        return new BoatLocationMessage(versionNumber, time, sourceId, sequenceNum,
                deviceType, latitude, longitude, altitude, heading, pitch, roll, boatSpeed, cog,
                sog, apparentWindSpeed, apparantWindAngle, trueWindSpeed, trueWindDirection, trueWindAngle, currentDrift, currentSet, rudderAngle);
    }

    @Test
    public void messageSizeForBoatLocation() throws Exception {
        BinaryMessage message = generateBoatLocationMessage();
        byte[] messageBody = message.createMessage();

        int expectedSize = headerSize + boatLocationSize + CRCSize;
        assertEquals(expectedSize, messageBody.length); //Dont know how to validate crc yet
    }

    @Test
    public void crc() {
        byte[] actualMessage = new byte[8];
        BinaryMessage message = generateBoatLocationMessage();
        byte[] messageBody = message.createMessage();
        int sourceIndex = headerSize + boatLocationSize;
        int actualCrc = PacketUtils.getIntFromByteArray(messageBody, sourceIndex, actualMessage, CRCSize);
        long longCRC = Integer.toUnsignedLong(actualCrc);
        CRC32 crc = new CRC32();
        crc.update(messageBody, 0, messageBody.length - CRCSize);
        long foundCRC = crc.getValue();
        assertEquals(foundCRC, longCRC);
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

}