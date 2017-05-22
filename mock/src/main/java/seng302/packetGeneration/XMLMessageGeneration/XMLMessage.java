package seng302.packetGeneration.XMLMessageGeneration;

import seng302.MessageType;
import seng302.packetGeneration.BinaryMessage;
import seng302.packetGeneration.PacketGenerationUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Implemention of the xml section of a binary message
 */
public class XMLMessage extends BinaryMessage{

    private byte[] versionNum;
    private byte[] ackNumber;
    private byte[] timestamp;
    private byte[] xmlMsgSubType;
    private byte[] seqNumber;
    private byte[] xmlBytes;
    private byte[] xmlText;
    private byte[] xmlTextLenByte;
    private int xmlTextLenInt;

    // Constructor for XMLMessage.
    public XMLMessage(String xml, short ackN, short seqNum) {
        this.versionNum = PacketGenerationUtils.intToOneByte(0x01);
        this.ackNumber = PacketGenerationUtils.shortToTwoBytes(ackN);
        this.timestamp = PacketGenerationUtils.longToSixBytes(System.currentTimeMillis());
        this.xmlMsgSubType = PacketGenerationUtils.intToOneByte(0x00);
        this.seqNumber = PacketGenerationUtils.shortToTwoBytes(seqNum);
        this.xmlBytes = xml.getBytes(StandardCharsets.UTF_8);
        this.xmlText = Arrays.copyOf(xmlBytes, xmlBytes.length);
        this.xmlTextLenByte = PacketGenerationUtils.shortToTwoBytes((short) xmlText.length);
        this.xmlTextLenInt = xml.length();
    }


    @Override
    public byte[] getBody() {
        byte[] output = new byte[14 + xmlTextLenInt];
        //Copy specific bytes into here
        System.arraycopy(versionNum, 0, output, XMLMessageUtility.MESSAGE_VERSION.getIndex(), XMLMessageUtility.MESSAGE_VERSION.getSize());
        System.arraycopy(ackNumber, 0, output, XMLMessageUtility.ACK_NUM.getIndex(), XMLMessageUtility.ACK_NUM.getSize());
        System.arraycopy(timestamp, 0, output, XMLMessageUtility.TIME_STAMP.getIndex(), XMLMessageUtility.TIME_STAMP.getSize());
        System.arraycopy(xmlMsgSubType, 0, output, XMLMessageUtility.XML_MESSAGE_SUB_TYPE.getIndex(), XMLMessageUtility.XML_MESSAGE_SUB_TYPE.getSize());        System.arraycopy(seqNumber, 0, output, XMLMessageUtility.SEQUENCE_NUMBER.getIndex(), XMLMessageUtility.SEQUENCE_NUMBER.getSize());
        System.arraycopy(xmlTextLenByte, 0, output, XMLMessageUtility.XML_MESSAGE_LENGTH.getIndex(), XMLMessageUtility.XML_MESSAGE_LENGTH.getSize());
        System.arraycopy(xmlBytes, 0, output, XMLMessageUtility.XML_MESSAGE.getIndex(), xmlTextLenInt);
        return output;
    }

    @Override
    protected int getMessageType() {
        return MessageType.XML_MESSAGE.getMessageType();
    }
}

