package seng302.XMLCreation;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.Mark;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;


public class RaceXMLCreator {

    Race race;
    Document raceXML;

    public RaceXMLCreator(Race race){
        this.race = race;
        this.raceXML = DocumentHelper.createDocument();

    }

    public Document createDocument() throws IOException {

        Element root = this.raceXML.addElement("Race");

        root.addElement("CreationTimeDate").addText("/TODO");



        root.addElement("RaceStartTime")
                .addAttribute("Start", "2015-08-29T13:10:00+02:00") //TODO Generate time properly
                .addAttribute("Postpone", "False");

        root.addElement("RaceID")
                .addText("12345");

        root.addElement("RaceType")
                .addText("Fleet");

        Element participants = root.addElement("Participants");

        for (Boat b: race.getBoats()){
            participants.addElement("Yacht")
                    .addAttribute("SourceID", String.valueOf(b.getSourceId()));
        }

        Element course = root.addElement("Course");

        for(CompoundMark mark: race.getCompoundMarks()){
            Element compoundMark = course.addElement("CompoundMark")
                    .addAttribute("CompoundMarkID", String.valueOf(mark.getId()))
                    .addAttribute("Name", mark.getName());

            int seqid = 1;
            for(Mark singleMark: mark.getMarks()){
                compoundMark.addElement("Mark")
                        .addAttribute("SeqID", String.valueOf(seqid))
                        .addAttribute("Name", "/TODO")
                        .addAttribute("TargetLat", String.valueOf(singleMark.getLatitude()))
                        .addAttribute("TargetLng", String.valueOf(singleMark.getLongitude()))
                        .addAttribute("SourceID", String.valueOf(singleMark.getSourceId()));
                seqid ++;

            }
        }

        Element compoundMarkSequence = root.addElement("CompoundMarkSequence");

        int seqId = 1;
        for(CompoundMark mark: race.getCompoundMarks()) {
            compoundMarkSequence.addElement("Corner")
                    .addAttribute("SeqID", String.valueOf(seqId))
                    .addAttribute("CompoundMarkID", String.valueOf(mark.getId()))
                    .addAttribute("Rounding", "/TODO")
                    .addAttribute("ZoneSize", "/TODO");
            seqId ++;
        }

        Element courseLimit = root.addElement("CourseLimit");


        seqId = 1;
        for(CourseLimit mark: race.getBoundaries()) {
            courseLimit.addElement("Limit")
                    .addAttribute("SeqID", String.valueOf(seqId))
                    .addAttribute("Lat", String.valueOf(mark.getLat()))
                    .addAttribute("Lon", String.valueOf(mark.getLon()));
            seqId ++;
        }










//        this.raceXML.addElement("Race");

        //addRoot();
        /*Element root = document.addElement("Race");

        Element author1 = root.addElement("author")
                .addAttribute("name", "James")
                .addAttribute("location", "UK")
                .addText("James Strachan");

        Element author2 = root.addElement("author")
                .addAttribute("name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");


        FileWriter out = new FileWriter("foo.xml");
        document.write(out);*/

        return this.raceXML;
    }



}
