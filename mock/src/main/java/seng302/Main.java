package seng302;

import seng302.Server.GeneratedData;
import seng302.Server.StreamServer;

import java.io.IOException;

/**
 * Runs the non javafx program
 */
public class Main {

    public static void main(String[] args) throws IOException {
        StreamServer server = new StreamServer(9090);
        GeneratedData genData = new GeneratedData();
        genData.runServerTimers();
        server.start(genData);
    }
}