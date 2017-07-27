package seng302;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.Client.Messages.Message;
import seng302.Race.Race;
import seng302.RaceObjects.Boat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationMessageTest {
    Message message;
    Race race;

    @Before
    public void setup() {
        byte[] locationTestMessage = {
                (byte)0x47,                                         //Sync Byte 1
                (byte)0x83,                                         //Sync Byte 2
                (byte)0x25,                                         //Message type (37)
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Timestamp 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Timestamp 6/6
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,     //Source ID
                (byte)0x38, (byte)0x00,                             //Message Length (56)

                (byte)0x01,                                         //MessageVersionNumber
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Current Time 3/6
                (byte)0x00, (byte)0x00, (byte)0x00,                 //Current Time 6/6
                (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,     //SourceID of Boat (101)

                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,     //Sequence Number

                (byte)0x00,                                         //Device Type (1) [Racing Yacht]
                (byte)0x42, (byte)0x46, (byte)0xF6, (byte)0x16,     //Latitude (32.290326)
                (byte)0xF4, (byte)0x24, (byte)0xE2, (byte)0xD1,     //Longitude (-64.851502)

                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,     //Altitude

                (byte)0xB6, (byte)0xA0,                             //Heading (226)

                (byte)0x00, (byte)0x00,                             //Pitch
                (byte)0x00, (byte)0x00,                             //Roll
                (byte)0x00, (byte)0x00,                             //Boat Speed
                (byte)0x00, (byte)0x00,                             //Course Over Ground

                (byte)0x62, (byte)0x50,                             //Speed Over Ground (40)

                (byte)0x00, (byte)0x00,                             //Apparent Wind Speed
                (byte)0x00, (byte)0x00,                             //Apparent Wind Angle
                (byte)0x00, (byte)0x00,                             //True Wind Speed
                (byte)0x00, (byte)0x00,                             //True Wind Direction
                (byte)0x00, (byte)0x00,                             //True Wind Angle
                (byte)0x00, (byte)0x00,                             //Current Drift
                (byte)0x00, (byte)0x00,                             //Current Set
                (byte)0x00, (byte)0x00,                             //Rudder Angle

                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00      //CRC
        };

        race = new Race();
        List participants = new ArrayList<Integer>();
        participants.add(101);
        race.setParticipants(participants);
        HashMap<Integer, Boat> testBoatMap = new HashMap<Integer, Boat>();
        testBoatMap.put(101, new Boat("Test_1", "Test_1", 101, "Country_1"));
        race.setBoats(testBoatMap);
        message = new Message(locationTestMessage, race);
        try {
            message.parseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boatLatitudeTest() {
        Assert.assertEquals(32.290326, race.getBoatsMap().get(101).getLatitude(), 0.00001);
    }

    @Test
    public void boatLongitudeTest() {
        Assert.assertEquals(-64.851502, race.getBoatsMap().get(101).getLongitude(), 0.00001);
    }

    @Test
    public void boatSpeedTest() {
        Assert.assertEquals(20578, race.getBoatsMap().get(101).getSpeed(), 0.1);
    }

    @Test
    public void boatHeadingTest() {
        Assert.assertEquals(226, race.getBoatsMap().get(101).getHeading(), 0.1);
    }
}
