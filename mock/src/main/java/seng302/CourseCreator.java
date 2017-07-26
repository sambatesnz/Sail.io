//package seng302;
//
//
//import javafx.scene.paint.Color;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//import seng302.RaceObjects.CompoundMark;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class CourseCreator {
//
//    private Document configDoc;
//    private String WINDDIRECTION = "windDirection";
//    private String WINDSPEED = "windSpeed";
//    private String LATITUDE = "latitude";
//    private String LONGITUDE = "longitude";
//    private String COMPOUND_MARK = "compoundMark";
//    private String MARK_TYPE = "type";
//    private String MARK_NAME = "name";
//    private String MARK_COLOR = "color";
//    private String MARK_ID = "id";
//    private String MARK = "Mark";
//    private String GATE = "Gate";
//    private String GATE_ORDER = "gateOrder";
//    private String BOUNDARY = "boundary";
//    String courseRelativeFileLocation;
//
//
//    /**
//     * Creates an object that we can create files
//     * @param courseFileLocation the relative location of the course xml file
//     */
//    public CourseCreator(String courseFileLocation) throws IOException {
//        this.courseRelativeFileLocation = courseFileLocation;
//        this.configDoc = loadCourseXmlFile(courseFileLocation);
//    }
//
//    /**
//     * Loads the course xml file as a Document with a DOM Structure
//     * @param relativeFilePath file path of the course you with to load
//     * @return The DOM document which we can now parse
//     */
//    private Document loadCourseXmlFile(String relativeFilePath) throws IOException {
//        String basePath = new File("").getAbsolutePath();
//        InputStream fileInputStream = null;
//        if (relativeFilePath.length()<1){
//            fileInputStream = Main.class.getClassLoader().getResourceAsStream( "course.xml" );
//        }
//        else{
//            fileInputStream = Main.class.getClassLoader().getResourceAsStream(relativeFilePath);
//        }
//
//        OutputStream outputStream = new FileOutputStream(new File("").getAbsolutePath()+ "/temp.txt");
//
//        int read = 0;
//        byte[] bytes = new byte[1024];
//
//        while ((read = fileInputStream.read(bytes)) != -1) {
//            outputStream.write(bytes, 0, read);
//        }
//
//        File configFile = new File(new File("").getAbsolutePath()+ "/temp.txt");
//
//
//        Document doc = null;
//        try {
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            doc = dBuilder.parse(configFile);
//            doc.getDocumentElement().normalize();
//        } catch (ParserConfigurationException | SAXException | IOException e) {
//            throw new Error(e);
//        }
//        return doc;
//    }
//
//    /**
//     * Gets the textual value from a single xml element
//     * @param element the element you wish to find the value for
//     * @param name the name of the element
//     * @return A string containing the textual value contained inside an xml element
//     */
//    private String getContentFromElement(Element element, String name){
//        return element.getElementsByTagName(name).item(0).getTextContent();
//    }
//
//    /**
//     * Overloaded function
//     * @param element
//     * @param name
//     * @param order If you have multiple tags, you can define the order you want
//     * @return
//     */
//    private String getContentFromElement(Element element, String name, int order){
//        return element.getElementsByTagName(name).item(order).getTextContent();
//    }
//
//    /**
//     * Gets the gate order defined from the xml
//     * @return Arraylist containing the gate order
//     */
//    public ArrayList<Integer> getGateOrderForRace(){
//
//        NodeList nodes = this.configDoc.getElementsByTagName(GATE_ORDER);
//        String gateOrders = null;
//        ArrayList<Integer> courseOrder;
//
//        try {
//            Element gateorder = (Element) nodes.item(0);
//            gateOrders = gateorder.getTextContent();
//            String formattedGateOrders = gateOrders.replaceAll("\\s+", "");
//            List<String> gateOrdersAsStrings = Arrays.asList(formattedGateOrders.split(","));
//            courseOrder = new ArrayList<>(); //Ollie was here, but he has now changed as a person.
//            for (String s: gateOrdersAsStrings){
//                courseOrder.add(Integer.parseInt(s));
//            }
//        } catch (NullPointerException unluggy) {
//            throw new Error("Gate order tag not found!");
//        }
//        return courseOrder;
//    }
//
//    public short getWindDirection() {
//        NodeList nodes = this.configDoc.getElementsByTagName(WINDDIRECTION);
//        short windDir = 0;
//
//        try
//        {
//            for (int i=0; i< nodes.getLength(); i++){
//                String strippedNode = nodes.item(i).getTextContent().replaceAll("\\s+", "");
//                windDir = Short.parseShort(strippedNode);
//            }
//        } catch (Exception e) {
//            System.out.println("Could not parse the wind direction.");
//            throw e;
//        }
//        return windDir;
//    }
//
//    public short getWindSpeed() {
//        NodeList nodes = this.configDoc.getElementsByTagName(WINDSPEED);
//        short windSpeed = 0;
//
//        try
//        {
//            for (int i=0; i< nodes.getLength(); i++){
//                String strippedNode = nodes.item(i).getTextContent().replaceAll("\\s+", "");
//                windSpeed = Short.parseShort(strippedNode);
//            }
//        } catch (Exception e) {
//            System.out.println("Could not parse the wind speed.");
//            throw e;
//        }
//        return windSpeed;
//    }
//
//    private CompoundMark createLandmarkFromElement(Element markElement) {
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
//        return new Com(name, positions, color, id, type);
//    }
//
//    public ArrayList<CompoundMark> getLandmarks() {
//        NodeList nodes = this.configDoc.getElementsByTagName(COMPOUND_MARK);
//        ArrayList<CompoundMark> compoundMarks = new ArrayList<>();
//
//        for (int i=0; i< nodes.getLength(); i++){
//            Node node = nodes.item(i);
//            Element markElement = (Element) node;
//            CompoundMark mark = createLandmarkFromElement(markElement);
//            compoundMarks.add(mark);
//        }
//        return compoundMarks;
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
//}
