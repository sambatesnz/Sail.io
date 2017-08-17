package seng302.RaceObjects;

import seng302.Rounding;

/**
 * Represents a section of the Race
 */
public class Leg {
    private Rounding rounding;

    public Rounding getRounding() {
        return rounding;
    }

    public int getCompoundMarkId() {
        return compoundMarkId;
    }

    private int compoundMarkId;

    public Leg(Rounding rounding, int compoundMarkId) {
        this.rounding = rounding;
        this.compoundMarkId = compoundMarkId;
    }
}