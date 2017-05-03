package seng302;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class StreamClient {
    //    private StreamParser parser;

    private Socket clientSocket;
    private DataInputStream streamInput;
    private String serverName;
    private int port;
    private byte[] data;
    String output = "";

    public StreamClient() {
        data = new byte[1000];
        clientSocket = null;
        streamInput = null;
        AppConfig config = new AppConfig();
        serverName = config.getProperty(AppConfig.DATA_HOST_NAME);
        port = Integer.parseInt(config.getProperty(AppConfig.DATA_HOST_PORT));
    }

    public void retrieveData() {
        int breakNo = 0;
        while (clientSocket != null && streamInput != null && breakNo < 200) {
            try {
                int result = streamInput.read(data);
                output += new String(data, 0, result);
                breakNo ++;
                System.out.println(breakNo);
                int syncPacket1 = data[0];
                int syncPacket2 = data[1];
//                System.out.printf("SP1: %d, SP2: %d", syncPacket1, syncPacket2);

//                if (syncPacket1 == 71 && syncPacket2 == -125) {
//                    // TODO: Pass to Parser.
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        disconnect();
    }

    public void connect() {
        try {
            String host = new URL(serverName).getHost();
            System.out.println("Attempting to connect...");
            clientSocket = new Socket(host, port);
            System.out.println("Connected.");
            streamInput = new DataInputStream(clientSocket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}