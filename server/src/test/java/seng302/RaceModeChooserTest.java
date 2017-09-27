package seng302;

import org.junit.Test;
import seng302.DataGeneration.IServerData;
import seng302.Modes.RaceManager;

import static org.junit.Assert.assertEquals;

/**
 * Tests the race mode chooser
 */
public class RaceModeChooserTest {


    @Test
    public void defaultMode() throws Exception {
        //Default type of race is the race manager
        String[] args = new String[0];
        RaceModeChooser raceModeChooser = new RaceModeChooser(args);
        IServerData race = raceModeChooser.createRace();
        assertEquals(RaceManager.class,  race.getClass());
    }

}