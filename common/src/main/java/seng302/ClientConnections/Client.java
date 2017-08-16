package seng302.ClientConnections;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

/**
 * Abstract class containing base implementation of a client who wants
 * to connect to an instance of our server
 */
public abstract class Client {

    protected String host;
    protected int port;
    protected Socket clientSocket;

    protected InputStream streamInput;
    protected OutputStream streamOutput;

    public Client(String ipAddress, int port){
        this.port = port;

        try {
            host = new URL(ipAddress).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public final void connect() throws IOException {
        try {
            clientSocket = new Socket(host, port);
            streamInput = new BufferedInputStream(clientSocket.getInputStream());
            streamOutput = new BufferedOutputStream(clientSocket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        connectionEvent();
        processStreams();
    }

    protected abstract void connectionEvent() throws IOException;


    protected abstract void processStreams();

    protected void disconnect(){
        try {
            System.out.println("Socket disconnected.");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getStreamInput() {
        return streamInput;
    }

    public OutputStream getStreamOutput() {
        return streamOutput;
    }



}
