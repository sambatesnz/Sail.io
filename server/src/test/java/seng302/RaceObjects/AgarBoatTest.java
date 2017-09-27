package seng302.RaceObjects;

import org.junit.Before;
import org.junit.Test;
import seng302.BoatGenerator;
import seng302.Modes.AgarRace;

import static org.junit.Assert.assertEquals;

/**
 * Agar boat tests
 */
public class AgarBoatTest {

    GenericBoat boat;

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

    @Test
    public void losingSizeTest() throws Exception {
        int expectedSize = 50;
        AgarRace race = new AgarRace();
        while (boat.getAgarSize() > 50) {
            boat.setAgarSize(boat.getAgarSize() - 1);
        }
        assertEquals(expectedSize, boat.getAgarSize());
    }

    @Test
    public void resettingSize() throws Exception {
        int expectedSize = 100;
        AgarRace race = new AgarRace();
        while (boat.getAgarSize() > 50) {
            race.reduceBoatSize(boat);
        }
        boat.loseLife();
        assertEquals(expectedSize, boat.getAgarSize());
    }

    @Test
    public void minimumSize() throws Exception {
        int expectedLives = 2;

        AgarRace race = new AgarRace();

        while (boat.getLives() != 2) {
            try {
                race.reduceBoatSize(boat);
            } catch (Exception e) {
                //We expected this to fail
            }
        }
        assertEquals(expectedLives, boat.getLives());
    }

}