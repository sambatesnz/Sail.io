package seng302.PacketGeneration.DataGenerator;

import org.junit.Test;
import seng302.DataGenerator;


/**
 * Created by osr13 on 25/07/17.
 */
public class DataGeneratorTest {

    @Test
    public void messageSizeForBoatLocation() throws Exception {
        DataGenerator dataGenerator = new DataGenerator();
        String xmlMessage = dataGenerator.loadFile("Race.xml");
        System.out.println(xmlMessage);
    }
}
