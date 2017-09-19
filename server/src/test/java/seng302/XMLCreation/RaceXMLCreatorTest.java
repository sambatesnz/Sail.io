package seng302.XMLCreation;

import org.junit.Test;
import seng302.Modes.Race;

/**
 * Created by sba136 on 26/07/17.
 */
public class RaceXMLCreatorTest {


    @Test
    public void getXML() throws Exception {
        Race myRace = new Race();
        RaceXMLCreator race = new RaceXMLCreator(myRace);
        System.out.println(race.getXML());
        //System.out.println(race.createDocument().toString());
    }

}