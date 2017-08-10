package seng302;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

public class ConnectionIdentifier {
    private Hashtable socketStreams;
    private static int DEFAULT_ID = 1;

    private int id;

    public ConnectionIdentifier() {
        this.socketStreams = new Hashtable();
        id = DEFAULT_ID;
    }

    public int addSocket(Socket socket){
        int id = this.id;
        socketStreams.put(id, socket);
        this.id ++;
        return id;
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
