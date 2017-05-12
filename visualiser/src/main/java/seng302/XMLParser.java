
package seng302;


import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class XMLParser {

    private String WINDDIRECTION = "windDirection";
    private String LATITUDE = "Lat";
    private String LONGITUDE = "Lon";
    private String COMPOUND_MARK = "compoundMark";
    private String MARK_TYPE = "type";
    private String MARK_NAME = "name";
    private String MARK_COLOR = "color";
    private String MARK_ID = "id";
    private String MARK = "Mark";
    private String GATE = "Gate";
    private String GATE_ORDER = "gateOrder";
    private String BOUNDARY = "boundary";
    private String COURSE_LIMIT = "CourseLimit";
    private String SEQ_ID = "SeqID";

    private String xmlString;
    private Document xmlDoc;


    /**
     * Creates an object that we can create files
     * @param xml the relative location of the course xml file
     */
    public XMLParser(String xml) throws IOException {
        this.xmlString = xml;
        xmlDoc = loadCourseXmlFile();
    }

    /**
     * Loads the course xml file as a Document with a DOM Structure
     * @return The DOM document which we can now parse
     */
    private Document loadCourseXmlFile() throws IOException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlString)));
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the textual value from a single xml element
     * @param element the element you wish to find the value for
     * @param name the name of the element
     * @return A string containing the textual value contained inside an xml element
     */
    private String getContentFromElement(Element element, String name){
        return element.getElementsByTagName(name).item(0).getTextContent();
    }

    /**
     * Overloaded function
     * @param element
     * @param name
     * @param order If you have multiple tags, you can define the order you want
     * @return
     */
    private String getContentFromElement(Element element, String name, int order){
        return element.getElementsByTagName(name).item(order).getTextContent();
    }

    /**
     * Gets the gate order defined from the xml
     * @return Arraylist containing the gate order
     */
    public List<CourseLimit> getCourseLimits(){

        NodeList nodes = xmlDoc.getElementsByTagName(COURSE_LIMIT).item(0).getChildNodes();
        List<CourseLimit> courseLimits = new ArrayList<>();

        try {

            for (int i = 0; i < nodes.getLength(); i++) {

                Element ele = (Element) nodes.item(i);
                System.out.println(ele.toString());
                int seqId = Integer.parseInt(ele.getAttribute(SEQ_ID));
                double lat = Double.parseDouble(ele.getAttribute(LATITUDE));
                double lon = Double.parseDouble(ele.getAttribute(LONGITUDE));

                CourseLimit courseLimit = new CourseLimit(seqId, lat, lon);
                courseLimits.add(courseLimit);

                System.out.println(seqId + ", " + lat + ", " + lon);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        courseLimits.sort(Comparator.comparingInt(CourseLimit::getSeqId));
        return courseLimits;
    }

//    public int getWindDirection() {
//        NodeList nodes = this.xmlDoc.getElementsByTagName(WINDDIRECTION);
//        int windDir = 0;
//
//        try
//        {
//            for (int i=0; i< nodes.getLength(); i++){
//                String strippedNode = nodes.item(i).getTextContent().replaceAll("\\s+", "");
//                windDir = Integer.parseInt(strippedNode);
//            }
//        } catch (Exception e) {
//            System.out.println("Could not parse the windspeed.");
//            throw e;
//        }
//        return windDir;
//    }
//
//    private Landmark createLandmarkFromElement(Element markElement) {
//
//        // Rips the color, type?, and the name for each Landmark
//        String name = markElement.getAttribute(MARK_NAME);
//        String type = markElement.getAttribute(MARK_TYPE);
//
//
//        Color color = Color.valueOf(markElement.getAttribute(MARK_COLOR));
//
//        int id = Integer.parseInt(markElement.getAttribute(MARK_ID));
//        double latitude;
//        double longitude;
//
//        ArrayList<Position> positions = new ArrayList<>();
//        //We know a mark is always going to have a lat and long
//        latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE));
//        longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE));
//
//        positions.add(new Position(latitude, longitude));
//        if (type.equals(GATE)){
//            latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE, 1));
//            longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE, 1));
//            positions.add(new Position(latitude, longitude));
//        }
//        return new Landmark(name, positions, color, id, type);
//    }
//
//    public ArrayList<Landmark> getLandmarks() {
//        NodeList nodes = this.configDoc.getElementsByTagName(COMPOUND_MARK);
//        ArrayList<Landmark> landmarks = new ArrayList<>();
//
//        for (int i=0; i< nodes.getLength(); i++){
//            Node node = nodes.item(i);
//            Element markElement = (Element) node;
//            Landmark mark = createLandmarkFromElement(markElement);
//            landmarks.add(mark);
//        }
//        return landmarks;
//    }
//
//    public ArrayList<Position> getBoundaries() {
//        NodeList nodes = this.configDoc.getElementsByTagName(BOUNDARY);
//        ArrayList<Position> boundaries = new ArrayList<>();
//
//        for (int i=0; i< nodes.getLength(); i++){
//            Node node = nodes.item(i);
//            Element markElement = (Element) node;
//            Position pos = createPositionFromElement(markElement);
//            boundaries.add(pos);
//        }
//        return boundaries;
//    }
//
//    private Position createPositionFromElement(Element markElement) {
//        double latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE));
//        double longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE));
//
//        return new Position(latitude, longitude);
//    }
}


