package seng302.Polars;

/**
 * Created by tjg73 on 26/07/17.
 */
public enum PolarRatio {

    ANGLE0(0.0),
    ANGLE15(0.38),
    ANGLE30(0.756),
    ANGLE45(1.144),
    ANGLE60(1.532),
    ANGLE75(1.712),
    ANGLE90(1.891),
    ANGLE105(1.9195),
    ANGLE120(1.948),
    ANGLE135(1.9205),
    ANGLE150(1.893),
    ANGLE165(1.5275),
    ANGLE180(1.162);

    private double ratio;

    PolarRatio(double ratio){
        this.ratio = ratio;
    }

    public double getRatio(){
        return this.ratio;
    }

}
