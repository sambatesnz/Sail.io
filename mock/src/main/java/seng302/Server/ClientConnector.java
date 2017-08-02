package seng302.Server;

import seng302.DataGeneration.IServerData;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Abstracts away Connections from running the race.
 */
public class ClientConnector extends Thread {
    private Socket socket;
    private IServerData mockData;

    public ClientConnector(Socket socket, IServerData mockData) throws IOException {
        this.socket = socket;
        this.mockData = mockData;
        System.out.println("New Connection From client");
    }

    public void run(){
        System.out.println("Connection established");
        Delegator delegator = new Delegator(mockData.getRace());
        this.mockData.beginGeneratingData();
        InputStream in = null;
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!this.mockData.finished() && !socket.isClosed()){
            if(this.mockData.ready()){
                byte[] data = this.mockData.getData();
                try {
                    send(data);
                } catch (IOException e) {
                    try {
                        close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (!socket.isClosed()){
                try {
                    byte[] data = new byte[4000];
                    final int HEADER_LEN = 15;
                    if (in.available() > HEADER_LEN){
                        in.read(data);
                        int syncByte1 = byteArrayToInt(data, 0, 1);
                        int syncByte2 = byteArrayToInt(data, 1,1);
                        int messageType = byteArrayToInt(data, 2,1);
                        int messageLen = byteArrayToInt(data, 13,2);

                        byte[] body = new byte[messageLen];
                        System.arraycopy(data,15, body,0, messageLen);

                        int messageCommand = byteArrayToInt(body, 0, 1);
                        delegator.processCommand(messageCommand);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.mockData.finishGeneratingData();
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a section from an array of bytes into an integer.
     * @param bytes The array to convert bytes from
     * @param pos The starting index of the bytes desired to be converted
     * @param len The number of bytes to be converted (from the given index)
     * @return An integer, converted from the given bytes
     */
    public static int byteArrayToInt(byte[] bytes, int pos, int len){
        byte[] intByte = new byte[4];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private void send(byte[] packets) throws IOException {
        socket.getOutputStream().write(packets);
        socket.getOutputStream().flush();  // Need to flush output stream to send packets
    }

    private void close() throws IOException {
        System.out.println("Connection closed");
        socket.close();
    }
}
