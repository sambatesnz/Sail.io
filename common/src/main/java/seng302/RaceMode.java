package seng302;

/**
 * Stores the possible types of race modes
 */
public enum RaceMode {
    RACE("race", 4941),
    PLAY("play", 4942),
    PRACTICE("practice", 4943),
    CUSTOM("custom", 4941);

    private String value;
    private int port;

    RaceMode(String value, int port){
        this.value = value;
        this.port = port;
    }

    public int getPort() {return this.port;}

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
