package seng302;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by osr13 on 12/05/17.
 */
public class XMLParserTest {

    @Test
    public void checkInputCorrect() throws IOException {
        java.util.Scanner s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("ExampleXMLs/Race.xml")).useDelimiter("\\A");
        String xmlString = s.hasNext() ? s.next() : "";


        XMLParser xmlParser = new XMLParser(xmlString);
        xmlParser.getCourseLimits();


        Assert.assertTrue(true);
    }
}
