package seng302.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tjg73 on 1/05/17.
 * Server Client based off implementation from this website:
 * http://cs.lmu.edu/~ray/notes/javanetexamples/
 */
public class StreamServer {

    private ServerSocket listener;
    private Socket socket;

    public StreamServer(int port) throws IOException {
        this.listener = new ServerSocket(port);
    }

    private void close() throws IOException {
        socket.close();
        listener.close();
    }

    /**
     * Starts the server at the specified port and
     * sends through data given by the data interface
     * @param dataInterface
     * @throws IOException
     */
    public void start(IServerData dataInterface) throws IOException {
        socket = listener.accept();

        while (!dataInterface.finished()){
//            System.out.print("We must print this for the program to work (sorry)");
            System.out.flush();
            if(dataInterface.ready()){
                byte[] data = dataInterface.getData();
                send(data);
            }
        }
        close();
    }

    private void send(byte[] packets) throws IOException {
        socket.getOutputStream().write(packets);
    }

}
