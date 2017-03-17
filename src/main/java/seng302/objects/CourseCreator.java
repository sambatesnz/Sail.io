package seng302.objects;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CourseCreator {

    private Document configDoc;
    private String LATITUDE = "latitude";
    private String LONGITUDE = "longitude";
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

    private String getContentFromElement(Element element, String name){
        return element.getElementsByTagName(name).item(0).getTextContent();
    }

    ArrayList<CompoundMark> getMarks(){

        NodeList nodes = this.configDoc.getElementsByTagName("mark");
        ArrayList<CompoundMark> compoundMarks = new ArrayList<>();

        for (int i=0; i< nodes.getLength(); i++){
            Node currentNode = nodes.item(i);
            if (currentNode.getNodeName().equals("mark")) {
                Element currentMark = (Element) currentNode;
                String latitude = getContentFromElement(currentMark, LATITUDE);
                String longitude = getContentFromElement(currentMark, LONGITUDE);
                float convertedLat = Float.parseFloat(latitude);
                float convertedLong = Float.parseFloat(longitude);
                //CompoundMark mark = new CompoundMark(convertedLat, convertedLong);
                //compoundMarks.add(mark);
            }
        }
        return compoundMarks;
    }

    String doStuff(){
        System.out.println("nice !");
        return "WOW ";
    }
}
