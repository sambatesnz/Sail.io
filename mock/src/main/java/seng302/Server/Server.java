package seng302.Server;

import seng302.DataGeneration.MockRace;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Server to n client implementation from this website:
 * http://cs.lmu.edu/~ray/notes/javanetexamples/
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
            MockRace genData = new MockRace();
            genData.runServerTimers();
            InputStream in = null;
            try {
                in = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                try {
                    byte[] data = new byte[4000];
                    final int HEADER_LEN = 15;
                    if (in.available() > HEADER_LEN){
                        in.read(data);
                        int syncByte1 = byteArrayToInt(data, 0, 1);
                        int syncByte2 = byteArrayToInt(data, 1,1);
                        int messageType = byteArrayToInt(data, 2,1);
                        int messageLen = byteArrayToInt(data, 13,2);

                        byte[] body = new byte[messageLen];
                        System.arraycopy(data,15, body,0, messageLen);

                        int messageCommand = byteArrayToInt(body, 0, 1);

                    }
//                    else {                                              //
//                        System.out.println("No available data");        //Testing
//                    }                                                   //
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            genData.cancelServerTimers();
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Converts a section from an array of bytes into an integer.
         * @param bytes The array to convert bytes from
         * @param pos The starting index of the bytes desired to be converted
         * @param len The number of bytes to be converted (from the given index)
         * @return An integer, converted from the given bytes
         */
        public static int byteArrayToInt(byte[] bytes, int pos, int len){
            byte[] intByte = new byte[4];
            System.arraycopy(bytes, pos, intByte, 0, len);
            return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
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
