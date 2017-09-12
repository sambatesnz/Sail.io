package seng302.ManualTesting.ServerStability.ServerRestart;

import seng302.DataGeneration.IServerData;
import seng302.Server.Server;

/**
 * Test for server restarted
 */
public class ServerRestartTest {
    public static void main(String[] args) throws Exception {
        IServerData raceManager = new RaceRestartTest();
        new Server(4941, raceManager);
    }
}
