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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
;


public class RaceXMLCreator {

    Race race;
    Document raceXML;

    public Document createDocument() throws IOException {

        Element root = this.raceXML.addElement("Race");

        root.addElement("CreationTimeDate").addText("/TODO");


        root.addElement("RaceStartTime")
                .addAttribute("Start", getDateString())
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

        return this.raceXML;
    }




    private String getDateString() {
        final long ONE_MINUTE_IN_MILLIS=60000;
        TimeZone tz = TimeZone.getTimeZone("NZST");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"); // Quoted "Z" to indicate UTC, no timezone offset
        // df.setTimeZone(tz);
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        return df.format(new Date(t + ONE_MINUTE_IN_MILLIS / 2));
    }



    public RaceXMLCreator(Race race){
        this.race = race;
        this.raceXML = DocumentHelper.createDocument();


    }



}
