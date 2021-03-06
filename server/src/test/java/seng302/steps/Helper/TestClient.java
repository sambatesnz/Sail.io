package seng302.steps.Helper;

import seng302.Client.Messages.RaceRegistrationMessage;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.ClientConnections.Client;
import seng302.PacketGeneration.BinaryMessage;

import java.io.IOException;

/**
 *
 */
public class TestClient extends Client{

    public TestClient(String ipAddress, int port) {
        super(ipAddress, port);
    }

    @Override
    protected void connectionEvent() throws IOException {
        BinaryMessage rrm = new RaceRegistrationMessage(RaceRegistrationType.PARTICIPATE);
        super.getStreamOutput().write(rrm.createMessage());
        super.getStreamOutput().flush();
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void processStreams() {
        while (super.clientSocket != null && super.streamInput != null && super.streamOutput != null) {
            try {
                Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
    }

}




//public class TestClient {
//    private Socket clientSocket;
//    private InputStream streamInput;
//    private OutputStream streamOutput;
//    private String serverName;
//    private byte[] dataReceived;
//
//    private String host;
//    private int port;
//
//    public TestClient(String ipAddr, int port) {
//        dataReceived = new byte[4300];
//        clientSocket = null;
//        streamInput = null;
//        streamOutput = null;
//        this.serverName = ipAddr;
//        this.port = port;
//        System.out.println(this.port);
//        try {
//            host = new URL(serverName).getHost();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Handles both incoming and outgoing packets
//     */
//    public void processStreams() {
//        while (clientSocket != null && streamInput != null && streamOutput != null) {
//            try {
//                Thread.sleep(0);
//                nextMessage();
//                sendMessage();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Arrays.fill(dataReceived, (byte)0);
//        } BinaryMessage rrm = new RaceRegistrationMessage(RaceRegistrationType.PARTICIPATE);
//            streamOutput.write(rrm.createMessage());
//            streamOutput.flush();
//        disconnect();
//    }
//
//    private void nextMessage() throws IOException{
//
//        final int HEADER_LEN = 15;
//        final int CRC_LEN = 4;
//        if (streamInput.available() < HEADER_LEN){
//            return;
//        }
//        byte[] head = new byte[HEADER_LEN];
//        streamInput.mark(HEADER_LEN + 1);
//        streamInput.read(head);
//        // if !sync bytes then reset + read one byte + return;
//        if (head.length >1) {
//            if (!(head[0] == (byte)0x47 && head[1] == (byte)0x83)){
//                streamInput.reset();
//                streamInput.read(new byte[1]);
//                return;
//            }
//        }
//
//        byte[] lenBytes = new byte[4];
//        System.arraycopy(head, 13, lenBytes, 0, 2);
//        int messageLength = ByteBuffer.wrap(lenBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
//        if(streamInput.available() < messageLength + CRC_LEN){
//            streamInput.reset();
//            return;
//        }
//        byte[] body = new byte[messageLength + CRC_LEN];
//        streamInput.read(body);
//
//
//
//        byte[] message = new byte[messageLength + CRC_LEN + HEADER_LEN];
//        System.arraycopy(head, 0, message, 0, HEADER_LEN);
//        System.arraycopy(body, 0, message, HEADER_LEN, messageLength);
//        /*Message packet = new Message(message, race);
//        packet.parseMessage();*/
//    }
//
//    /**
//     * Sends BoatActionMessages over a socket stream
//     * @throws IOException
//     */
//    private void sendMessage() throws IOException {
//    }
//
//    public void connect() {
//        try {
//            System.out.println("Attempting to connect...");
//            clientSocket = new Socket(host, port);
//            System.out.println("Connected.");
//            streamInput = new BufferedInputStream(clientSocket.getInputStream());
//            streamOutput = new BufferedOutputStream(clientSocket.getOutputStream());
//
//            BinaryMessage rrm = new RaceRegistrationMessage(RaceRegistrationType.PARTICIPATE);
//            streamOutput.write(rrm.createMessage());
//            streamOutput.flush();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void disconnect() {
//        try {
//            System.out.println("Socket disconnected.");
//            clientSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
