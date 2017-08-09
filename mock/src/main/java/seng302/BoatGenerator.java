package seng302;

import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Mark;

/**
 * Generates boats dynamically while tracking their source id
 */
public class BoatGenerator {

    private static int LOWEST_SOURCE_ID = 101;
    private int sourceId;

    public BoatGenerator () {
        this.sourceId = LOWEST_SOURCE_ID;
    }

    public Boat generateBoat(){
        Boat boat = new Boat("Kevin", "KVN", sourceId, "kevin land");
        Mark mark = new Mark(32.3090300, -64.8466850);
        boat.setMark(mark);
        sourceId++;
        return boat;
    }

    public int getLowestSourceId(){
        return LOWEST_SOURCE_ID;
    }

}
