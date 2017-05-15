package seng302.packetGeneration;


/**
 * Enum for current type of race as defined by the AC35 specification
 */
enum RaceType {
    MATCH_RACE('1'),
    FLEET_RACE('2');

    private char value;

    RaceType(char value) {this.value = value;}

    public char value() { return value; }
}



