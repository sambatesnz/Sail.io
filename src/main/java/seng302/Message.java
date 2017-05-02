package seng302;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Messages used in the data streams
 */
public class Message {
    private static final int VALID_TIME_MILLI = 1000;
    public static final short BOAT_POSITION_LENGTH= 56;
    public static final short BOAT_POS_HEADER_LENGTH = 15;
    public static final short XML_HEADER_LENGTH = 15;
    public static final short BOAT_POSITION_MESSAGE_TYPE = 37;
    private static final byte SYNC_BYTE_1 = (byte) 0x47;
    private static final byte SYNC_BYTE_2 = (byte) 0x83;
    public static final int CRC_LENGTH = 4;

    private int messageId;

    public Message() {
        messageId = Integer.MIN_VALUE;
    }

    private static ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    public byte[] xmlMessage(String xml, short ackN, short seqNum) {
        byte messageType = 26;
        byte versionNum = 0x01;
        byte[] ackNumber = LEBuffer(2).putShort(ackN).array();
        byte[] time = LEBuffer(8).putLong(System.currentTimeMillis()).array();
        byte[] timestamp = Arrays.copyOfRange(time, 2, 8);
        byte xmlMsgSubType = 0x00;
        byte[] seqNumber = LEBuffer(2).putShort(seqNum).array();
        byte[] xmlBytes = xml.getBytes(StandardCharsets.UTF_8);
        byte[] xmlBody = Arrays.copyOf(xmlBytes, xmlBytes.length+1);
        byte[] xmlBodyLen = LEBuffer(2).putShort((short) xmlBody.length).array();

        ByteBuffer bytes = LEBuffer(XML_HEADER_LENGTH + xmlBody.length);
        bytes.put(versionNum);
        bytes.put(ackNumber);
        bytes.put(timestamp);
        bytes.put(xmlMsgSubType);
        bytes.put(seqNumber);
        bytes.put(xmlBodyLen);
        bytes.put(xmlBody);

        return bytes.array();
    }

    public byte[] message(int type, int length, byte[] body) {

    }

    public byte[] boatPositionMessage(Boat boat) {
        byte pitch = 37;
        byte[] time = LEBuffer(8).putLong(System.currentTimeMillis()).array();
        byte[] timestamp = Arrays.copyOfRange(time, 2, 8);
        byte[] messageLength = LEBuffer(2).putShort(BOAT_POSITION_LENGTH).array();
        byte[] messageID = LEBuffer(4).putInt(messageId++).array();

        ByteBuffer header = LEBuffer(BOAT_POS_HEADER_LENGTH);
        header.put(SYNC_BYTE_1);
        header.put(SYNC_BYTE_2);
        header.put(messageType);
        header.put(timestamp);
        header.put(messageID);
        header.put(messageLength);

        ByteBuffer bytes = LEBuffer(BOAT_POS_HEADER_LENGTH + BOAT_POSITION_LENGTH);
        bytes.put(header.array());
        bytes.put(boatPositionBody(boat));
        ByteBuffer bytesPCRC = LEBuffer(BOAT_POS_HEADER_LENGTH + BOAT_POSITION_LENGTH + CRC_LENGTH);
        bytesPCRC.put(bytes.array());
        bytesPCRC.put(calculateChecksum(bytes.array()));
        return bytesPCRC.array();
    }
    public byte[] calculateChecksum(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return LEBuffer(4).putInt((int) crc32.getValue()).array();
    }

    public byte[] boatPositionBody(Boat boat) {
        byte[] time = LEBuffer(8).putLong(System.currentTimeMillis() + VALID_TIME_MILLI).array();
        byte[] timestamp = Arrays.copyOfRange(time, 2, 8);
        byte[] sourceID = new byte[4];
        byte[] abrv = boat.getAbrv().getBytes(StandardCharsets.UTF_8);
        System.arraycopy(abrv, 0, sourceID, 0, abrv.length);
        byte[] seqNum = {0x00, 0x00, 0x00, 0x01};
        byte[] altitude = {0x00, 0x00, 0x00, 0x00};
        byte[] pitch = {0x00, 0x00};
        byte[] roll = {0x00, 0x00};
        byte versionNum = (byte) 0x01;
        byte deviceType = (byte) 0x01;
        byte[] speed = LEBuffer(2).putShort((short) (boat.getSpeed() * 1000)).array();

        //scaled down to fit into number of bytes
        byte[] latitude = LEBuffer(4).putInt(get4BytePos(boat.getLatitude())).array();
        byte[] longitude = LEBuffer(4).putInt(get4BytePos(boat.getLongitude())).array();
        byte[] heading = LEBuffer(2).putShort(get2ByteHeading(boat.getHeading())).array();

        ByteBuffer bytes = LEBuffer(BOAT_POSITION_LENGTH);
        bytes.put(versionNum);
        bytes.put(timestamp);
        bytes.put(sourceID);
        bytes.put(seqNum);
        bytes.put(deviceType);
        bytes.put(latitude);
        bytes.put(longitude);
        bytes.put(altitude);
        bytes.put(heading);
        bytes.put(pitch);
        bytes.put(roll);
        bytes.put(speed);
        return bytes.array();
    }

    public int get4BytePos(double pos){
        return (int) ((pos+180)/360*(1L << 32L)-(1L << 31L));
    }
    public short get2ByteHeading(double heading){
        return (short) ((heading)/360*(1L << 16L));
    }
}
