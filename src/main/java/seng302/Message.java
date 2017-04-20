package seng302;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Messages used in the data streams
 */
public class Message {
    private static final int VALID_TIME_MILLI = 1000;
    public static final short BOAT_POSITION_LENGTH = 56;
    public static final short HEADER_LENGTH = 15;
    private static final byte SYNC_BYTE_1 = (byte) 0x47;
    private static final byte SYNC_BYTE_2 = (byte) 0x83;
    public static final int CRC_LENGTH = 4;

    private int messageId;

    public Message() {
        messageId = Integer.MIN_VALUE;
    }

    public List<Byte> boatPositionMessage(Boat boat) {
        byte[] time = ByteBuffer.allocate(8).putLong(System.currentTimeMillis()).array();
        byte[] timestamp = Arrays.copyOfRange(time, 2, 8);
        byte messageType = 37;
        byte[] messageLength = ByteBuffer.allocate(2).putShort(BOAT_POSITION_LENGTH).array();
        byte[] messageID = ByteBuffer.allocate(4).putInt(messageId++).array();

        List<Byte> header = new ArrayList<>(HEADER_LENGTH);
        header.add(SYNC_BYTE_1);
        header.add(SYNC_BYTE_2);
        header.add(messageType);
        for (byte b : timestamp) {
            header.add(b);
        }
        for (byte b : messageID) {
            header.add(b);
        }
        for (byte b : messageLength) {
            header.add(b);
        }

        List<Byte> bytes = new ArrayList<>(HEADER_LENGTH + BOAT_POSITION_LENGTH + CRC_LENGTH);
        bytes.addAll(header);
        bytes.addAll(boatPositionBody(boat));

        byte[] CRC = calculateChecksum(bytes);
        for (byte b: CRC) {
            bytes.add(b);
        }
        return bytes;
    }
    public byte[] calculateChecksum(List<Byte> bytes) {
        CRC32 crc32 = new CRC32();
        byte[] bytesArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytesArray[i] = bytes.get(i);
        }
        crc32.update(bytesArray);
        return ByteBuffer.allocate(4).putInt((int) crc32.getValue()).array();
    }

    public List<Byte> boatPositionBody(Boat boat) {
        byte messageType = (byte) 0x37;
        byte[] time = ByteBuffer.allocate(8).putLong(System.currentTimeMillis() + VALID_TIME_MILLI).array();
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
        byte[] speed = ByteBuffer.allocate(2).putShort((short) (boat.getSpeed() * 1000)).array();

        //scaled down to fit into number of bytes
        byte[] latitude = ByteBuffer.allocate(4).putInt(get4BytePos(boat.getLatitude())).array();
        byte[] longitude = ByteBuffer.allocate(4).putInt(get4BytePos(boat.getLongitude())).array();
        byte[] heading = ByteBuffer.allocate(2).putShort(get2ByteHeading(boat.getHeading())).array();

        List<Byte> bytes = new ArrayList<>(BOAT_POSITION_LENGTH);
        bytes.add(versionNum);
        for (byte b : timestamp) {
            bytes.add(b);
        }
        for (byte b : sourceID) {
            bytes.add(b);
        }
        for (byte b : seqNum) {
            bytes.add(b);
        }
        bytes.add(deviceType);
        for (byte b : latitude) {
            bytes.add(b);
        }
        for (byte b : longitude) {
            bytes.add(b);
        }
        for (byte b : altitude) {
            bytes.add(b);
        }
        for (byte b : heading) {
            bytes.add(b);
        }
        for (byte b : pitch) {
            bytes.add(b);
        }
        for (byte b : roll) {
            bytes.add(b);
        }
        for (byte b : speed) {
            bytes.add(b);
        }
        for (int i = 0; i < 20; i++) {
            bytes.add((byte) 0x00);
        }
        return bytes;
    }

    public int get4BytePos(double pos){
        return (int) ((pos+180)/360*(1L << 32L)-(1L << 31L));
    }
    public short get2ByteHeading(double heading){
        return (short) ((heading)/360*(1L << 16L));
    }
}
