//package seng302;
//
//import javafx.scene.paint.Color;
//import org.junit.Assert;
//import org.junit.Test;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * Tests the CourseHandler class
// */
//public class CourseHandlerTest {
//    private CourseHandler parseXML(String fileName) throws SAXException {
//        CourseHandler courseHandler = new CourseHandler();
//        try {
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser parser = factory.newSAXParser();
//            parser.parse(getClass().getClassLoader().getResourceAsStream(fileName), courseHandler);
//        } catch (ParserConfigurationException | IOException e) {
//            e.printStackTrace();
//        }
//        return courseHandler;
//    }
//
//    @Test
//    public void testLegsGoodXML() throws SAXException {
//        CourseHandler courseHandler = parseXML("coursetest.xml");
//
//        List<Leg> legs = legs = courseHandler.getLegs();
//        Assert.assertEquals(legs.size(), 5);
//        Assert.assertEquals("Mark", legs.get(1).getHead().getName());
//    }
//
//    @Test
//    public void testGatesGoodXML() throws SAXException {
//        CourseHandler courseHandler = parseXML("coursetest.xml");
//
//        List<Gate> gates = courseHandler.getGates();
//        Assert.assertEquals(gates.size(), 4);
//        Assert.assertEquals("WindwardGate1", gates.get(2).getHead().getName());
//    }
//
//    @Test
//    public void testLandmarksGoodXML() throws SAXException {
//        CourseHandler courseHandler = parseXML("coursetest.xml");
//
//        Map<String, Landmark> landmarksMap = courseHandler.getLandmarks();
//        Assert.assertEquals(landmarksMap.size(), 9);
//        Assert.assertEquals(Color.RED, landmarksMap.get("FinishLine1").getColor());
//    }
//
//    @Test(expected=org.xml.sax.SAXException.class)
//    public void testBadXML() throws SAXException {
//        parseXML("badcoursetest.xml");
//    }
//}
