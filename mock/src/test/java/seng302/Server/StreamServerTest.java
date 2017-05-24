package seng302.Server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertArrayEquals;


public class StreamServerTest {

//    /**
//     * Sends two byte arrays over the server,
//     * using the StreamServerTest implementation
//     * @throws Exception
//     */
//    @Test
//    public void testServerWithByteArray() throws Exception {
//        TestServerData data = new TestServerData(); //implemented as a stack
//
//        byte[] testByteData = new byte[]{(byte)0X00, (byte)0X01};
//        byte[] testByteData2 = new byte[]{(byte)0X02, (byte)0X03};
//
//        data.add(testByteData2);
//        data.add(testByteData);
//
//        ServerStream server = new ServerStream(9091);
//
//        Thread serverThread = new Thread(() -> {
//            try {
//                server.start(data);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });
//        serverThread.start();
//
//        String serverAddress = ServerUtility.getLocalIpAddress();
//        Socket s = new Socket(serverAddress, 9091);
//        BufferedReader input =
//                new BufferedReader(new InputStreamReader(s.getInputStream()));
//
//        String answer = input.readLine();
//        byte[] actual = answer.getBytes();
//
//        byte[] expected = new byte[] {0,1,2,3};
//        assertArrayEquals(actual, expected);
//    }
//
//
//    @Test
//    public void serverWithGeneratedMessage() throws Exception {
////
////        ServerStream server = new ServerStream(9090);
////        Thread serverThread = new Thread(() -> {
////            try {
////                server.start(data);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////        });
////        serverThread.start();
////    }
//
////filename is filepath string
////        //file:/home/cosc/student/sha162/Documents/team-4/target/classes/RaceView.fxml
//
//
//        //TODO
//        //Send the race .xml file to stream server to send over sockets
//        //Send the Regatta file to stream server to send over sockets
//        //Send the boats file to stream server to send over sockets
//
//        //Generate a fake race internally
//        //Send boats
//
////        DataGenerator d = new DataGenerator("Race.xml");
//
//       BoatLocationMessageDeprecated m = new BoatLocationMessageDeprecated();
//
//    }

}