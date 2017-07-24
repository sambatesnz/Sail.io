package seng302.ManualTesting.MessageTypeGeneration;

import seng302.DataGeneration.IServerData;
import seng302.ManualTesting.MessageTypeGeneration.DataGenerators.RaceXmlDataGenerator;
import seng302.Server.Server;

import java.io.IOException;

/**
 * Created by sha162 on 24/07/17.
 */
public class RaceXMLTest {

    public static void main(String[] args) throws IOException {
        IServerData raceData = new RaceXmlDataGenerator();
        new Server(4941, raceData);

    }
}
