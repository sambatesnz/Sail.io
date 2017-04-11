package seng302;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Messages used in the data streams
 */
public class Message {
    private final byte syncByte1 = (byte) 0x47;
    private final byte syncByte2 = (byte) 0x83;

    public List<Byte> boatPositionMessage(Boat boat) {
        byte messageType = (byte) 0x37;
        byte[] time = ByteBuffer.allocate(8).putLong(System.currentTimeMillis()).array();
        byte[] timeStamp = Arrays.copyOfRange(time, 2, 8);
        byte[] sourceID = {0x00, 0x00, 0x00, 0x01};
        byte[] seqNum = {0x00, 0x00, 0x00, 0x01};
        byte[] altitude = {0x00, 0x00, 0x00, 0x00};
        byte[] pitch = {0x00, 0x00};
        byte[] roll = {0x00, 0x00};
        byte versionNum = (byte) 0x01;

        byte deviceType = (byte) 0x01;

        //scaled down to fit into number of bytes
        byte[] latitude = ByteBuffer.allocate(4).putDouble(get4BytePos(boat.getLatitude())).array();
        byte[] longitude = ByteBuffer.allocate(4).putDouble(get4BytePos(boat.getLongitude())).array();
        byte[] heading = ByteBuffer.allocate(2).putDouble(get2ByteHeading(boat.getHeading())).array();

        List<Byte> bytes = new ArrayList<>(56);
        bytes.add(versionNum);
        for (byte b : timeStamp) {
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
        return bytes;
    }

    public Long get4BytePos(double pos){
        return (long) ((pos+180)/360*(1L << 32L)-(1L << 31L));
    }
    public Long get2ByteHeading(double heading){
        return (long) ((heading)/360*(1L << 16L));
    }
}
