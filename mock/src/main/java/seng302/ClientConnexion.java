package seng302;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Receives all packets FROM each Client - this includes the initial RRM, and the KeyStrokes.
 */
public class ClientConnexion extends Thread {
    private Server2 server;
    private Socket socket;

    public ClientConnexion(Server2 server, Socket socket) {
        this.server = server;
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            while (true) {
                final int HEADER_LEN = 15;
                byte[] data = new byte[4000];
                if (din.available() > HEADER_LEN) {
                    din.read(data);
                    boolean validPacket = validatePacket(data);
                    if (validPacket) {
                        server.addPacketToQueue(data);
                    }
                }
            }
        } catch (EOFException ie) {
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            server.removeConnection(socket);
        }
    }

    private boolean validatePacket(byte[] data) {
        final int SYNC_BYTE_1 = 0;
        final int SYNC_BYTE_2 = 1;
        return data[SYNC_BYTE_1] == (byte) 0x47 && data[SYNC_BYTE_2] == (byte) 0x83;
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
}