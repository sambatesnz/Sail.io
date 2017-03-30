package seng302.objects;

import org.junit.Test;
import seng302.Model.CompoundMark;
import seng302.Model.CourseCreator;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class CourseCreatorTest {

    @Test
    public void fileWithOneMark() throws Exception {
        String fileLocation = "one-mark.xml";
        CourseCreator courseCreator = new CourseCreator(fileLocation);
        ArrayList<CompoundMark> marks = courseCreator.getCompoundMarks();
        assertEquals("There should only be one mark on this course" ,marks.size(), 1);
        CompoundMark mark1 = marks.get(0);
        ArrayList<CompoundMark.Point> points = mark1.getCompoundMarks();
        CompoundMark.Point point = points.get(0);
        assertTrue(point.getLatitude() == 32.293039);

    }

}