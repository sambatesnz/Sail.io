package seng302.objects;


import com.sun.deploy.security.ValidationState;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class CourseCreator {

    private Document configDoc;
    private String LATITUDE = "latitude";
    private String LONGITUDE = "longitude";
    private String COMPOUND_MARK = "compoundMark";
    private String MARK_TYPE = "type";
    private String MARK_NAME = "name";
    private String MARK_ID = "id";
    private String MARK = "Mark";
    private String GATE = "Gate";
    private String GATE_ORDER = "gateOrder";
    String courseRelativeFileLocation;


    /**
     * Creates an object that we can create files
     * @param courseFileLocation the relative location of the course xml file
     */
    public CourseCreator(String courseFileLocation){
        this.courseRelativeFileLocation = courseFileLocation;
        this.configDoc = loadCourseXmlFile(courseFileLocation);
    }

    /**
     * Loads the course xml file as a Document with a DOM Structure
     * @param relativeFilePath file path of the course you with to load
     * @return The DOM document which we can now parse
     */
    private Document loadCourseXmlFile(String relativeFilePath) {
        String basePath = new File("").getAbsolutePath();
        File configFile = new File(basePath + relativeFilePath);


        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(configFile);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new Error(e);
        }
        return doc;
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
    public ArrayList<Integer> getGateOrderForRace(){
        NodeList nodes = this.configDoc.getElementsByTagName(GATE_ORDER);
        Element gateorder = (Element) nodes.item(0);
        String gateOrders = gateorder.getTextContent();

        String formattedGateOrders = gateOrders.replaceAll("\\s+", "");
        List<String> gateOrdersAsStrings = Arrays.asList(formattedGateOrders.split(","));

        ArrayList<Integer> courseOrder = new ArrayList<>(); //Ollie was here, but he has now changed as a person.
        for (String s: gateOrdersAsStrings){
            courseOrder.add(Integer.parseInt(s));
        }
        return courseOrder;
    }

    /**
     * Gets an array list of compounds from the xml file
     * @return An array list of compound marks
     */
    public ArrayList<CompoundMark> getCompoundMarks(){

        NodeList nodes = this.configDoc.getElementsByTagName(COMPOUND_MARK);
        ArrayList<CompoundMark> compoundMarks = new ArrayList<>();

        for (int i=0; i< nodes.getLength(); i++){
            Node node = nodes.item(i);

            Element markElement = (Element) node;

            CompoundMark mark = createMarkFromElement(markElement);
            compoundMarks.add(mark);

        }
        return compoundMarks;
    }

    /** Takes a xml mark and converts it into compound mark
     * @param markElement the element you wish to convert to a mark
     * @return CompoundMark
     */
    private CompoundMark createMarkFromElement(Element markElement) {
        String name = markElement.getAttribute(MARK_NAME);
        String type = markElement.getAttribute(MARK_TYPE);
        int id = Integer.parseInt(markElement.getAttribute(MARK_ID));
        double latitude;
        double longitude;
        CompoundMark mark = new CompoundMark(name, id);

        //We know a mark is always going to have a lat and long
        latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE));
        longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE));

        mark.addMark(latitude, longitude);
        if (type.equals(GATE)){
            latitude = Double.parseDouble(getContentFromElement(markElement, LATITUDE, 1));
            longitude = Double.parseDouble(getContentFromElement(markElement, LONGITUDE, 1));
            mark.addMark(latitude, longitude);
        }
        return mark;
    }
}
