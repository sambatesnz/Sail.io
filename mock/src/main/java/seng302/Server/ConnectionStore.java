package seng302.Server;

import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * Client connections are added and removed through
 * The Connection Store.
 * Messages can be broadcasted to all connections or to a singlular connection
 */
public class ConnectionStore {
    private final Hashtable<Integer, Socket> socketStreams;
    private IServerData race;

    public ConnectionStore(IServerData race) {
        this.socketStreams = new Hashtable<>();
        this.race = race;
    }


    /**
     * Adds a socket connection to the tracked connections
     * @param socket the socked you wish to add
     * @return id of the socket that you added (the sockets port number)
     */
    public int addSocket(Socket socket) {
        int id = socket.getPort();
        socketStreams.put(id, socket);
        return id;
    }


    /**
     * Tries to send a stream of bytes to all connected sockets
     * @param bytes array of bytes you wish to send
     */
    public synchronized void sendToAll(byte[] bytes) {
        for (Object value: socketStreams.values()){
            Socket socket = (Socket) value;
            try {
                boolean hasPackets = bytes.length > 0;
                if (hasPackets) {
                    OutputStream stream = socket.getOutputStream();
                    stream.write(bytes);
                }
            } catch (IOException e) {
                System.out.println("removing connections");
                removeConnection(socket);
            }
        }
    }


    /**
     * Sends a message to a particular client
     * @param message A server message (AC35 spec message wrapped with a header which is the clients id)
     * @throws IOException
     */
    public synchronized void sendToOne(byte[] message) throws IOException {
        int clientID = ServerMessageGenerationUtils.unwrapHeader(message);
        byte[] messageToSend = ServerMessageGenerationUtils.unwrapBody(message);

        System.out.println(clientID);
        Socket clientSocket = (Socket) socketStreams.get(clientID);
        OutputStream stream = clientSocket.getOutputStream();
        boolean hasPackets = messageToSend.length > 0;
        if (hasPackets) {
            try {
                stream.write(messageToSend);
            } catch (SocketException e) {
                e.printStackTrace();
                removeConnection(clientSocket);
            }
        }
    }

    /**
     * Removes a connection
     * @param socket connection you wish to remove
     */
    public synchronized void removeConnection(Socket socket) {
        int socketId = socket.getPort();

        System.out.println("Removing connection to " + socket);

        socketStreams.remove(socketId);
        try {
            socket.close();
        } catch (IOException ie) {
            System.out.println("Error closing " + socket);
            ie.printStackTrace();
        }
    }

    public synchronized Socket getSocket(int id) throws Exception {
        Socket socket = socketStreams.get(id);

        if (socket == null){
            throw new NullPointerException("Couldn't find socket with id " + id);
        }

        return socket;
    }


    public int connectionAmount() {
        return socketStreams.size();
    }

    public boolean hasConnections(){
        return socketStreams.size() > 0;
    }

    private Enumeration getIds() {
        return socketStreams.keys();
    }

    public List<Integer> getConnections() {
        return Collections.list(socketStreams.keys());
    }
}
