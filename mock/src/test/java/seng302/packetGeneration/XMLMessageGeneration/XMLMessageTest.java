package seng302.packetGeneration.XMLMessageGeneration;

import org.junit.Test;
import seng302.packetGeneration.BinaryMessage;
import seng302.packetGeneration.PacketUtils;

import static org.junit.Assert.*;

/**
 * Tests for xml messages
 */
public class XMLMessageTest {

    private String xml;
    private short ackNumber;
    private short sequenceNumber;

    private byte[] body;


    public XMLMessageTest() {
        this.xml = "<>Hellow world<>";
        this.ackNumber = 5;
        this.sequenceNumber = 42;

        BinaryMessage message = new XMLMessage(xml, ackNumber, sequenceNumber);
        body = message.getBody();
    }

    @Test
    public void messageLength() throws Exception {
        int SIZE_WITHOUT_MESSAGE = 14;
        byte[] message = new byte[8];
        int source = XMLMessageUtility.XML_MESSAGE_LENGTH.getIndex();
        int size = XMLMessageUtility.XML_MESSAGE_LENGTH.getSize();
        int XMLMessageSize = PacketUtils.getIntFromByteArray(body, source, message, size);
        assertEquals(body.length, XMLMessageSize + SIZE_WITHOUT_MESSAGE);
    }

    @Test
    public void messageVersionNumber() throws Exception {
        int MESSAGE_VERSION_NUMBER = 1;
        byte[] message = new byte[8];
        int actualVersionNumber = PacketUtils.getIntFromByteArray(body, XMLMessageUtility.MESSAGE_VERSION.getIndex(), message, XMLMessageUtility.MESSAGE_VERSION.getSize());
        assertEquals(MESSAGE_VERSION_NUMBER, actualVersionNumber);
    }

    @Test
    public void ackNumber() throws Exception {
        byte[] message = new byte[8];
        int actualAckNumber = PacketUtils.getIntFromByteArray(body, XMLMessageUtility.ACK_NUM.getIndex(), message, XMLMessageUtility.ACK_NUM.getSize());
        assertEquals(ackNumber, actualAckNumber);
    }

    @Test
    public void timeStamp() throws Exception {
        byte[] message = new byte[8];
        long nearTimeStamp = System.currentTimeMillis();
        long actualTimeStamp = PacketUtils.getLongFromByteArray(body, XMLMessageUtility.TIME_STAMP.getIndex(), message, XMLMessageUtility.TIME_STAMP.getSize());
        assertEquals(nearTimeStamp, actualTimeStamp, 10); //Check that times are within 10 seconds of each other
    }

    @Test
    public void xmlMessageSubType() throws Exception {
        int MESSAGE_SUB_TYPE = 0; //Currently 0
        byte[] message = new byte[8];
        int actualMessageSubType = PacketUtils.getIntFromByteArray(body, XMLMessageUtility.XML_MESSAGE_SUB_TYPE.getIndex(), message, XMLMessageUtility.XML_MESSAGE_SUB_TYPE.getSize());
        assertEquals(MESSAGE_SUB_TYPE, actualMessageSubType);
    }

    @Test
    public void sequenceNumber() throws Exception {
        byte[] message = new byte[8];
        int actualSequenceNumber = PacketUtils.getIntFromByteArray(body, XMLMessageUtility.SEQUENCE_NUMBER.getIndex(), message, XMLMessageUtility.SEQUENCE_NUMBER.getSize());
        assertEquals(sequenceNumber, actualSequenceNumber);
    }

    @Test
    public void xmlMessageLength() throws Exception {
        byte[] message = new byte[8];
        int actualMessageLength = PacketUtils.getIntFromByteArray(body, XMLMessageUtility.XML_MESSAGE_LENGTH.getIndex(), message, XMLMessageUtility.XML_MESSAGE_LENGTH.getSize());
        byte[] convertedXML = xml.getBytes();
        assertEquals(convertedXML.length, actualMessageLength);
    }

    @Test
    public void xmlMessage() throws Exception {
        byte[] message = new byte[100];
        int index = XMLMessageUtility.XML_MESSAGE.getIndex();
        int actualMessageLength = PacketUtils.getIntFromByteArray(body, XMLMessageUtility.XML_MESSAGE_LENGTH.getIndex(), message, XMLMessageUtility.XML_MESSAGE_LENGTH.getSize());
        byte[] copy = new byte[actualMessageLength];
        System.arraycopy(body, index, copy, 0, actualMessageLength);
        String actualXML = new String(copy , "UTF-8");
        assertTrue(xml.equals(actualXML));
    }

}