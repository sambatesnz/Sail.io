package seng302;

/**
 * For parsing command line paramaters
 */
public class CommandLineParser {

    private RaceMode raceMode;
    private static String MODE = "-mode=";


    public CommandLineParser(String[] args) {
        raceMode = decideRaceMode(args);
    }

    private RaceMode decideRaceMode(String[] args) {
        RaceMode racemode = RaceMode.RACE;
        if (args.length < 1) {
            raceMode = RaceMode.RACE;
        } else {
            String mode = getArg(args, MODE);
            racemode = RaceMode.getRaceMode(mode);
        }
        return racemode;
    }


    private String getArg(String[] args, String option) {
        String value = "";
        for (String arg : args) {
            if (arg.contains(option)) {
                value = arg.replaceFirst(".*=", "").trim();
            }
        }
        return value;
    }


    public RaceMode getRaceMode() {
        return raceMode;
    }
}
