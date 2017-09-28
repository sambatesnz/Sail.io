package seng302;

import org.junit.Test;
import seng302.PacketParsing.XMLParser;
import seng302.RaceObjects.AgarBoat;
import seng302.RaceObjects.GenericBoat;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the LocationSpawner class
 */
public class LocationSpawnerTest {

    @Test
    public void LocationSpawnTest() throws Exception {
        boolean expectedCollision = false;
        BoatGenerator boatGenerator = new BoatGenerator();
        GenericBoat boat = new AgarBoat(boatGenerator.generateBoat());

        ArrayList<GenericBoat> boats = new ArrayList<>(Arrays.asList(boat));
        CollisionDetector detector = new CollisionDetector();
        XMLParser parser = new XMLParser(getRaceXml());

        LocationSpawner.generateSpawnPoints(boats, parser.getCourseLimits(), parser.getCourseLimits(), detector, null);
        boolean actualCollision = detector.hasCollision(boat, parser.getCourseLimits(), boats, null);
        assertEquals(expectedCollision, actualCollision);
    }

    private String getRaceXml() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Race>\n" +
                "    <CreationTimeDate>2015-08-29T13:12:40+02:00</CreationTimeDate>\n" +
                "    <RaceStartTime Start=\"2015-08-29T13:10:00+02:00\" Postpone=\"False\" />\n" +
                "    <RaceID>15082901</RaceID>\n" +
                "    <RaceType>Fleet</RaceType>\n" +
                "    <Participants>\n" +
                "        <!--<Yacht SourceID=\"101\" />-->\n" +
                "        <!--<Yacht SourceID=\"102\"/>-->\n" +
                "        <!--<Yacht SourceID=\"103\" />-->\n" +
                "        <!--<Yacht SourceID=\"104\" />-->\n" +
                "        <!--<Yacht SourceID=\"105\" />-->\n" +
                "        <!--<Yacht SourceID=\"106\" />-->\n" +
                "    </Participants>\n" +
                "    <Course>\n" +
                "        <CompoundMark CompoundMarkID=\"1\" Name=\"Mark0\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Start Line 1\" TargetLat=\"57.6703330\" TargetLng=\"11.8289330\" SourceID=\"122\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Start Line 2\" TargetLat=\"57.6703330\" TargetLng=\"11.8261330\" SourceID=\"123\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"2\" Name=\"Mark1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Mark1\" TargetLat=\"57.6675700\" TargetLng=\"11.8359880\" SourceID=\"131\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"3\" Name=\"Mark2\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"57.6709220\" TargetLng=\"11.8433900\" SourceID=\"124\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"57.6700220\" TargetLng=\"11.8437900\" SourceID=\"125\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"4\" Name=\"Mark3\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Wind Gate 1\" TargetLat=\"57.6660170\" TargetLng=\"11.8279170\" SourceID=\"126\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"57.6650170\" TargetLng=\"11.8279170\" SourceID=\"127\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"5\" Name=\"Mark4\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Finish Line 1\" TargetLat=\"57.6711240\" TargetLng=\"11.8444950\" SourceID=\"128\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Finish Line 2\" TargetLat=\"57.6720240\" TargetLng=\"11.8444950\" SourceID=\"129\" />\n" +
                "        </CompoundMark>\n" +
                "    </Course>\n" +
                "    <CompoundMarkSequence>\n" +
                "        <Corner SeqID=\"1\" CompoundMarkID=\"1\" Rounding=\"PS\" ZoneSize=\"3\" />\n" +
                "        <!--<Corner SeqID=\"2\" CompoundMarkID=\"2\" Rounding=\"Port\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"3\" CompoundMarkID=\"3\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"4\" CompoundMarkID=\"4\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"5\" CompoundMarkID=\"3\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"6\" CompoundMarkID=\"4\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"7\" CompoundMarkID=\"3\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"8\" CompoundMarkID=\"4\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"9\" CompoundMarkID=\"3\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"10\" CompoundMarkID=\"4\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"11\" CompoundMarkID=\"5\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "    </CompoundMarkSequence>\n" +
                "    <CourseLimit>\n" +
                "        <Limit SeqID=\"1\" Lat=\"57.6739450\" Lon=\"11.8417100\" />\n" +
                "        <Limit SeqID=\"2\" Lat=\"57.6709520\" Lon=\"11.8485010\" />\n" +
                "        <Limit SeqID=\"3\" Lat=\"57.6690260\" Lon=\"11.8472790\" />\n" +
                "        <Limit SeqID=\"4\" Lat=\"57.6693140\" Lon=\"11.8457610\" />\n" +
                "        <Limit SeqID=\"5\" Lat=\"57.6665370\" Lon=\"11.8432910\" />\n" +
                "        <Limit SeqID=\"6\" Lat=\"57.6641400\" Lon=\"11.8385840\" />\n" +
                "        <Limit SeqID=\"7\" Lat=\"57.6629430\" Lon=\"11.8332030\" />\n" +
                "        <Limit SeqID=\"8\" Lat=\"57.6629480\" Lon=\"11.8249660\" />\n" +
                "        <Limit SeqID=\"9\" Lat=\"57.6709450\" Lon=\"11.8250920\" />\n" +
                "        <Limit SeqID=\"10\" Lat=\"57.6732450\" Lon=\"11.8250920\" />\n" +
                "        <Limit SeqID=\"11\" Lat=\"57.6732450\" Lon=\"11.8321340\" />\n" +
                "        <Limit SeqID=\"12\" Lat=\"57.6709450\" Lon=\"11.8321340\" />\n" +
                "    </CourseLimit>\n" +
                "</Race>\n" +
                "\n" +
                "        <!--<?xml version=\"1.0\" encoding=\"utf-8\"?>-->\n" +
                "        <!--<Race>-->\n" +
                "        <!--<CreationTimeDate>2017-05-16T16:00:45-03:00</CreationTimeDate>-->\n" +
                "        <!--<RaceStartTime Start=\"2017-05-16T16:00:00-03:00\" Postpone=\"False\" />-->\n" +
                "        <!--<RaceID>17051610</RaceID>-->\n" +
                "        <!--<RaceType>Match</RaceType>-->\n" +
                "        <!--<Participants>-->\n" +
                "        <!--<Yacht SourceID=\"103\" Entry=\"Port\" />-->\n" +
                "        <!--<Yacht SourceID=\"105\" Entry=\"Stbd\" />-->\n" +
                "        <!--</Participants>-->\n" +
                "        <!--<Course>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"1\" Name=\"Entry\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"Entry Mark\" TargetLat=\"32.3124610\" TargetLng=\"-64.8371090\" SourceID=\"130\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"2\" Name=\"SL1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"Start Line 1\" TargetLat=\"32.3109510\" TargetLng=\"-64.8389420\" SourceID=\"122\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Start Line 2\" TargetLat=\"32.3098420\" TargetLng=\"-64.8375940\" SourceID=\"123\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"3\" Name=\"M1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"Mark 1\" TargetLat=\"32.3049220\" TargetLng=\"-64.8427680\" SourceID=\"131\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"4\" Name=\"LG1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"32.2979900\" TargetLng=\"-64.8309020\" SourceID=\"124\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"32.2968590\" TargetLng=\"-64.8320160\" SourceID=\"125\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"5\" Name=\"WG1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"WG1\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"126\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"32.3062510\" TargetLng=\"-64.8458040\" SourceID=\"127\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"6\" Name=\"LG1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"32.2979900\" TargetLng=\"-64.8309020\" SourceID=\"124\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"32.2968590\" TargetLng=\"-64.8320160\" SourceID=\"125\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"7\" Name=\"WG1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"WG1\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"126\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"32.3062510\" TargetLng=\"-64.8458040\" SourceID=\"127\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"8\" Name=\"LG1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"32.2979900\" TargetLng=\"-64.8309020\" SourceID=\"124\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"32.2968590\" TargetLng=\"-64.8320160\" SourceID=\"125\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--<CompoundMark CompoundMarkID=\"9\" Name=\"WG1\">-->\n" +
                "        <!--<Mark SeqID=\"1\" Name=\"WG1\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"126\" />-->\n" +
                "        <!--<Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"127\" />-->\n" +
                "        <!--</CompoundMark>-->\n" +
                "        <!--</Course>-->\n" +
                "        <!--<CompoundMarkSequence>-->\n" +
                "        <!--<Corner SeqID=\"1\" CompoundMarkID=\"1\" Rounding=\"Port\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"2\" CompoundMarkID=\"2\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"3\" CompoundMarkID=\"3\" Rounding=\"Port\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"4\" CompoundMarkID=\"4\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"5\" CompoundMarkID=\"5\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"6\" CompoundMarkID=\"6\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"7\" CompoundMarkID=\"7\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"8\" CompoundMarkID=\"8\" Rounding=\"PS\" ZoneSize=\"3\" />-->\n" +
                "        <!--<Corner SeqID=\"9\" CompoundMarkID=\"9\" Rounding=\"SP\" ZoneSize=\"3\" />-->\n" +
                "        <!--</CompoundMarkSequence>-->\n" +
                "        <!--<CourseLimit name=\"Boundary\" draw=\"1\" avoid=\"1\" fill=\"1\" lock=\"0\" colour=\"000000FF\" notes=\"5,45\">-->\n" +
                "        <!--<Limit SeqID=\"1\" Lat=\"32.3090300\" Lon=\"-64.8466850\" />-->\n" +
                "        <!--<Limit SeqID=\"2\" Lat=\"32.3121800\" Lon=\"-64.8424450\" />-->\n" +
                "        <!--<Limit SeqID=\"3\" Lat=\"32.3000020\" Lon=\"-64.8270390\" />-->\n" +
                "        <!--<Limit SeqID=\"4\" Lat=\"32.2962360\" Lon=\"-64.8299680\" />-->\n" +
                "        <!--<Limit SeqID=\"5\" Lat=\"32.2934570\" Lon=\"-64.8338660\" />-->\n" +
                "        <!--<Limit SeqID=\"6\" Lat=\"32.3054160\" Lon=\"-64.8498380\" />-->\n" +
                "        <!--</CourseLimit>-->\n" +
                "        <!--</Race>-->";
    }
}