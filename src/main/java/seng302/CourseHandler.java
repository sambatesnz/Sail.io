//package seng302;
//
//import javafx.scene.paint.Color;
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Class to parse the race xml file and extract the relative boats, marks and boundaries
// */
//public class CourseHandler extends DefaultHandler {
//    private List<Gate> gates;
//    private List<Leg> legs;
//    private List<Position> boundaries;
//    private Map<String, Landmark> landmarks;
//    private Landmark currentLandmark;
//    private Gate currentGate;
//    private Position currentBoundary;
//    private Landmark legStart;
//    private Landmark legEnd;
//    private StringBuilder content;
//    private boolean inBoundary;
//
//    /**
//     * Constructs the class readying it for use
//     */
//    public CourseHandler() {
//        content = new StringBuilder();
//        inBoundary = false;
//    }
//
//    /**
//     * Get the course boundaries
//     * @return the course boundaries
//     */
//    public List<Position> getBoundaries() {
//        return boundaries;
//    }
//
//    /**
//     * Get the gates
//     * @return the gates
//     */
//    public List<Gate> getGates() {
//        return gates;
//    }
//
//    /**
//     * Get the legs
//     * @return the legs
//     */
//    public List<Leg> getLegs() {
//        return legs;
//    }
//
//    /**
//     * Get the landmarks
//     * @return the landmarks
//     */
//    public Map<String, Landmark> getLandmarks() {
//        return landmarks;
//    }
//
//    /**
//     * Begins parsing of an element
//     * @param uri The empty string since the element has no Namespace URI or the Namespace processing is not being performed
//     * @param localName The empty string since Namespace processing is not being performed
//     * @param qName The qualified name (with prefix)
//     * @param atts The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object
//     * @throws SAXException Any SAX exception, possibly wrapping another exception
//     */
//    @Override
//    public void startElement(String uri, String localName, String qName,
//                             Attributes atts) throws SAXException {
//        content = new StringBuilder();
//        if(qName.equalsIgnoreCase("course")) {
//            landmarks = new HashMap<>();
//            gates = new ArrayList<>();
//            legs = new ArrayList<>();
//            boundaries = new ArrayList<>();
//        } else if(qName.equalsIgnoreCase("landmark")) {
//            currentLandmark = new Landmark();
//        } else if(qName.equalsIgnoreCase("gate")) {
//            currentGate = new Gate();
//        } else if(qName.equalsIgnoreCase("boundary")) {
//            inBoundary = true;
//            currentBoundary = new Position(0.0, 0.0);
//        }
//    }
//
//    /**
//     * Ends parsing of an element
//     * @param uri The empty string since the element has no Namespace URI or the Namespace processing is not being performed
//     * @param localName The empty string since Namespace processing is not being performed
//     * @param qName The qualified name (with prefix)
//     * @throws SAXException Any SAX exception, possibly wrapping another exception
//     */
//    @Override
//    public void endElement(String uri, String localName, String qName)
//            throws SAXException {
//        if(qName.equalsIgnoreCase("name")) {
//            currentLandmark.setName(content.toString());
//        } else if(qName.equalsIgnoreCase("long")) {
//            try {
//                if (inBoundary) {
//                    currentBoundary.setLongitude(Double.valueOf(content.toString()));
//                } else {
//                    currentLandmark.setLong(Double.valueOf(content.toString()));
//                }
//            } catch (NumberFormatException e) {
//                throw new SAXException("content not a number");
//            }
//        } else if(qName.equalsIgnoreCase("lat")) {
//            try {
//                if (inBoundary) {
//                    currentBoundary.setLatitude(Double.valueOf(content.toString()));
//                } else {
//                    currentLandmark.setLat(Double.valueOf(content.toString()));
//                }
//            } catch (NumberFormatException e) {
//                throw new SAXException("content not a number");
//            }
//        } else if(qName.equalsIgnoreCase("colour")) {
//            currentLandmark.setColor(Color.valueOf(content.toString()));
//        } else if(qName.equalsIgnoreCase("landmark")) {
//            landmarks.put(currentLandmark.getName(), currentLandmark);
//        } else if(qName.equalsIgnoreCase("mark1")) {
//            currentGate.setStart(landmarks.get(content.toString()));
//        } else if(qName.equalsIgnoreCase("mark2")) {
//            currentGate.setDest(landmarks.get(content.toString()));
//        } else if(qName.equalsIgnoreCase("landmark")) {
//            gates.add(currentGate);
//        } else if(qName.equalsIgnoreCase("start")) {
//            legStart = landmarks.get(content.toString());
//        } else if(qName.equalsIgnoreCase("end")) {
//            legEnd = landmarks.get(content.toString());
//        } else if(qName.equalsIgnoreCase("leg")) {
//            legs.add(new Leg(legStart, legEnd));
//        }  else if(qName.equalsIgnoreCase("boundary")) {
//            boundaries.add(currentBoundary);
//            inBoundary = false;
//        }  else if(qName.equalsIgnoreCase("gate")) {
//            gates.add(currentGate);
//        }
//    }
//
//    /**
//     * Adds new characters to content, possibly called multiple times per element
//     * @param ch The characters
//     * @param start The start position in the character array
//     * @param length The number of characters to use from the character array
//     * @throws SAXException Any SAX exception, possibly wrapping another exception
//     */
//    @Override
//    public void characters(char[] ch, int start, int length)
//            throws SAXException {
//        content.append(ch, start, length);
//    }
//}