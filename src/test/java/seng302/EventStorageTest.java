package seng302;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by osr13 on 9/03/17.
 */
public class EventStorageTest {

    @Test
    public void testEventStorageTime() {
        EventStorage kevEventStorage = new EventStorage("kevBoat", "kevEvent", 100, 100);
        assertTrue(kevEventStorage.getEventTime() == 100);
    }
}
