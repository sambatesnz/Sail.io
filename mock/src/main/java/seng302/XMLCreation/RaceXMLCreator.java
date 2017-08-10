package seng302.XMLCreation;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.Mark;

import java.io.IOException;


/**
 * Creates race xml dynamically based on the state of the race passed in
 */
public class RaceXMLCreator implements XMLCreator {


    private Document xml;


    public RaceXMLCreator(Race race) throws IOException {
        this.xml = createDocument(race);
    }

    @Override
    public String getXML() throws IOException {
        return xml.asXML();
    }

    /**
     * Creates a Document of an xml race
     */
    private Document createDocument(Race race) throws IOException {

        Document raceXML = DocumentHelper.createDocument();

        Element root = raceXML.addElement("Race");

        root.addElement("CreationTimeDate").addText("/TODO");


        root.addElement("RaceStartTime")
                .addAttribute("Start", race.getDateString())
                .addAttribute("Postpone", "False");

        root.addElement("RaceID")
                .addText("12345");

        root.addElement("RaceType")
                .addText("Fleet");

        Element participants = root.addElement("Participants");


        for (Boat b : race.getBoats()) {
            participants.addElement("Yacht")
                    .addAttribute("SourceID", String.valueOf(b.getSourceId()));
        }

        Element course = root.addElement("Course");

        for (CompoundMark mark : race.getCompoundMarks()) {
            Element compoundMark = course.addElement("CompoundMark")
                    .addAttribute("CompoundMarkID", String.valueOf(mark.getId()))
                    .addAttribute("Name", mark.getName());

            int seqid = 1;
            for (Mark singleMark : mark.getMarks()) {
                compoundMark.addElement("Mark")
                        .addAttribute("SeqID", String.valueOf(seqid))
                        .addAttribute("Name", "/TODO")
                        .addAttribute("TargetLat", String.valueOf(singleMark.getLatitude()))
                        .addAttribute("TargetLng", String.valueOf(singleMark.getLongitude()))
                        .addAttribute("SourceID", String.valueOf(singleMark.getSourceId()));
                seqid++;

            }
        }

        Element compoundMarkSequence = root.addElement("CompoundMarkSequence");

        int seqId = 1;
        for (CompoundMark mark : race.getCompoundMarks()) {
            compoundMarkSequence.addElement("Corner")
                    .addAttribute("SeqID", String.valueOf(seqId))
                    .addAttribute("CompoundMarkID", String.valueOf(mark.getId()))
                    .addAttribute("Rounding", "/TODO")
                    .addAttribute("ZoneSize", "/TODO");
            seqId++;
        }

        Element courseLimit = root.addElement("CourseLimit");


        seqId = 1;
        for (CourseLimit mark : race.getBoundaries()) {
            courseLimit.addElement("Limit")
                    .addAttribute("SeqID", String.valueOf(seqId))
                    .addAttribute("Lat", String.valueOf(mark.getLat()))
                    .addAttribute("Lon", String.valueOf(mark.getLon()));
            seqId++;
        }

        return raceXML;
    }


}
