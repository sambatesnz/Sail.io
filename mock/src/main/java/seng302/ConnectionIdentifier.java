package seng302;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ConnectionIdentifier {
    private Hashtable socketStreams;
    private ArrayList<Integer> connections;

    public ConnectionIdentifier() {
        this.socketStreams = new Hashtable();
        connections  = new ArrayList<>();
    }

    public int addSocket(Socket socket){
        int id = socket.getPort();
        socketStreams.put(id, socket);
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

}
