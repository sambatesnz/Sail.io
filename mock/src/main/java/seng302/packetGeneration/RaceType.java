package seng302.packetGeneration;


enum RaceType {
    MATCH_RACE('1'),
    FLEET_RACE('2');

    private char value;

    RaceType(char value) {this.value = value;}

    public char value() { return value; }
}



