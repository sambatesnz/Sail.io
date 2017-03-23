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
    ArrayList<CompoundMark> marks;
    Race myRace;
    Course raceCourse;

    @Before
    public void initialize(){
        String fileLocation = "/src/test/test-resources/course-creator-test-course.xml";
        CourseCreator courseCreator = new CourseCreator(fileLocation);
        this.marks = courseCreator.getCompoundMarks();
        this.raceCourse = new Course("Test Course");
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
        CourseCreator courseCreator = new CourseCreator(fileLocation);
        ArrayList<CompoundMark> marks = courseCreator.getCompoundMarks();
        Course raceCourse = new Course("test");
        Race myRace = new Race(raceCourse);
        myRace.raceSetup();
        System.out.println(myRace);

    }


}