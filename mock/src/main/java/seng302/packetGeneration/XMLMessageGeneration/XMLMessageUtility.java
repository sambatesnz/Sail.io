package seng302.packetGeneration.XMLMessageGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class XMLMessageUtility {

    static int MESSAGE_VERSION_POS = 0;
    static int MESSAGE_VERSION_SIZE = 1;
    static int ACK_NUM_POS = 1;
    static int ACK_NUM_SIZE = 2;
    static int TIME_STAMP_POS = 3;
    static int TIME_STAMP_SIZE = 6;
    static int XML_MESSAGE_SUB_TYPE_POS = 9;
    static int XML_MESSAGE_SUB_TYPE_SIZE = 1;
    static int SEQUENCE_NUMBER_POS = 10;
    static int SEQUENCE_NUMBER_SIZE = 2;
    static int XML_MESSAGE_LENGTH_POS = 12;
    static int XML_MESSAGE_LENGTH_SIZE = 2;
    static int XML_MESSAGE_POS = 14;
    static int XML_MESSAGE_SIZE;

    private XMLMessageUtility() {
    }
}
