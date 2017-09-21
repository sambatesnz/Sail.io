package seng302;

import org.junit.Before;
import org.junit.Test;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.BoatInterface;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoatGeneratorTest {

    private BoatGenerator boatGenerator;

    @Before
    public void setup(){
        boatGenerator = new BoatGenerator();
    }

    @Test
    public void generateBoat() throws Exception {
        int expectedSourceId = boatGenerator.getLowestSourceId(); //Lowest source id
        for (int i = 0; i < 20; i++) {
            BoatInterface b = boatGenerator.generateBoat();
            assertEquals(b.getSourceId(), expectedSourceId);
            expectedSourceId++;
        }
    }

    @Test
    public void addRemoveBoats() throws Exception {
        boatGenerator.generateBoat();
        BoatInterface secondBoat = boatGenerator.generateBoat();
        String expectedName = secondBoat.getName();
        boatGenerator.makeAvailable(secondBoat);
        BoatInterface thirdBoat = boatGenerator.generateBoat();
        assertEquals(expectedName, thirdBoat.getName());
    }

    @Test
    public void addAndRemoveMaxAmountOfBoats() {
        //This test adds and removes boats to see if they are being tracked properly
        ArrayList<BoatInterface> boats = new ArrayList<>();
        addBoats(10, boats);
        removeBoats(5, boats);
        assertEquals(5, boats.size()); //There should now be five boats
        addBoats(15, boats);
        assertEquals(20, boats.size());

    }

    private void removeBoats(int amount, ArrayList<BoatInterface> boats) {
        for (int i = 0; i < amount; i++) {
            try {
                boatGenerator.makeAvailable(boats.get(i));
                boats.remove(boats.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addBoats(int amount, ArrayList<BoatInterface> boats) {
        for (int i = 0; i < amount; i++) {
            try {
                boats.add(boatGenerator.generateBoat());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void addTooManyBoats()  {
        boolean expected = true;
        boolean exceptionThrown = false;
        for (int i = 0; i < 21; i++) {
            try {
                boatGenerator.generateBoat();
            } catch (Exception e) {
                exceptionThrown = true;
            }
        }
        assertEquals(expected, exceptionThrown);
    }

}