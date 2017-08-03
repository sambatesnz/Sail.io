package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.MockRace;

import java.io.*;
import java.net.*;
import java.util.*;
public class Server2
{
    private ServerSocket ss;

    private Hashtable outputStreams = new Hashtable();

    private IServerData mockRace;

    public Server2( int port ) throws IOException {
        this.mockRace = new MockRace();
        this.mockRace.beginGeneratingData();
        listen( port );
    }

    private void listen( int port ) throws IOException {
        ss = new ServerSocket( port );
        System.out.println( "Listening on "+ss );
        while (true) {
            Socket s = ss.accept();
            System.out.println( "Connection from "+s );
            DataOutputStream dout = new DataOutputStream( s.getOutputStream() );
            outputStreams.put( s, dout );
            new ServerThread( this, s );
        }
    }
    Enumeration getOutputStreams() {
        return outputStreams.elements();
    }

    void sendToAll( String message ) {
        byte[] bytes = this.mockRace.getData();
        synchronized( outputStreams ) {
            for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) {

                DataOutputStream dout = (DataOutputStream)e.nextElement();
                try {
                    dout.write(bytes);
//                    dout.writeUTF( message );
                } catch( IOException ie ) { System.out.println( ie ); }
            }
        }
    }
    void removeConnection( Socket s ) {
        synchronized( outputStreams ) {
            System.out.println( "Removing connection to "+s );
            outputStreams.remove( s );
            try {
                s.close();
            } catch( IOException ie ) {
                System.out.println( "Error closing "+s );
                ie.printStackTrace();
            }
        }
    }

    static public void main( String args[] ) throws Exception {
        int port = Integer.parseInt( "4941" );
        new Server2( port );
    }
}