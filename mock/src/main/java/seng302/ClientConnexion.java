package seng302;

import seng302.PacketGeneration.PacketGenerationUtils;
import seng302.PacketGeneration.PacketUtils;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.PacketParsing.PacketParserUtils;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Receives all packets FROM each Client - this includes the initial RRM, and the KeyStrokes.
 */
public class ClientConnexion extends Thread {
    private Server2 server;
    private Socket socket;
    private int id;

    public ClientConnexion(Server2 server, Socket socket, int id) {
        System.out.println(socket.getPort());
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
                if (din.available() > HEADER_LEN) {
                    din.read(data);
                    boolean validPacket = validatePacket(data);

                    if (validPacket) {
                        System.out.println("Client " + id + " has sent a packet");

                        byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(data, id);
                        server.addPacketToQueue(wrappedMessage);
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
}