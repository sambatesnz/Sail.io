package seng302;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ServerThread extends Thread
{
    private Server2 server;
    private Socket socket;
    public ServerThread( Server2 server, Socket socket ) {
        this.server = server;
        this.socket = socket;
        start();
    }
    public void run() {
        try {
            DataInputStream din = new DataInputStream( socket.getInputStream() );
            while (true) {

                byte[] data = new byte[4000];
                if (din.available() > 15) {
                    din.read(data);
                    System.out.println(Arrays.toString(data));
                }
                server.sendToAll("poos");
//                String message = din.readUTF();
//                System.out.println( "Sending "+message );
//                server.sendToAll( "HEY BRO" );
            }
        } catch( EOFException ie ) {
        } catch( IOException ie ) {
            ie.printStackTrace();
        } finally {
            server.removeConnection( socket );
        }
    }
}