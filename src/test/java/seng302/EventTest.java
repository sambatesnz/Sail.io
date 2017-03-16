package seng302;

import org.junit.Test;
import seng302.utility.Event;

import static org.junit.Assert.assertTrue;

/**
 * Created by osr13 on 9/03/17.
 */
public class EventTest {

    @Test
    public void testEventName() {
        Event kevinsEvent = new Event("KevEvent", 1, 2,3);
        assertTrue(kevinsEvent.getEventName().equals("KevEvent"));
    }

}
