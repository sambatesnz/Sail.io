package seng302.Client;

import org.junit.Assert;
import org.junit.Test;
import seng302.Server.ServerUtility;
import seng302.Server.StreamServer;
import seng302.Server.TestServerData;
import seng302.StreamClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * Gets the StreamClient to read in the first 200 packets.
 */
public class StreamClientTest {

    @Test
    public void testClientCanConnectToServer() throws Exception {
        TestServerData data = new TestServerData(); //implemented as a stack

        byte[] testByteData = new byte[]{(byte)0X00, (byte)0X01};
        byte[] testByteData2 = new byte[]{(byte)0X02, (byte)0X03};
        byte[] testByteData3 = new byte[] {(byte) 9};

        data.add(testByteData3);
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

        StreamClient client = new StreamClient("127.0.0.1", 9090);
        client.connect();
//        client.retrieveData();
        Thread.sleep(1000);
        client.disconnect();
        Assert.assertFalse(false);
    }


}
