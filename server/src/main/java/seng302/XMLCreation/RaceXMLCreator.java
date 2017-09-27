package seng302.XMLCreation;

import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import seng302.Modes.Race;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Mark;
import seng302.Rounding;

import java.io.IOException;


/**
 * Creates race xml dynamically based on the state of the race passed in
 */
public class RaceXMLCreator implements XMLCreator {


    private Document xml;
    private Race race;
    private Document raceXML;


    public RaceXMLCreator(Race race) throws IOException {
        this.race = race;
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


        for (GenericBoat b : race.getBoats()) {
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
        if (race.getCompoundMarks().size() > 0) {
            for (Pair<CompoundMark, Rounding> pair: race.getCourseRoundingInfo()) {
                CompoundMark compoundMark = pair.getKey();
                String rounding = pair.getValue().getRounding();

                compoundMarkSequence.addElement("Corner")
                        .addAttribute("SeqID", String.valueOf(seqId))
                        .addAttribute("CompoundMarkID", String.valueOf(compoundMark.getId()))
                        .addAttribute("Rounding", rounding)
                        .addAttribute("ZoneSize", "/TODO");

                seqId++;
            }
        }

//        seqId = 1;
//        for (CompoundMark mark : race.getCompoundMarks()) {
//            System.out.println(mark.getRounding());
//            compoundMarkSequence.addElement("Corner")
//                    .addAttribute("SeqID", String.valueOf(seqId))
//                    .addAttribute("CompoundMarkID", String.valueOf(mark.getId()))
//                    .addAttribute("Rounding", mark.getRounding())
//                    .addAttribute("ZoneSize", "/TODO");
//            seqId++;
//        }

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
