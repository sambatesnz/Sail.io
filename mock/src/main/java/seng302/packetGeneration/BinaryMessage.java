package seng302.packetGeneration;


import seng302.packetGeneration.MessageHeaderGeneration.MessageHeader;

import java.util.zip.CRC32;


public abstract class BinaryMessage {

    private static int CRC_SIZE = 4;

    /**
     * Creates a binary message containing a header, body and crc
     * @return byte array of the whole message
     */
    public final byte[] createMessage() {
        byte[] messageBody = getBody();


        int messageType = getMessageType();

        MessageHeader messageHeader = new MessageHeader(messageType, -1, messageBody.length);
        byte[] header = messageHeader.getBody();

        int crc = calculateCRC(header, messageBody);
        byte[] crcSection = PacketGenerationUtils.intToFourBytes(crc);

        byte[] message = new byte[header.length + messageBody.length + CRC_SIZE];
        System.arraycopy(header, 0, message, 0, header.length);
        System.arraycopy(messageBody, 0, message, header.length, messageBody.length);
        System.arraycopy(crcSection, 0, message, header.length, crcSection.length);

        return message;
    }


    /**
     * Calulcates the check sum of the packet by using the crc of the header + message body
     * @param header header of the message
     * @param messageBody body of the message
     * @return the CRC value as an integer
     */
    private int calculateCRC(byte[] header, byte[] messageBody)
    {
        byte[] headerAndBody = new byte[header.length + messageBody.length];
        System.arraycopy(header, 0, headerAndBody, 0, header.length);
        System.arraycopy(messageBody, 0, headerAndBody, header.length, messageBody.length);
        CRC32 crc32 = new CRC32();
        crc32.update(headerAndBody);
        return (int) crc32.getValue();
    }

    /**
     * Gets the body of a message as defined by the AC35 specification
     * @return byte array of the body
     */
    public abstract byte[] getBody();

    /**
     * Gets the type of message as defined by the AC35 specification
     * @return byte[] array containing the converted message type
     */
    protected abstract int getMessageType();

}
