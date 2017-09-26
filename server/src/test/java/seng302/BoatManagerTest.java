package seng302;

import org.junit.Before;
import org.junit.Test;
import seng302.RaceObjects.AgarBoat;
import seng302.RaceObjects.BoatDecorator;
import seng302.RaceObjects.GenericBoat;

import static org.junit.Assert.*;

/**
 * Tests boat mangers class
 */
public class BoatManagerTest {

    BoatManager boatManager;
    BoatGenerator boatGenerator;


    @Before
    public void setup() throws Exception {
        this.boatManager = new BoatManager();
        this.boatGenerator = new BoatGenerator();
    }

    private GenericBoat createEliminatedBoat() throws Exception {
        BoatDecorator boat = new AgarBoat(boatGenerator.generateBoat());
        for (int i = 0; i < 3; i++) {
            boat.loseLife();
        }
        return boat;
    }

    @Test
    public void addEliminatedBoat() throws Exception {
        int expectedElimBoats = 1;
        GenericBoat boat = createEliminatedBoat();
        boatManager.addEliminatedBoat(boat);
        assertEquals(expectedElimBoats, boatManager.getEliminatedBoats().size());
    }

    @Test
    public void addSameBoatTwice() throws Exception {
        int expectedElimBoats = 1;
        GenericBoat boat = createEliminatedBoat();
        boatManager.addEliminatedBoat(boat);
        boatManager.addEliminatedBoat(boat);
        assertEquals(expectedElimBoats, boatManager.getEliminatedBoats().size());
    }

}