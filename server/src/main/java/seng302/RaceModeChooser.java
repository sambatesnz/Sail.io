package seng302;

import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;

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

    /**
     * Instantiates the race based on the cli params.
     * Mode will always be set, defaults to Race mode.
     * @return The new race
     */
    public IServerData createRace() {
        IServerData race = new RaceManager();
        //unused as these modes haven't been implemented yet
        System.out.println("Server started in " + mode.getRaceMode() + " mode!");
/*        if (mode == RaceMode.PRACTICE) {
            race = new RaceManager();
        } else if (mode == RaceMode.AGAR) {
            race = new RaceManager();
        } */
        return race;
    }
}
