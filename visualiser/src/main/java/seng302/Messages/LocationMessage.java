package seng302.Messages;


import seng302.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class LocationMessage{
    private long time;
    private long sourceID;
    private double latitude;
    private double longitude;
    private double heading;
    private int speedOverGround;

    public LocationMessage(byte[] bytes) {
//        messageVersionNumber = data[0];
        time = Message.byteArrayToLong(bytes, 1, 6);
        sourceID = Message.byteArrayToLong(bytes, 7, 4);
//        seqNumber = Message.byteArrayToInt(bytes, 11, 6);
        latitude = Message.byteArrayToLong(bytes, 16, 4) * 180 / 2147483648.0;
        longitude = Message.byteArrayToLong(bytes, 20, 4) * 180 / 2147483648.0;
        heading = Message.byteArrayToInt(bytes, 28, 2) * 360 / 65536.0;
        speedOverGround = Message.byteArrayToInt(bytes, 38, 2);
        System.out.println(heading);
    }

}

