package seng302;

import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class ConnectionIdentifier {
    private Hashtable socketStreams;
    private ArrayList<Integer> connections;


    public ConnectionIdentifier() {
        this.socketStreams = new Hashtable();
        connections  = new ArrayList<>();
    }


    public ArrayList<Integer> getConnections() {
        return connections;
    }


    public int addSocket(Socket socket) throws IOException {
        int id = socket.getPort();
        socketStreams.put(id, socket);
        connections.add(id);
        return id;
    }

    public boolean hasConnections(){
        return connections.size() > 0;
    }

    private Enumeration getIds() {
        return socketStreams.keys();
    }


    public Socket getSocket(int id) throws Exception {
        Socket socket = null;
        synchronized (socketStreams) {
            for (Enumeration e = getIds(); e.hasMoreElements(); ) {
                int socketId = (int) e.nextElement();
                if (socketId == id) {
                    socket = (Socket) socketStreams.get(id);
                }
            }
        }
        if (socket == null){
           throw new NullPointerException("Couldnt find socket");
        }
        return socket;
    }


    public int connectionAmount() {
        return connections.size();
    }

    public void sendToAll(byte[] bytes) throws IOException {
        synchronized (socketStreams) {
            for (Object value: socketStreams.values()){
                Socket socket = (Socket) value;
                OutputStream stream = socket.getOutputStream();
                boolean hasPackets = bytes.length > 0;
                if (hasPackets) {
                    stream.write(bytes);
                }
            }
        }
    }

    public void sendToOne(byte[] message) throws IOException {
        int clientID = ServerMessageGenerationUtils.unwrapHeader(message);
        byte[] messageToSend = ServerMessageGenerationUtils.unwrapBody(message);

        synchronized (socketStreams) {
            System.out.println(clientID);
            Socket clientSocket = (Socket) socketStreams.get(clientID);
            OutputStream stream = clientSocket.getOutputStream();
            boolean hasPackets = messageToSend.length > 0;
            if (hasPackets) {
                stream.write(messageToSend);
            }
        }
    }

    public void removeConnection(Socket socket) {
        int socketId = socket.getPort();

        synchronized (socketStreams) {
            System.out.println("Removing connection to " + socket);
            socketStreams.remove(socketId);
            try {
                socket.close();
            } catch (IOException ie) {
                System.out.println("Error closing " + socket);
                ie.printStackTrace();
            }
        }
    }
}
