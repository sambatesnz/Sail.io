package seng302.XMLCreation;

import org.dom4j.Document;
import org.junit.Test;
import seng302.Race;

import static org.junit.Assert.*;

/**
 * Created by sba136 on 26/07/17.
 */
public class RaceXMLCreatorTest {

    @Test
    public void test() throws Exception {
        Race myRace = new Race();
        RaceXMLCreator race = new RaceXMLCreator(myRace);
        Document d = race.createDocument();
        System.out.println(d.asXML());
        //System.out.println(race.createDocument().toString());
    }

}