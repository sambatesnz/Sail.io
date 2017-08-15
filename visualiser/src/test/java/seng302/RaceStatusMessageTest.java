package seng302;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.Client.Messages.Message;
import seng302.PacketGeneration.RaceStatus;
import seng302.RaceObjects.Race;
import seng302.RaceObjects.Boat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaceStatusMessageTest {
    Message message;
    Race race;

    @Before
    public void setup() {
        byte[] raceStatusTestMessage = {
                (byte)0x47,                                         //Sync Byte 1
                (byte)0x83,                                         //Sync Byte 2
                (byte)0x0C,                                         //Message type (12)
                (byte)0x00,(byte)0x00,(byte)0x00,                   //Timestamp 3/6
                (byte)0x00,(byte)0x00,(byte)0x00,                   //Timestamp 6/6
                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,        //Source ID
                (byte)0x54,(byte)0x00,                              //Message Length (84 [24 + 20 * 4])

                (byte)0x02,                                         //MessageVersionNumber
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Current Time 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Current Time 6/6
                (byte)0xA0, (byte)0x02, (byte)0x00, (byte)0x00,     //RaceID (672)
                (byte)0x03,                                         //Race Status (3)
                (byte)0xAD, (byte)0xDF, (byte)0x00,                 //Expected Start Time 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Expected Start Time 6/6 (57261)
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

                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00         //CRC
        };

        race = new Race();
        List<Integer> participants = new ArrayList<>();
        participants.add(101);
        participants.add(102);
        participants.add(103);
        race.setParticipants(participants);
        HashMap<Integer, Boat> testBoatMap = new HashMap<>();
        testBoatMap.put(101, new Boat("Test_1", "Test_1", 101, "Country_1"));
        testBoatMap.put(102, new Boat("Test_2", "Test_2", 102, "Country_2"));
        testBoatMap.put(103, new Boat("Test_3", "Test_3", 103, "Country_3"));
        race.setBoats(testBoatMap);
        message = new Message(raceStatusTestMessage, race);
        try {
            message.parseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void raceWindHeadingTest() {
        System.out.println(race.getWindHeading());
        Assert.assertEquals(180.0, race.getWindHeading(), 0.1);
    }

    @Test
    public void raceWindSpeedTest() {
        Assert.assertEquals(5000.0, race.getWindSpeed(), 0.1);
    }

    @Test
    public void raceStartTimeTest() {
        Assert.assertEquals(57261.0, race.getExpectedStartTime(), 0.1);
    }

    @Test
    public void raceStatusTest() {
        Assert.assertEquals(RaceStatus.STARTED, race.getRaceStatus());
    }

    @Test
    public void boatLegIndexTest() {
        Assert.assertEquals(6, race.getBoatsMap().get(101).getCurrentLegIndex());
        Assert.assertEquals(5, race.getBoatsMap().get(102).getCurrentLegIndex());
        Assert.assertEquals(7, race.getBoatsMap().get(103).getCurrentLegIndex());
    }

    @Test
    public void boatTimeToNextMarkTest() {
        Assert.assertEquals(6200, race.getBoatsMap().get(101).getTimeToNextMark());
        Assert.assertEquals(7300, race.getBoatsMap().get(102).getTimeToNextMark());
        Assert.assertEquals(1276, race.getBoatsMap().get(103).getTimeToNextMark());
    }

    @Test
    public void boatTimeToFinishTest() {
        Assert.assertEquals(98622, race.getBoatsMap().get(101).getTimeToFinish());
        Assert.assertEquals(96475, race.getBoatsMap().get(102).getTimeToFinish());
        Assert.assertEquals(89678, race.getBoatsMap().get(103).getTimeToFinish());
    }


}
