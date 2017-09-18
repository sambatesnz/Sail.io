package seng302.Server;

import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.Server.Server;

import java.io.*;
import java.net.*;

/**
 * Receives all packets FROM each Client - this includes the initial RRM, and the KeyStrokes.
 */
public class ClientConnectoin extends Thread {
    private Server server;
    private Socket socket;
    private int id;

    public ClientConnectoin(Server server, Socket socket, int id) {
        this.server = server;
        this.socket = socket;
        this.id = id;
        start();
    }

    public void run() {
        try {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            while (true) {
                final int HEADER_LEN = 15;
                byte[] data = new byte[4000];
                din.read(data);
                boolean validPacket = validatePacket(data);
                if (validPacket) {
                    byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(data, id);
                    server.addPacketToQueue(wrappedMessage);
                }
            }
        } catch (IOException e) {
            server.removeConnection(socket);
        }
    }

    private boolean validatePacket(byte[] data) {
        final int SYNC_BYTE_1 = 0;
        final int SYNC_BYTE_2 = 1;
        return data[SYNC_BYTE_1] == (byte) 0x47 && data[SYNC_BYTE_2] == (byte) 0x83;
    }
}