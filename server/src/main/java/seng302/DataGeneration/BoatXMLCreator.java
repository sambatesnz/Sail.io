package seng302.DataGeneration;

import seng302.RaceObjects.GenericBoat;
import seng302.XMLCreation.XMLCreator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.io.IOException;
import java.util.List;

public class BoatXMLCreator implements XMLCreator {

    private Document xml;

    public BoatXMLCreator(List<GenericBoat> boats) {
        this.xml = createDocument(boats);
    }

    private Document createDocument(List<GenericBoat> boats) {
        Document boatXML = DocumentHelper.createDocument();
        Element root = boatXML.addElement("BoatConfig");

        root.addElement("Modified").addText("/TODO");

        Element boatsElement = root.addElement("Boats");

        for (GenericBoat boat: boats){
            boatsElement.addElement("Boat")
                    .addAttribute("Type", "Yacht")
                    .addAttribute("SourceID", String.valueOf(boat.getSourceId()))
                    .addAttribute("ShortName", boat.getShortName())
                    .addAttribute("BoatName", boat.getBoatName().getValue())
                    .addAttribute("Country", boat.getCountry().getValue());
        }

        return boatXML;
    }

    @Override
    public String getXML() throws IOException {
        return xml.asXML();
    }

}
