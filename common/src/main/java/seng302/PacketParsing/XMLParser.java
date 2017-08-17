
package seng302.PacketParsing;


import javafx.scene.paint.Color;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import seng302.RaceObjects.*;
import seng302.Rounding;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XMLParser {

    private static final String LATITUDE = "Lat";
    private static final String LONGITUDE = "Lon";
    private static final String COMPOUND_MARK_ID = "CompoundMarkID";
    private static final String TYPE = "Type";
    private static final String YACHT = "Yacht";
    private static final String COURSE = "Course";
    private static final String COURSE_LIMIT = "CourseLimit";
    private static final String PARTICIPANTS = "Participants";
    private static final String RACESTARTTIME = "RaceStartTime";
    private static final String TIME = "Time";
    private static final String START = "Start";
    private static final String SEQ_ID = "SeqID";
    private static final String NAME = "Name";
    private static final String TARGETLAT = "TargetLat";
    private static final String TARGETLON = "TargetLng";
    private static final String SOURCEID = "SourceID";
    private static final String SHORTNAME = "ShortName";
    private static final String BOATNAME = "BoatName";
    private static final String COUNTRY = "Country";
    private static final String COMPOUND_MARK_SEQUENCE = "CompoundMarkSequence";
    private static final String BOATS = "Boats";
    private static final String REGATTA = "RegattaConfig";
    private static final String REGATTA_ID = "RegattaID";
    private static final String REGATTA_NAME = "RegattaName";
    private static final String COURSE_NAME = "CourseName";
    private static final String UTC_OFFSET = "UtcOffset";
    private static final String ROUNDING = "Rounding";

    private String xmlString;
    private Document xmlDoc;
    private Map<Integer, Mark> markMap;


    /**
     * Creates an object that we can create files
     *
     * @param xml the relative location of the course xml file
     * @throws IOException when the string cannot be created into a doc
     */
    public XMLParser(String xml) throws IOException {
        this.xmlString = xml.trim();
        xmlDoc = convertStringToDocument();
    }

    /**
     * Loads the course xml file as a Document with a DOM Structure
     *
     * @return The DOM document which we can now parse
     */
    private Document convertStringToDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            doc.getDocumentElement().normalize();
            return doc;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the textual value from a single xml element
     *
     * @param element the element you wish to find the value for
     * @param name    the name of the element
     * @return A string containing the textual value contained inside an xml element
     */
    private String getCparseMessageontentFromElement(Element element, String name) {
        return element.getElementsByTagName(name).item(0).getTextContent();
    }

    /**
     * Overloaded function
     *
     * @param element
     * @param name
     * @param order   If you have multiple tags, you can define the order you want
     * @return
     */
    private String getContentFromElement(Element element, String name, int order) {
        return element.getElementsByTagName(name).item(order).getTextContent();
    }

    /**
     * Gets the gate order defined from the xml
     *
     * @return Arraylist containing the gate order
     */
    public List<CourseLimit> getCourseLimits() {
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


    /**
     * Gets the gate order defined from the xml
     *
     * @return Arraylist containing the gate order
     */
    public List<Integer> getRaceParticipants() {
        NodeList nodes = xmlDoc
                .getElementsByTagName(PARTICIPANTS)
                .item(0)
                .getChildNodes();
        ArrayList<Integer> participants = new ArrayList<>();
        try {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    int sourceID = Integer.valueOf(nnm.getNamedItem(SOURCEID).getNodeValue());
                    participants.add(sourceID);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Collections.sort(participants);
        return participants;
    }

    // TODO: Refactor to return the expected start time as a long
//    public LocalDateTime getRaceStartTime() {
//        Node node = xmlDoc.getElementsByTagName(RACESTARTTIME).item(0);
//        NamedNodeMap nnm = node.getAttributes();
//        String timeString = nnm.getNamedItem(TIME).getNodeValue();
//        timeString = timeString.substring(0,19);
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//        LocalDateTime raceStartTime = LocalDateTime.parse(timeString, formatter);
//        return raceStartTime;
//    }

    public LocalDateTime getRaceStartTime() {
        Node node = xmlDoc.getElementsByTagName(RACESTARTTIME).item(0);
        NamedNodeMap nnm = node.getAttributes();
        String timeString;
        try {
            timeString = nnm.getNamedItem(TIME).getNodeValue();
        } catch (NullPointerException e) {
            timeString = nnm.getNamedItem(START).getNodeValue();
        }

        timeString = timeString.substring(0,19);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime raceStartTime = LocalDateTime.parse(timeString, formatter);
        return raceStartTime;
    }

    public List<Leg> getCourseOrder() {
        NodeList nodes = xmlDoc.getElementsByTagName(COMPOUND_MARK_SEQUENCE).item(0).getChildNodes();
        List<Map<String, String>> courseOrder = new ArrayList<>();
        List<Leg> legs = new ArrayList<>();
        try {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    String seqId = nnm.getNamedItem(SEQ_ID).getNodeValue();
                    String compoundMarkId = nnm.getNamedItem(COMPOUND_MARK_ID).getNodeValue();
                    String rounding = nnm.getNamedItem(ROUNDING).getNodeValue();
                    Map<String, String> compoundMarkInfo = new HashMap<>();
                    compoundMarkInfo.put(SEQ_ID, seqId);
                    compoundMarkInfo.put(COMPOUND_MARK_ID, compoundMarkId);
                    compoundMarkInfo.put(ROUNDING, rounding);

                    courseOrder.add(compoundMarkInfo);
                }
            }
            courseOrder.sort(Comparator.comparingInt(o -> Integer.parseInt(o.get(SEQ_ID))));
            for (Map<String, String> map: courseOrder) {
                legs.add(new Leg(Rounding.getEnum(map.get(ROUNDING)), Integer.parseInt(map.get(COMPOUND_MARK_ID))));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return legs;
    }

    public List<CompoundMark> getCourseLayout() {
        NodeList nodes = xmlDoc.getElementsByTagName(COURSE).item(0).getChildNodes();
        List<CompoundMark> courseObjects = new ArrayList<>();
        try {
            markMap = new HashMap<>();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                ArrayList<Mark> positions = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = node.getAttributes();
                    String compoundMarkName = nnm.getNamedItem(NAME).getNodeValue();
                    int compoundMarkId = Integer.parseInt(nnm.getNamedItem(COMPOUND_MARK_ID).getNodeValue());
                    NodeList marks = node.getChildNodes();
                    for (int j = 0; j < marks.getLength(); j++) {
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
                            positions.add(new Mark(lat, lon, sourceId));
//                            System.out.println(String.format("Name: %s, Lat: %f, Long: %f, SrcId: %d, SeqId: %d", name, lat, lon, sourceId, seqId));
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

    public Map<Integer, Mark> getMarks() {
        return markMap;
    }

    public Map<Integer, Boat> getBoats() {
        NodeList nodes = xmlDoc.getElementsByTagName(BOATS).item(0).getChildNodes();
        Map<Integer, Boat> boats = new HashMap<>();
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

                        Boat boat = new Boat(boatName, shortName, srcId, country);
                        boats.put(srcId, boat);
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return boats;
    }

    public Regatta getRegatta() {
        NodeList nodes = xmlDoc.getElementsByTagName(REGATTA).item(0).getChildNodes();
        int regattaId = -1;
        String regattaName = "";
        String courseName = "";
        int utc = -1;
        try {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    System.out.println(node.getNodeName());
                    NamedNodeMap nnm = node.getAttributes();
//                    System.out.println(node.getTextContent());
                    switch (node.getNodeName()) {
                        case REGATTA_ID:
                            regattaId = Integer.parseInt(node.getTextContent());
                            break;
                        case REGATTA_NAME:
                            regattaName = node.getTextContent();
                            break;
                        case COURSE_NAME:
                            courseName = node.getTextContent();
                            break;
                        case UTC_OFFSET:
                            utc = Integer.parseInt(node.getTextContent());
                            break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new Regatta(regattaId, regattaName, courseName, utc);
    }
}