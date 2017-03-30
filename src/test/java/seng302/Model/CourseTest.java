package seng302.Model;

import org.junit.Test;

import java.io.IOException;
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
    public void generateTotalCourseLengthWithTwoMarks() throws IOException {
        //Linear course is a course with mark(0,0) and mark(0,10)
        String fileLocation = "linear-course.xml";
        Course myCourse = new Course("Test course", fileLocation);

        double courseLength = myCourse.generateTotalCourseLength();
        double expectedCourseLength = 1112;

        //check whether the course length is in an acceptable range of +-2km
        assertTrue(isNumInRange(courseLength, expectedCourseLength, RANGE));
    }


    @Test
    public void generateTotalCourseLengthWithFourMarks() throws IOException {
        //Linear course is a course with mark(0,0) and mark(0,10)
        String fileLocation = "linear-course.xml";
        Course myCourse = new Course("Test course", fileLocation);

        ArrayList<Integer> newCourseOrder = new ArrayList<>(Arrays.asList(1,2,1,2));
        myCourse.overloadCourseOrder(newCourseOrder);

        double courseLength = myCourse.generateTotalCourseLength();
        double expectedCourseLength = 3335;


        assertTrue(isNumInRange(courseLength, expectedCourseLength, RANGE));
    }

    @Test
    public void generateTotalCourseLengthOfAmericasCupRace() throws IOException {
        String fileLocation = "americas-cup-course.xml";
        Course americasCupCourse = new Course("Americas Cup Course", fileLocation);

        double courseLength = americasCupCourse.generateTotalCourseLength();

        assertTrue(isNumInRange(courseLength, 10.5, 1));
    }
}