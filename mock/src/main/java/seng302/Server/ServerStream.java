package seng302.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tjg73 on 1/05/17.
 * Server seng302.Client based off implementation from this website:
 * http://cs.lmu.edu/~ray/notes/javanetexamples/
 */
public class ServerStream {

    private ServerSocket listener;
    private Socket socket;

    public ServerStream(int port) throws IOException {
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
            System.out.flush();  // Need to flush output stream to send packets
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
