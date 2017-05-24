package seng302.Server.TempServer;

import seng302.Server.GeneratedData;
import seng302.Server.IServerData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sha162 on 24/05/17.
 */
public class Server {

    public Server(int port) throws IOException {
        ServerSocket listener = new ServerSocket(port);
        try {
            while(true) {
                new Generator(listener.accept()).start();

            }
        } finally {
            listener.close();
        }
    }


    private static class Generator extends Thread {
        private Socket socket;
        public Generator(Socket socket) throws IOException {
            this.socket = socket;
            System.out.println("New Connection From client");
        }

        public void run(){
            System.out.println("Connection established");
            GeneratedData genData = new GeneratedData();
            genData.runServerTimers();
            while (!genData.finished() && !socket.isClosed()){
                System.out.flush();  // Need to flush output stream to send packets
                if(genData.ready()){
                    byte[] data = genData.getData();
                    try {
                        send(data);
                    } catch (IOException e) {
                        try {
                            close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

            genData.cancelServerTimers();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void send(byte[] packets) throws IOException {
            socket.getOutputStream().write(packets);
        }

        private void close() throws IOException {
            System.out.println("Connection closed");
            socket.close();
        }
    }
}
