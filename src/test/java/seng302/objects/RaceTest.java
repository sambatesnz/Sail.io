package seng302.objects;

import javafx.scene.Group;
import org.junit.Before;
import org.junit.Test;
import seng302.Model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;

import static org.junit.Assert.*;

public class RaceTest {
    private ArrayList<CompoundMark> marks;
    private Race myRace;
    private Course raceCourse;

    @Before
    public void initialize(){
        String fileLocation = "/src/test/test-resources/course-creator-test-course.xml";
        this.raceCourse = new Course("Test Course", fileLocation);
        myRace = new Race(raceCourse);
    }

    @Test
    public void generateBoats(){
        int numBoatsInRace = 2;
        myRace.generateBoats(numBoatsInRace );
        assertEquals("There should be two boats in the race", myRace.getRacingBoats().size(), numBoatsInRace );
    }

    @Test
    public void slowestBoatSpeed(){
        int numBoatsInRace = 6;
        myRace.generateBoats(numBoatsInRace);
        float slowestBoatSpeed = Integer.MAX_VALUE;
        for (Boat boat: myRace.getRacingBoats()){
            float currentBoatSpeed = boat.getBoatSpeed();
            if (currentBoatSpeed < slowestBoatSpeed) {
                slowestBoatSpeed = currentBoatSpeed;
            }
        }
        assertTrue(slowestBoatSpeed == myRace.getSlowestBoatSpeed());
    }

    @Test
    public void updateLatAndLong(){
        String fileLocation = "/src/test/test-resources/linear-course.xml";

        assertTrue(false); //This should fail as is hasnt been implemented
    }


}