package seng302.Helper;

import seng302.BoatGenerator;
import seng302.RaceObjects.Boat;

/**
 * Helper for testing to generate a boat with a given sourceId
 */
public class BoatGeneratorExtension extends BoatGenerator {

    public Boat generateBoat(int id){
        Boat boat = super.generateBoat();
        boat.setSourceID(id);
        return boat;
    }
}
