package seng302;

/**
 * For parsing command line parameters
 * Current accepts 1 parameter in the form of (-mode=.....)
 * Where mode is race, practice or agar
 */
public class CommandLineParser {

    private RaceMode raceMode;
    private static String MODE = "-mode=";

    /**
     * Constructor for the cli parser
     * @param args list of args you wish to parse
     */
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
            arg = arg.toLowerCase().trim();
            System.out.println(arg);
            if (arg.contains(option)) {
                value = arg.replaceFirst(".*=", "");
            }
        }
        return value;
    }

    /**
     * Gets the race mode
     * @return RaceMode enum
     */
    public RaceMode getRaceMode() {
        return raceMode;
    }
}
