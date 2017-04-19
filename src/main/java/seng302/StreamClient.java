package seng302;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import io.socket.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by tjg73 on 18/04/17.
 */
public class StreamClient {

    private Socket socket;

    public StreamClient(){
        try {
            socket = IO.socket("http://132.181.16.12:4941/");
            setUp();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void setUp(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("message","hello from java");
            }
        });

        socket.on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                byte[] bytearray = (byte[])args[0]; //received bytes

                for  (byte b : bytearray) {
                    System.out.println("byte"+b);
                }
            }

        });

       socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {}
        });

    }
}
