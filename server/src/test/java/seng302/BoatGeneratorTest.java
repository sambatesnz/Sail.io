package seng302;

import org.junit.Test;
import seng302.RaceObjects.Boat;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoatGeneratorTest {

    @Test
    public void generateBoat() throws Exception {
        BoatGenerator boatGenerator = new BoatGenerator();
        int expectedSourceId = boatGenerator.getLowestSourceId(); //Lowest source id
        for (int i = 0; i < 20; i++) {
            Boat b = boatGenerator.generateBoat();
            assertEquals(b.getSourceId(), expectedSourceId);
            expectedSourceId++;
        }
    }

    @Test
    public void addRemoveBoats() throws Exception {
        BoatGenerator boatGenerator = new BoatGenerator();

        boatGenerator.generateBoat();
        Boat secondBoat = boatGenerator.generateBoat();
        String expectedName = secondBoat.getName();
        boatGenerator.makeAvailable(secondBoat);
        Boat thirdBoat = boatGenerator.generateBoat();
        assertEquals(expectedName, thirdBoat.getName());
    }

    @Test
    public void addMaxBoats() {
        BoatGenerator boatGenerator = new BoatGenerator();
        ArrayList<Boat> boats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            try {
                boats.add(boatGenerator.generateBoat());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 5; i++) {
            try {
                boatGenerator.makeAvailable(boats.get(i));
                boats.remove(boats.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        assertEquals(5, boats.size());

        for (int i = 0; i < 8; i++) {
            try {
                boats.add(boatGenerator.generateBoat());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        assertEquals(13, boats.size());

    }

}