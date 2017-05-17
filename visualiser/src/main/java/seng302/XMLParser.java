
package seng302;


import javafx.scene.paint.Color;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class XMLParser {

    private String WINDDIRECTION = "windDirection";
    private String LATITUDE = "Lat";
    private String LONGITUDE = "Lon";
    private String COMPOUND_MARK_ID = "CompoundMarkID";
    private String TYPE = "Type";
    private String YACHT = "Yacht";
    private String MARK_NAME = "name";
    private String MARK_COLOR = "color";
    private String MARK_ID = "id";
    private String MARK = "Mark";
    private String GATE = "Gate";
    private String GATE_ORDER = "gateOrder";
    private String BOUNDARY = "boundary";
    private String COURSE = "Course";
    private String COURSE_LIMIT = "CourseLimit";
    private String LIMIT = "Limit";
    private String SEQ_ID = "SeqID";
    private String NAME = "Name";
    private String TARGETLAT = "TargetLat";
    private String TARGETLON = "TargetLng";
    private String SOURCEID = "SourceID";
    private String SHORTNAME = "ShortName";
    private String BOATNAME = "BoatName";
    private String COUNTRY = "Country";
    private String COMPOUND_MARK_SEQUENCE = "CompoundMarkSequence";
    private String BOATS = "Boats";
    private String BOAT = "Boat";

    private String xmlString;
    private Document xmlDoc;


    /**
     * Creates an object that we can create files
     * @param xml the relative location of the course xml file
     */
    public XMLParser(String xml) throws IOException {
        this.xmlString = xml;
        xmlDoc = convertStringToDocument();
    }

    /**
     * Loads the course xml file as a Document with a DOM Structure
     * @return The DOM document which we can now parse
     */
    private Document convertStringToDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
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
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    float lon = Float.valueOf(nnm.getNamedItem(LONGITUDE).getNodeValue());
                    float lat = Float.valueOf(nnm.getNamedItem(LATITUDE).getNodeValue());
                    int seqId = Integer.valueOf(nnm.getNamedItem(SEQ_ID).getNodeValue());
                    courseLimits.add(new CourseLimit(seqId, lat, lon));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        courseLimits.sort(Comparator.comparingInt(CourseLimit::getSeqId));
        return courseLimits;
    }


    public List<Integer> getCourseOrder(){
        NodeList nodes = xmlDoc.getElementsByTagName(COMPOUND_MARK_SEQUENCE).item(0).getChildNodes();
        List<Integer> courseOrder = new ArrayList<>();
        try {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    courseOrder.add(Integer.parseInt(nnm.getNamedItem(COMPOUND_MARK_ID).getNodeValue()));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
//        courseOrder.sort(Comparator.comparingInt(CourseLimit::getSeqId));
        return courseOrder;
    }


    public List<CompoundMark> getCourseLayout(){
        NodeList nodes = xmlDoc.getElementsByTagName(COURSE).item(0).getChildNodes();
        List<CompoundMark> courseObjects = new ArrayList<>();
        try {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                ArrayList<Mark> positions = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    String compoundMarkName = nnm.getNamedItem(NAME).getNodeValue();
                    int compoundMarkId = Integer.parseInt(nnm.getNamedItem(COMPOUND_MARK_ID).getNodeValue());
                    NodeList marks = node.getChildNodes();
                    for (int j = 0; j < marks.getLength(); j ++) {
                        Node mark = marks.item(j);
                        if (mark.getNodeType() == Node.ELEMENT_NODE) {
                            NamedNodeMap nnm2 = mark.getAttributes();
                            int sourceId = Integer.valueOf(nnm2.getNamedItem(SOURCEID).getNodeValue());
                            double lat = Double.valueOf(nnm2.getNamedItem(TARGETLAT).getNodeValue());
                            double lon = Double.valueOf(nnm2.getNamedItem(TARGETLON).getNodeValue());
                            String name = nnm2.getNamedItem(NAME).getNodeValue();
                            int seqId = -1;
                            if (nnm2.getLength() >= 5) {
                                seqId = Integer.valueOf(nnm2.getNamedItem(SEQ_ID).getNodeValue());
                            }
                            positions.add(new Mark(lat, lon));
                            System.out.println(String.format("Name: %s, Lat: %f, Long: %f, SrcId: %d, SeqId: %d", name, lat, lon, sourceId, seqId));
                        }
                    }
                    String type = "Mark";
                    if (positions.size() > 1) {
                        type = "Gate";
                    }
                    courseObjects.add(new CompoundMark(compoundMarkName, positions, Color.RED, compoundMarkId, type));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        courseObjects.sort(Comparator.comparingInt(CompoundMark::getId));
        return courseObjects;
    }

    public List<Boat> getBoats() {
        NodeList nodes = xmlDoc.getElementsByTagName(BOATS).item(0).getChildNodes();
        List<Boat> boats = new ArrayList<>();
        try {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    if (nnm.getNamedItem(TYPE).getNodeValue().equals(YACHT)) {
                        int srcId = Integer.parseInt(nnm.getNamedItem(SOURCEID).getNodeValue());
                        String shortName = nnm.getNamedItem(SHORTNAME).getNodeValue();
                        String boatName = nnm.getNamedItem(BOATNAME).getNodeValue();
                        String country = nnm.getNamedItem(COUNTRY).getNodeValue();
                        boats.add(new Boat(boatName, shortName, srcId, country));
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
//        courseOrder.sort(Comparator.comparingInt(CourseLimit::getSeqId));
        return boats;
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
//    private CompoundMark createLandmarkFromElement(Element markElement) {
//
//        // Rips the color, type?, and the name for each CompoundMark
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
//        ArrayList<Mark> positions = new ArrayList<>();
//        //We know a mark is always going to have a lat and long
//        latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE));
//        longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE));
//
//        positions.add(new Mark(latitude, longitude));
//        if (type.equals(GATE)){
//            latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE, 1));
//            longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE, 1));
//            positions.add(new Mark(latitude, longitude));
//        }
//        return new CompoundMark(name, positions, color, id, type);
//    }
//
//    public ArrayList<CompoundMark> getCompoundMarks() {
//        NodeList nodes = this.configDoc.getElementsByTagName(COMPOUND_MARK);
//        ArrayList<CompoundMark> landmarks = new ArrayList<>();
//
//        for (int i=0; i< nodes.getLength(); i++){
//            Node node = nodes.item(i);
//            Element markElement = (Element) node;
//            CompoundMark mark = createLandmarkFromElement(markElement);
//            landmarks.add(mark);
//        }
//        return landmarks;
//    }
//
//    public ArrayList<Mark> getBoundaries() {
//        NodeList nodes = this.configDoc.getElementsByTagName(BOUNDARY);
//        ArrayList<Mark> boundaries = new ArrayList<>();
//
//        for (int i=0; i< nodes.getLength(); i++){
//            Node node = nodes.item(i);
//            Element markElement = (Element) node;
//            Mark pos = createPositionFromElement(markElement);
//            boundaries.add(pos);
//        }
//        return boundaries;
//    }
//
//    private Mark createPositionFromElement(Element markElement) {
//        double latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE));
//        double longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE));
//
//        return new Mark(latitude, longitude);
//    }
}


