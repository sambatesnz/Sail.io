package seng302.objects;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class CourseCreatorTest {

    @Test
    public void failedCreation() throws Exception {
        String fileLocation = "fake-location.jpg";
        boolean exceptionThrown = false;

        //In an idea world this would work: please look into this if you have the time
//        ExpectedException thrown;
//        thrown.expect(FileNotFoundException.class);
//        thrown.expectMessage("/home/cosc/student/sha162/Documents/team-4/src/main/resources/config/asd.xml (No such file or directory)");


        try {
            new CourseCreator(fileLocation);
        }
        catch (Error e){
            exceptionThrown = true;
        }
        assertTrue("Expected a false file name to throw an error", exceptionThrown);
    }

    @Test
    public void fileWithOneMark() throws Exception {
        String fileLocation = "/src/test/test-resources/one-mark.xml";
        CourseCreator courseCreator = new CourseCreator(fileLocation);
        ArrayList<CompoundMark> marks = courseCreator.getCompoundMarks();
        assertEquals("There should only be one mark on this course" ,marks.size(), 1);
        CompoundMark mark1 = marks.get(0);
        ArrayList<CompoundMark.Point> points = mark1.getPoints();
        CompoundMark.Point point = points.get(0);
        assertTrue(point.getLatitude() == 32.293039);

    }

}