package seng302.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by tjg73 on 24/03/17.
 */
public class CourseTest {

    Integer RANGE = 2;

    private boolean isNumInRange(double actual, double expected, int range){
        return (expected < actual + range) && (expected > actual - range);
    }

    @Test
    public void generateTotalCourseLengthWithTwoMarks(){
        //Linear course is a course with mark(0,0) and mark(0,10)
        String fileLocation = "/src/test/test-resources/linear-course.xml";
        Course myCourse = new Course("Test course", fileLocation);

        double courseLength = myCourse.generateTotalCourseLength();
        double expectedCourseLength = 1112;

        //check whether the course length is in an acceptable range of +-2km
        assertTrue(isNumInRange(courseLength, expectedCourseLength, RANGE));
    }


    @Test
    public void generateTotalCourseLengthWithFourMarks(){
        //Linear course is a course with mark(0,0) and mark(0,10)
        String fileLocation = "/src/test/test-resources/linear-course.xml";
        Course myCourse = new Course("Test course", fileLocation);

        ArrayList<Integer> newCourseOrder = new ArrayList<>(Arrays.asList(1,2,1,2));
        myCourse.overloadCourseOrder(newCourseOrder);

        double courseLength = myCourse.generateTotalCourseLength();
        double expectedCourseLength = 2224;

        assertTrue(isNumInRange(courseLength, expectedCourseLength, RANGE));
    }
}