package seng302.Polars;

/**
 * Enum for polar ratios based on the angle to the wind
 */
public enum PolarRatio {

    ANGLE0(1.162),
    ANGLE15(1.5275),
    ANGLE30(1.893),
    ANGLE45(1.9205),
    ANGLE60(1.948),
    ANGLE75(1.9195),
    ANGLE90(1.891),
    ANGLE105(1.712),
    ANGLE120(1.532),
    ANGLE135(1.144),
    ANGLE150(0.756),
    ANGLE165(0.38),
    ANGLE180(0.0);

    private double ratio;

    PolarRatio(double ratio){
        this.ratio = ratio;
    }

    public double getRatio(){
        return this.ratio;
    }

}
