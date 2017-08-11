package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;

/**
 * Runs the Mock server
 */
public class Main {

    public static void main(String[] args) throws Exception {
        IServerData mockData = new RaceManager(); //Default race
        new Server2(4941, mockData);
    }
}   