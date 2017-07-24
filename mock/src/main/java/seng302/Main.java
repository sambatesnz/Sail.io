package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.MockRace;
import seng302.Server.Server;

import java.io.IOException;

/**
 * Runs the Mock server
 */
public class Main {

    public static void main(String[] args) throws IOException {
        IServerData mockData = new MockRace(); //Default race
        new Server(4941, mockData);
    }
}   