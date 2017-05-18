package seng302.packetGeneration;


public abstract class BinaryMessage {


    protected abstract byte[] getBody();

    protected byte[] getCRC(){
        return null;
    }

    protected byte[] getHeader(int messageType) {
        return null;
    }

    protected byte[] createMessage(int messageType) {
        return null;
    }


}
