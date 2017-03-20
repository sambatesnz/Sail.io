package seng302.objects;

import org.junit.Before;
import org.junit.Test;
import seng302.Model.Boat;
import seng302.Model.CompoundMark;
import seng302.Model.CourseCreator;
import seng302.Model.Race;

import java.io.File;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;

import static org.junit.Assert.*;

public class RaceTest {
    ArrayList<CompoundMark> marks;
    Race myRace;

    @Before
    public void initialize(){
        String fileLocation = "/src/test/test-resources/course-creator-test-course.xml";
        CourseCreator courseCreator = new CourseCreator(fileLocation);
        this.marks = courseCreator.getCompoundMarks();
        myRace = new Race(marks);
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


}