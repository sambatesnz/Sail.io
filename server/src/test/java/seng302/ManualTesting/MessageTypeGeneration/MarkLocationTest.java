package seng302.ManualTesting.MessageTypeGeneration;

import seng302.DataGeneration.IServerData;
import seng302.ManualTesting.MessageTypeGeneration.DataGenerators.MarkLocationDataGenerator;
import seng302.Server.Server;

public class MarkLocationTest {
    public static void main(String[] args) throws Exception {
        IServerData raceData = new MarkLocationDataGenerator();
        new Server(4941, raceData);
    }
}
