package seng302;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ConnectionIdentifier {
    private Hashtable socketStreams;

    public ArrayList<Integer> getConnections() {
        return connections;
    }

    private ArrayList<Integer> connections;
    private Hashtable outputStreams;


    public ConnectionIdentifier() {
        this.socketStreams = new Hashtable();
        this.outputStreams = new Hashtable();
        connections  = new ArrayList<>();
    }

    public int addSocket(Socket socket) throws IOException {
        int id = socket.getPort();
        DataOutputStream dout = null;
        dout = new DataOutputStream(socket.getOutputStream());
        socketStreams.put(id, socket);
        outputStreams.put(socket, dout);
        connections.add(id);
        return id;
    }

    public boolean hasConnections(){
        return connections.size() > 0;
    }

    private Enumeration getStreams() {
        return socketStreams.keys();
    }


    public Socket getSocket(int id) throws Exception {
        Socket socket = null;
        synchronized (socketStreams) {
            for (Enumeration e = getStreams(); e.hasMoreElements(); ) {
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

    private Enumeration getOutputStreams() {
        return outputStreams.elements();
    }


    public int connectionAmount() {
        return connections.size();
    }

    public void sendToAll(byte[] bytes) {
        synchronized (outputStreams) {

            for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) {

                DataOutputStream dout = (DataOutputStream) e.nextElement();
                try {
                    boolean hasPackets = bytes.length > 0;
                    if (hasPackets) {
                        dout.write(bytes);
                    }
                } catch (IOException ie) {
                    System.out.println(ie);
                }
            }
        }
    }
}
