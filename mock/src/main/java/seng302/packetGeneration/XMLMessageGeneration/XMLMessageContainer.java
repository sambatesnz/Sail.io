package seng302.packetGeneration.XMLMessageGeneration;

import seng302.packetGeneration.PacketGenerationUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by jhl79 on 16/05/17.
 */
public class XMLMessageContainer {

    byte[] versionNum;
    byte[] ackNumber;
    byte[] time;
    byte[] timestamp;
    byte[] xmlMsgSubType;
    byte[] seqNumber;
    byte[] xmlBytes;
    byte[] xmlText;
    byte[] xmlTextLenByte;
    int xmlTextLenInt;

    // Constructor for XMLMessageContainer.
    public XMLMessageContainer(String xml, short ackN, short seqNum) {
        this.versionNum = PacketGenerationUtils.intToOneByte(0x01);
        this.ackNumber = PacketGenerationUtils.shortToTwoBytes(ackN);
        this.time = PacketGenerationUtils.longToSixBytes(System.currentTimeMillis());
        this.timestamp = Arrays.copyOfRange(time, 2, 8);
        this.xmlMsgSubType = PacketGenerationUtils.intToOneByte(0x00);
        this.seqNumber = PacketGenerationUtils.shortToTwoBytes(seqNum);
        this.xmlBytes = xml.getBytes(StandardCharsets.UTF_8);
        this.xmlText = Arrays.copyOf(xmlBytes, xmlBytes.length + 1);
        this.xmlTextLenByte = PacketGenerationUtils.shortToTwoBytes((short) xmlText.length);
        this.xmlTextLenInt = xml.length();
    }

    // Returns a byte array of the XML message.
    public byte[] getXMLMessage() {
        byte[] output = new byte[14 + xmlTextLenInt];
        System.out.println("Total XMLMessage length is: " + (14 + xmlTextLenInt));
        //Copy specific bytes into here
        System.arraycopy(versionNum, 0, output, XMLMessageUtility.MESSAGE_VERSION_POS, XMLMessageUtility.MESSAGE_VERSION_SIZE);
        System.arraycopy(ackNumber, 0, output, XMLMessageUtility.ACK_NUM_POS, XMLMessageUtility.ACK_NUM_SIZE);
        System.arraycopy(timestamp, 0, output, XMLMessageUtility.TIME_STAMP_POS, XMLMessageUtility.TIME_STAMP_SIZE);
        System.arraycopy(xmlMsgSubType, 0, output, XMLMessageUtility.XML_MESSAGE_SUB_TYPE_POS, XMLMessageUtility.XML_MESSAGE_SUB_TYPE_SIZE);
        System.arraycopy(seqNumber, 0, output, XMLMessageUtility.SEQUENCE_NUMBER_POS, XMLMessageUtility.SEQUENCE_NUMBER_SIZE);
        System.arraycopy(xmlTextLenByte, 0, output, XMLMessageUtility.XML_MESSAGE_LENGTH_POS, XMLMessageUtility.XML_MESSAGE_LENGTH_SIZE);
        System.arraycopy(xmlBytes, 0, output, XMLMessageUtility.XML_MESSAGE_POS, xmlTextLenInt);
        return output;
    }
}

