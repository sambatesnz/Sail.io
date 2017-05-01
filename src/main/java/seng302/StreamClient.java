package seng302;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class StreamClient {
    private Socket socket = null;
    ArrayList<String> buffer = new ArrayList<>();
    ArrayList<byte[]> byteBuffer = new ArrayList<>();

    String ip;
    int port;

    public StreamClient(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        socketConnect(ip, port);
    }

    // make the connection with the socket
    public void socketConnect(String ip, int port) throws UnknownHostException, IOException {
        System.out.println("[Connecting to socket...]");
        this.socket = new Socket(ip, port);
    }

    // writes and receives the full message int the socket (String)
    public String echo(String message) {
        try {
            // out & in
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

            // writes str in the socket and read
            out.println(message);
            String returnStr = in.readLine();
            //echo(returnStr);
            return returnStr;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void listen() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
        while (true){
            if (in.ready()) {
                String out = in.readLine();

                buffer.add(out);
                popFromBuffer();
                if (out.charAt(4)   == 9) {
                    socket.close();
                    break;
                }
            }
        }
        popFromBuffer();

    }

    private void popFromBuffer() {
        if (buffer.size() > 2) {
            System.out.println("Buffer popped value: " + buffer.get(0) + ".");
            buffer.remove(0);
        } else if (socket.isClosed()) {
            while (buffer.size() > 0) {
                System.out.println("Buffer popped value: " + buffer.get(0) + ".");
                buffer.remove(0);
            }
        }
    }

    private void popFromByteBuffer() throws IOException {
        if (byteBuffer.size() > 4) {

            byteBuffer.remove(0);
        } else if (socket.isClosed() && byteBuffer.size() > 0) {
            while (byteBuffer.size() > 0) {
                float f = ByteBuffer.wrap(byteBuffer.get(0)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                System.out.println("Buffer popped value: " + f + ".");
                byteBuffer.remove(0);
            }
            if (byteBuffer.size() == 0) {
                socket.close();
            }
        }
    }

    // get the socket instance
    private Socket getSocket() {
        return socket;
    }
}


//package seng302;
//
//import io.socket.client.IO;
//import io.socket.emitter.Emitter;
//import io.socket.client.Socket;
//
//import java.net.URISyntaxException;
//
///**
// * Created by tjg73 on 18/04/17.
// */
//public class StreamClient {
//
//    private Socket socket;
//
//    public StreamClient(){
//        try {
//            socket = IO.socket("http://132.181.16.12:4941/");
//            setUp();
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setUp(){
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                socket.emit("message","hello from java");
//            }
//        });
//
//        socket.on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                byte[] bytearray = (byte[])args[0]; //received bytes
//
//                for  (byte b : bytearray) {
//                    System.out.println("byte"+b);
//                }
//            }
//
//        });
//
//       socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {}
//        });
//
//    }
//}
