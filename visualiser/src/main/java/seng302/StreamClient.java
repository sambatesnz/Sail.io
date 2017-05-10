package seng302;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class StreamClient {
    //    private StreamParser parser;

    private Socket clientSocket;
    private InputStream streamInput;
    private String serverName;
    private byte[] data;

    private ByteBuffer messageBuffer;
    private byte[] messageBytes;
    private String host;
    private int port;
//    String output = "";

    public StreamClient() {
        data = new byte[4300];
        clientSocket = null;
        streamInput = null;
        AppConfig config = new AppConfig();
        serverName = config.getProperty(AppConfig.DATA_HOST_NAME);
        port = Integer.parseInt(config.getProperty(AppConfig.DATA_HOST_PORT));
        try {
            host = new URL(serverName).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public StreamClient(String host, int port) {
        data = new byte[4300];
        clientSocket = null;
        streamInput = null;
        this.host = host;
        this.port = port;
    }

    public void retrieveData() {
        int breakNo = 0;
        int result = 0;
        boolean moreData = false;
        while (clientSocket != null && streamInput != null && result != -1) {
            try {
                nextMessage();




            } catch (Exception e) {
                e.printStackTrace();
            }
            Arrays.fill(data, (byte)0);
        }
        disconnect();
    }

    private void nextMessage() throws IOException{
        final int HEADER_LEN = 15;
        if (streamInput.available() < HEADER_LEN){
            return;
        }
        byte[] head = new byte[HEADER_LEN];
        streamInput.mark(HEADER_LEN);
        streamInput.read(head);
        byte[] messageLengthBytes = new byte[4];
        byte[] messageTypeBytes = new byte[4];
        System.arraycopy(head,13,messageLengthBytes,0,2);
        System.arraycopy(head,2,messageTypeBytes,0,2);
        int messageLength = ByteBuffer.wrap(messageLengthBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
        int messageType = ByteBuffer.wrap(messageTypeBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
        System.out.println(messageLength);

        if(streamInput.available() < messageLength){
            streamInput.reset();
            return;
        }
        if (messageLength < 0) {
            System.out.println(messageLength);
        }
        byte[] message = new byte[messageLength];
//        System.arraycopy(data,0,message,0,15+messageLength);
        //TODO: passmessage in to the thing

    }

    public void connect() {
        try {
            System.out.println("Attempting to connect...");
            clientSocket = new Socket(host, port);
            System.out.println("Connected.");
            streamInput = new BufferedInputStream(clientSocket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            System.out.println("Socket disconnected.");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeHost() {

    }
}