package seng302;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osr13 on 12/05/17.
 */
public class XMLParserTest {
    private XMLParser xmlParser;

    @Before
    public void setup() throws IOException {
        java.util.Scanner s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("ExampleXMLs/Race.xml")).useDelimiter("\\A");
        String xmlString = s.hasNext() ? s.next() : "";
        xmlParser = new XMLParser(xmlString);
    }

    @Test
    public void checkCourseLimitsCorrect() throws IOException {
        List<CourseLimit> cls = xmlParser.getCourseLimits();
        Assert.assertTrue(cls.size() == 10);
    }

    @Test
    public void checkCourseLayoutsCorrect() throws IOException {
        List<Landmark> landmarks = xmlParser.getCourseLayout();
        Assert.assertTrue(landmarks.size() == 4);
    }
}
