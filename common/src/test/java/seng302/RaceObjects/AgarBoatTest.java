package seng302.RaceObjects;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import seng302.BoatGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.security.util.PendingException;

import static org.junit.Assert.*;

/**
 * Agar boat tests
 */
public class AgarBoatTest {

    BoatInterface boat;

    @Before
    public void setup() throws Exception {
        BoatGenerator generator = new BoatGenerator();
        boat = new AgarBoat(generator.generateBoat());
    }

    @Test
    public void startingNumberOfLives() throws Exception {
        int expectedNumberOfLives = 3;
        int actualNumberOfLives = boat.getLives();
        assertEquals(expectedNumberOfLives, actualNumberOfLives);
    }

    @Test
    public void losingALife() throws Exception {
        int expectedNumberOfLives = 2;
        boat.loseLife();
        int actualNumberOfLives = boat.getLives();
        assertEquals(expectedNumberOfLives, actualNumberOfLives);
    }

    @Test
    public void boatElimination() throws Exception {
        boolean expectedElimination = true;
        while (!boat.isEliminated()){
            boat.loseLife();
        }
        boolean actualEliminationFlag = boat.isEliminated();
        assertEquals(expectedElimination, actualEliminationFlag);
    }

    @Test
    public void losingCorrectNumberOfLives() throws Exception {
        int expectedNumberOfLives = 0;
        int initialNumberOfLives = 3;
        boolean expectedElimination = true;
        for (int i = 0; i < initialNumberOfLives; i++) {
            boat.loseLife();
        }
        int actualNumberOfLives = boat.getLives();
        boolean actualEliminationFlag = boat.isEliminated();
        assertEquals(expectedNumberOfLives, actualNumberOfLives);
        assertEquals(expectedElimination, actualEliminationFlag);
    }



    @Ignore @Test
    public void sizeIncreas() throws Exception {
        assertTrue("Functionality not implemented yet" ,true==false);
    }



}