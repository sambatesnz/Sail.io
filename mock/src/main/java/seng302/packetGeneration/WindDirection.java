package seng302.packetGeneration;

/**
 * enum for the wind direction used in packet generation\
 * Comes from AC35 specification
 * There is no West specificed
 */
public class WindDirection {
    public static short NORTH = 0x000;
    public static short EAST = 0x4000;
    public static short SOUTH = (short) 0x8000;

}
