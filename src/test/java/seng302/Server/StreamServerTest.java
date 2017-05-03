package seng302.Server;

import org.junit.Test;
import seng302.StreamClient;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

import static org.junit.Assert.*;


public class StreamServerTest {

    /**
     * Sends two byte arrays over the server,
     * using the StreamServerTest implementation
     * @throws Exception
     */
    @Test
    public void testServerWithByteArray() throws Exception {
        TestServerData data = new TestServerData(); //implemented as a stack

        byte[] testByteData = new byte[]{(byte)0X00, (byte)0X01};
        byte[] testByteData2 = new byte[]{(byte)0X02, (byte)0X03};

        data.add(testByteData2);
        data.add(testByteData);

        StreamServer server = new StreamServer(9090);

        Thread serverThread = new Thread(() -> {
            try {
                server.start(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        serverThread.start();

        String serverAddress = ServerUtility.getLocalIpAddress();
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));

        String answer = input.readLine();
        byte[] actual = answer.getBytes();

        byte[] expected = new byte[] {0,1,2,3};
        assertArrayEquals(actual, expected);
    }
}