package seng302;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class StreamClient {
    //    private StreamParser parser;

    private Socket clientSocket;
    private DataInputStream streamInput;
    private String serverName;
    private byte[] data;
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
                System.out.println("Requesting Data: ");
                result = streamInput.read(data);
                System.out.println("Data read in.");
//                output += new String(data, 0, result);
                breakNo ++;
                //System.out.println(breakNo);
                int syncPacket1 = data[0];
                int syncPacket2 = data[1];
                int dataType = data [2];
                //System.out.printf("SP1: %d, SP2: %d", syncPacket1, syncPacket2);
//                System.out.println(Arrays.toString(data));
                byte[] dest = new byte[2];
                // Retrieve the length of the packet from the header
                System.arraycopy(data,13,dest,0,2);
                short length = ByteBuffer.wrap(dest).order(ByteOrder.LITTLE_ENDIAN).getShort();
                //System.out.println(length);
                if ((syncPacket1 == 71 && syncPacket2 == -125) || moreData) {
                    //System.out.println("Valid Packet.");
                    // TODO: Pass to Parser.

                    if (dataType == 26 || moreData) {
                        moreData = true;
                        String output = new String(data, 0, result);
                        System.out.println(output);
                        System.out.println("End of Packet\n");
                        char end = output.toCharArray()[output.length() - 1];
                        System.out.println("End of packet: '" + end + "'\n");
                        if (data[result - 1] == 0x00) {
                            moreData = false;
                        }

                        //if (output.charAt(output.length() - 1) == null))
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Arrays.fill(data, (byte)0);
        }
        disconnect();
    }

    public void connect() {
        try {
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
            System.out.println("Socket disconnected.");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeHost() {

    }
}