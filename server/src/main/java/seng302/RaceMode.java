package seng302;

/**
 * Stores the possible types of race modes
 */
public enum RaceMode {
    RACE("race"),
    AGAR("agar"),
    PRACTICE("practice");

    private String value;

    RaceMode(String value){
        this.value = value;
    }

    public String getRaceMode(){
        return this.value;
    }

    public static RaceMode getRaceMode(String value){
        RaceMode raceMode = RACE;
        for (RaceMode rm: RaceMode.values()){
            if (rm.getRaceMode().equals(value)) {
                raceMode = rm;
            }
        }
        return raceMode;
    }
}
