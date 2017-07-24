package seng302.Client;
import seng302.Client.Messages.*;
import seng302.Race.Race;
import seng302.UserInputController.KeyBindingUtility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Client {
    private Socket clientSocket;
    private InputStream streamInput;
    private OutputStream streamOutput;
    private String serverName;
    private byte[] dataReceived;

    private String host;
    private int port;
    private Race race;

    public Client(Race race) {
        this.race = race;
        dataReceived = new byte[4300];
        clientSocket = null;
        streamInput = null;
        streamOutput = null;
        AppConfig config = new AppConfig();
        serverName = config.getProperty(AppConfig.DATA_HOST_NAME);
        port = Integer.parseInt(config.getProperty(AppConfig.DATA_HOST_PORT));
        try {
            host = new URL(serverName).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles both incoming and outgoing packets
     */
    public void processStreams() {
        while (clientSocket != null && streamInput != null && streamOutput != null) {
            try {
                Thread.sleep(0);
                nextMessage();
                sendMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Arrays.fill(dataReceived, (byte)0);
        }
        disconnect();
    }

    private void nextMessage() throws IOException{

        final int HEADER_LEN = 15;
        final int CRC_LEN = 4;
        if (streamInput.available() < HEADER_LEN){
            return;
        }
        byte[] head = new byte[HEADER_LEN];
        streamInput.mark(HEADER_LEN + 1);
        streamInput.read(head);
        // if !sync bytes then reset + read one byte + return;
        if (head.length >1) {
            if (!(head[0] == (byte)0x47 && head[1] == (byte)0x83)){
                streamInput.reset();
                streamInput.read(new byte[1]);
                return;
            }
        }

        byte[] lenBytes = new byte[4];
        System.arraycopy(head, 13, lenBytes, 0, 2);
        int messageLength = ByteBuffer.wrap(lenBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
        if(streamInput.available() < messageLength + CRC_LEN){
            streamInput.reset();
            return;
        }
        byte[] body = new byte[messageLength + CRC_LEN];
        streamInput.read(body);



        //TODO: pass message in to the thing
        byte[] message = new byte[messageLength + CRC_LEN + HEADER_LEN];
        System.arraycopy(head, 0, message, 0, HEADER_LEN);
        System.arraycopy(body, 0, message, HEADER_LEN, messageLength);
        Message packet = new Message(message, race);
        packet.parseMessage();
    }

    /**
     * Sends BoatActionMessages over a socket stream
     * @throws IOException
     */
    private void sendMessage() throws IOException {
        if (KeyBindingUtility.keyPressed()){
            byte[] packetToSend = KeyBindingUtility.getUserInputData();
            streamOutput.write(packetToSend);
            streamOutput.flush();
        }
    }

    public void connect() {
        try {
            System.out.println("Attempting to connect...");
            clientSocket = new Socket(host, port);
            System.out.println("Connected.");
            streamInput = new BufferedInputStream(clientSocket.getInputStream());
            streamOutput = new BufferedOutputStream(clientSocket.getOutputStream());
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
}