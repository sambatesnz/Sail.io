package seng302.ManualTesting.MessageTypeGeneration;

import seng302.DataGeneration.IServerData;
import seng302.ManualTesting.MessageTypeGeneration.DataGenerators.RegattaDataGenerator;
import seng302.Server2;

import java.io.IOException;

/**
 * Created by sha162 on 24/07/17.
 */
public class RegattaXMLTest {

    public static void main(String[] args) throws Exception {
        IServerData regattaData = new RegattaDataGenerator();
        new Server2(4941, regattaData);

    }
}
