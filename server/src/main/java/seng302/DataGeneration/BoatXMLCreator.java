package seng302.DataGeneration;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import seng302.RaceObjects.Boat;
import seng302.XMLCreation.XMLCreator;

import java.io.IOException;
import java.util.List;

public class BoatXMLCreator implements XMLCreator {

    private Document xml;

    public BoatXMLCreator(List<Boat> boats) {
        this.xml = createDocument(boats);
    }

    private Document createDocument(List<Boat> boats) {
        Document boatXML = DocumentHelper.createDocument();
        Element root = boatXML.addElement("BoatConfig");

        root.addElement("Modified").addText("/TODO");

        Element boatsElement = root.addElement("Boats");

        for (Boat boat: boats){
            boatsElement.addElement("Boat")
                    .addAttribute("Type", "Yacht")
                    .addAttribute("SourceID", String.valueOf(boat.getSourceId()))
                    .addAttribute("ShortName", boat.getShortName())
                    .addAttribute("BoatName", boat.getBoatName())
                    .addAttribute("Country", boat.getCountry());
        }

        return boatXML;
    }

    @Override
    public String getXML() throws IOException {
        return xml.asXML();
    }

}
