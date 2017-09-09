package seng302;

import seng302.DataGeneration.IServerData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Determines what implementation of IServerData is wanter
 * Based on the cli options
 */
public class RaceModeChooser {

    private RaceMode mode;

    /**
     * Constructor for the race mode chooser
     * @param args String of arguments that need parsing
     */
    public RaceModeChooser(String[] args) {
        CommandLineParser cliParser = new CommandLineParser(args);
        mode = cliParser.getRaceMode();
    }

    public IServerData getRaceMode() throws Exception {
        throw new Exception("not implemented yet");
    }


}
