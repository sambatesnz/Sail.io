package seng302.Messages;


import seng302.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class LocationMessage{
    private int time;
    private int sourceID;
    private double latitude;
    private double longitude;
    private double heading;
    private int speedOverGround;

    public LocationMessage(byte[] bytes) {
//        messageVersionNumber = data[0];
        time = ByteBuffer.wrap(bytes, 1, 6).order(ByteOrder.LITTLE_ENDIAN).getInt();
        sourceID = ByteBuffer.wrap(bytes, 7, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
//        seqNumber = ByteBuffer.wrap(bytes, 11, 6).order(ByteOrder.LITTLE_ENDIAN).getInt();
        latitude = ByteBuffer.wrap(bytes, 16, 4).order(ByteOrder.LITTLE_ENDIAN).getInt() * 180 / 2147483648.0;
        longitude = ByteBuffer.wrap(bytes, 20, 4).order(ByteOrder.LITTLE_ENDIAN).getInt() * 180 / 2147483648.0;
        heading = ByteBuffer.wrap(bytes, 28, 2).order(ByteOrder.LITTLE_ENDIAN).getInt() * 360 / 65536.0;
        speedOverGround = ByteBuffer.wrap(bytes, 38, 2).order(ByteOrder.LITTLE_ENDIAN).getInt();

    }

}

