package seng302.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by tjg73 on 24/03/17.
 */
public class CourseTest {

    @Test
    public void generateTotalCourseLength(){
        String fileLocation = "/src/test/test-resources/linear-course.xml";
        Course myCourse = new Course("Test course", fileLocation);

        System.out.println(myCourse.generateTotalCourseLength());



//        CompoundMark markA = new CompoundMark("Mark 1", 1);
//        markA.addMark(0 , 0);
//
//        CompoundMark markB = new CompoundMark("Mark 2", 2);
//        markA.addMark(0 , 10);
//
//        ArrayList<CompoundMark> compoundMarks = new ArrayList<>();
//        compoundMarks.add(markA);
//        compoundMarks.add(markB);
//
//        ArrayList<Integer> courseOrder = new ArrayList<>(Arrays.asList(1,2));

//        Course myCourse = new Course("Test Course", compoundMarks, courseOrder);
//
//        Course.




    }
}