package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;
import seng302.Server.Server;

import java.util.Arrays;

/**
 * Runs the Mock server
 */
public class Main {

    public static void main(String[] args) throws Exception {
        IServerData mockData = new RaceManager(); //Default race
        new Server(4942, mockData);
    }
}   