package seng302.packetGeneration;


import seng302.packetGeneration.MessageHeaderGeneration.MessageHeader;


public abstract class BinaryMessage {


    public final byte[] createMessage() {
        byte[] messageBody = getBody();
        int messageType = getMessageType();

        MessageHeader messageHeader = new MessageHeader(messageType, -1, messageBody.length);


        return new byte[0];
    }

    public abstract byte[] getBody();


    /**
     * Gets the type of message as defined by the AC35 specification
     * @return byte[] array containing the converted message type
     */
    protected abstract int getMessageType();

    private byte[] getCRC(){
        return null;
    }

    private byte[] getHeader(int messageType) {
        return null;
    }

}
