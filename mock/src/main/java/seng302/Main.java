package seng302;

import seng302.Server.GeneratedData;
import seng302.Server.TempServer.Server;

import java.io.IOException;

/**
 * Runs the non javafx program
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        ServerStream server = new ServerStream(9090);
//        GeneratedData genData = new GeneratedData();
//        genData.runServerTimers();

        GeneratedData data = new GeneratedData();
        Server server = new Server(9090);

    }
}