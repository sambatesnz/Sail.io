package seng302;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * For testing command line paramters
 */
public class CommandLineParserTest {

    String[] args;
    RaceMode expectedMode;

    @Before
    public void setup(){
        args = new String[1];
    }

    private void setArg(String arg){
        args[0] = arg;
    }

    private RaceMode getRaceMode(){
        CommandLineParser parser = new CommandLineParser(args);
        RaceMode mode = parser.getRaceMode();
        return mode;
    }

    @Test
    public void raceMode() throws Exception {
        expectedMode = RaceMode.RACE;
        setArg("-mode=race");
        assertEquals(expectedMode, getRaceMode());
    }

    @Test
    public void raceModeWithEmptySpace() throws Exception {
        expectedMode = RaceMode.RACE;
        setArg("-mode=race    ");
        assertEquals(expectedMode, getRaceMode());
    }

    @Test
    public void raceModeWithCapitals() throws Exception {
        expectedMode = RaceMode.AGAR;
        setArg("-mode=Play");
        assertEquals(expectedMode, getRaceMode());
    }

    @Test
    public void agarMode() throws Exception {
        expectedMode = RaceMode.AGAR;
        setArg("-mode=play");
        assertEquals(expectedMode, getRaceMode());
    }

    @Test
    public void practiceMode() throws Exception {
        expectedMode = RaceMode.PRACTICE;
        setArg("-mode=practice");
        assertEquals(expectedMode, getRaceMode());
    }

    @Test
    public void noModeSelected() throws Exception {
        expectedMode = RaceMode.RACE;
        setArg("");
        assertEquals(expectedMode, getRaceMode());
    }

    @Test
    public void noParameters() throws Exception {
        String[] noArgs = new String[0];
        args = noArgs;
        expectedMode = RaceMode.RACE;
        assertEquals(expectedMode, getRaceMode());
    }
}