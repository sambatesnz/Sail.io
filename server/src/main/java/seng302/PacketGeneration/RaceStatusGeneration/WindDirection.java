package seng302.PacketGeneration.RaceStatusGeneration;

/**
 * enum for the wind direction used in packet generation\
 * Comes from AC35 specification
 * There is no West specified
 */
public class WindDirection {
    public static short NORTH = 0x0000;
    public static short EAST = 0x4000;
    public static short SOUTH = (short) 0x8000;

    private enum Test {
        NORTH(0x8000),
        EAST(0x4000);

        private short value;

        Test(int value) {this.value = (short) value;}

        public short value() { return value;}

    }

}
