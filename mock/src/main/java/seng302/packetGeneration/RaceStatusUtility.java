package seng302.packetGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class RaceStatusUtility {

    private RaceStatusUtility() {

    }


    static long convert(){
        return 12345;
    }

    static ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }
}
