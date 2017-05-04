package seng302;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by osr13 on 1/05/17.
 */
public class dummyServer {

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        Socket socket = listener.accept();
        int count = 0;
        try {
            while (count < 10) {
                try {
                    PrintWriter out =
                            new PrintWriter(socket.getOutputStream(), true);
                    out.println(count);
                    count ++;
                } catch (IOException ioe) {
                    System.out.println("Problem.");
                }
            }
        }
        finally {
            listener.close();
        }
    }
}
