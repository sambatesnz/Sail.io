package seng302.Server;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import seng302.Main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class RaceCreator {

    private Document configDoc;
    private String RACEID = "RaceID";
    private String RACETYPE = "RaceType";
    String courseRelativeFileLocation;


    /**
     * Creates an object that we can create files
     * @param raceFileLocation the relative location of the course xml file
     * @throws IOException if xml could not be found
     */
    public RaceCreator(String raceFileLocation) throws IOException {
        this.courseRelativeFileLocation = raceFileLocation;
        this.configDoc = loadRaceXmlFile(raceFileLocation);
    }

    /**
     * Loads the course xml file as a Document with a DOM Structure
     * @param relativeFilePath file path of the course you with to load
     * @return The DOM document which we can now parse
     */
    private Document loadRaceXmlFile(String relativeFilePath) throws IOException {
        String basePath = new File("").getAbsolutePath();
        InputStream fileInputStream = null;
        if (relativeFilePath.length()<1){
            fileInputStream = Main.class.getClassLoader().getResourceAsStream( "course5.xml" );
        }
        else{
            fileInputStream = Main.class.getClassLoader().getResourceAsStream(relativeFilePath);
        }

        OutputStream outputStream = new FileOutputStream(new File("").getAbsolutePath()+ "/temp2.txt");

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        File configFile = new File(new File("").getAbsolutePath()+ "/temp2.txt");


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

    public int getRaceID() {
        NodeList nodes = this.configDoc.getElementsByTagName(RACEID);
        int raceID = 0;

        try
        {
            for (int i=0; i< nodes.getLength(); i++){
                String strippedNode = nodes.item(i).getTextContent().replaceAll("\\s+", "");
                raceID = Integer.parseInt(strippedNode);
            }
        } catch (Exception e) {
            System.out.println("Could not parse the wind speed.");
            throw e;
        }
        return raceID;
    }

    public char getRaceType() {
        NodeList nodes = this.configDoc.getElementsByTagName(RACEID);
        char raceType = '0';

        try
        {
            for (int i=0; i< nodes.getLength(); i++){
                String strippedNode = nodes.item(i).getTextContent().replaceAll("\\s+", "");
                raceType = strippedNode.charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Could not parse the wind speed.");
            throw e;
        }
        return (char) (raceType + (char)-48);
    }
}
