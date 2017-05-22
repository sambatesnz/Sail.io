package seng302;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by osr13 on 12/05/17.
 */
public class XMLParserTest {
    private XMLParser raceParser;
    private XMLParser boatParser;
    private XMLParser regattaParser;

    @Before
    public void setup() throws IOException {
        java.util.Scanner s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("Race.xml")).useDelimiter("\\A");
        String xmlString = s.hasNext() ? s.next() : "";
        raceParser = new XMLParser(xmlString);

        s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("Boats.xml")).useDelimiter("\\A");
        xmlString = s.hasNext() ? s.next() : "";
        boatParser = new XMLParser(xmlString);

        s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("Regatta.xml")).useDelimiter("\\A");
        xmlString = s.hasNext() ? s.next() : "";
        regattaParser = new XMLParser(xmlString);
    }

    @Test
    public void checkCourseLimitsCorrect() throws IOException {
        List<CourseLimit> cls = raceParser.getCourseLimits();
        Assert.assertTrue(cls.size() == 6);
    }

    @Test
    public void checkCourseLayoutsCorrect() throws IOException {
        List<CompoundMark> compoundMarks = raceParser.getCourseLayout();
        Assert.assertTrue(compoundMarks.size() == 5);
    }

    @Test
    public void checkCourseOrderCorrect() throws IOException {
        List<Integer> courseOrder = raceParser.getCourseOrder();
        Assert.assertTrue(courseOrder.size() == 6);
    }

    @Test
    public void checkBoats() throws IOException {
        Map<Integer, Boat> boats = boatParser.getBoats();
        Assert.assertTrue(boats.size() == 6);
    }

    @Test
    public void checkRegatta() throws IOException {
        Regatta reg = regattaParser.getRegatta();
        Assert.assertTrue(reg.getUtcOffset() == 12);
    }

    @Test
    public void checkParticipants() throws IOException {
        List<Integer> participants = raceParser.getRaceParticipants();
        Assert.assertTrue(participants.size() == 2);
    }

    @Test
    public void checkStartTime() throws IOException {
        LocalDateTime startTime = raceParser.getRaceStartTime();
        LocalDateTime trial = LocalDateTime.of(2012, 12, 31, 11, 59);
        System.out.println(startTime.toString());
        Assert.assertTrue(startTime.isBefore(trial));
    }
}
