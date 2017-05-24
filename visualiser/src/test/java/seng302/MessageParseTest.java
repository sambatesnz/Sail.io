package seng302;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.Messages.RaceStatusMessage;

public class MessageParseTest {
    RaceStatusMessage raceStatus;

    @Before
    public void setup() {
        byte[] raceStatusTestMessage = {
                (byte)0x02,                                         //MessageVersionNumber
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Current Time 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Current Time 6/6
                (byte)0xA0, (byte)0x02, (byte)0x00, (byte)0x00,     //RaceID (672)
                (byte)0x03,                                         //Race Status (3)
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Expected Start Time 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Expected Start Time 6/6
                (byte)0x00, (byte)0x80,                             //Wind Direction (180)
                (byte)0x88, (byte)0x13,                             //Wind Speed (5000)
                (byte)0x03,                                         //Number of Boats in Race (3)
                (byte)0x01,                                         //Race Type (1)

                (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,     //SourceID Boat 1 (101)
                (byte)0x03,                                         //Boat Status Boat 1 (3)
                (byte)0x06,                                         //Leg Number Boat 1 (6)
                (byte)0x04,                                         //Penalties Awarded Boat 1 (4)
                (byte)0x02,                                         //Penalties Served Boat 1 (2)
                (byte)0x38, (byte)0x18, (byte)0x00,                 //Time to next mark Boat 1 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Time to next mark Boat 1 6/6 (6200)
                (byte)0x3E, (byte)0x81, (byte)0x01,                 //Time to Finish Boat 1 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Time to Finish Boat 1 6/6 (98622)

                (byte)0x66, (byte)0x00, (byte)0x00, (byte)0x00,     //SourceID Boat 2 (102)
                (byte)0x03,                                         //Boat Status Boat 2 (3)
                (byte)0x05,                                         //Leg Number Boat 2 (5)
                (byte)0x00,                                         //Penalties Awarded Boat 2 (4)
                (byte)0x00,                                         //Penalties Served Boat 2 (2)
                (byte)0x84, (byte)0x1C, (byte)0x00,                 //Time to next mark Boat 2 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Time to next mark Boat 2 6/6 (7300)
                (byte)0xDB, (byte)0x78, (byte)0x01,                 //Time to Finish Boat 2 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Time to Finish Boat 2 6/6 (96475)

                (byte)0x67, (byte)0x00, (byte)0x00, (byte)0x00,     //SourceID Boat 3 (103)
                (byte)0x03,                                         //Boat Status Boat 3 (3)
                (byte)0x07,                                         //Leg Number Boat 3 (7)
                (byte)0x03,                                         //Penalties Awarded Boat 3 (3)
                (byte)0x00,                                         //Penalties Served Boat 3 (0)
                (byte)0xFC, (byte)0x04, (byte)0x00,                 //Time to next mark Boat 3 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Time to next mark Boat 3 6/6 (1276)
                (byte)0x4E, (byte)0x5E, (byte)0x01,                 //Time to Finish Boat 3 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Time to Finish Boat 3 6/6 (89678)
        };
        raceStatus = new RaceStatusMessage(new Race(), raceStatusTestMessage);
    }

    @Test
    public void messageVersionTest() {
        // TODO Make a new race and boat objects, set variables to those from the above data, and test for equality.
        Assert.assertEquals(true, true);
    }


}
