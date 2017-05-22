package seng302.packetGeneration.XMLMessageGeneration;

/**
 * Enum for message subtypes as defined by xml message specification
 */
public enum XMLSubTypes {
    REGATTA(5),
    RACE(6),
    BOAT(7);

    private int subType;

    XMLSubTypes(int subType){
        this.subType = subType;
    }

    public int getSubType(){
        return subType;
    }
}
