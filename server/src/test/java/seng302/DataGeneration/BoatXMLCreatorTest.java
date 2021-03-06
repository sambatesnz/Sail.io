package seng302.DataGeneration;

import org.junit.Test;
import seng302.BoatGenerator;
import seng302.RaceObjects.GenericBoat;
import seng302.XMLCreation.XMLCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoatXMLCreatorTest {


    @Test
    public void getXML() throws Exception {
        BoatGenerator boatGenerator = new BoatGenerator();

        List<GenericBoat> boats = new ArrayList<>(Arrays.asList());

        for (int i = 0; i < 20; i++) {
            boats.add(boatGenerator.generateBoat());
        }

        XMLCreator boatXmlCreator = new BoatXMLCreator(boats);

        System.out.println(boatXmlCreator.getXML());
    }

}