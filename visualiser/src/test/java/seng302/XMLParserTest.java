package seng302;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.PacketParsing.XMLParser;
import seng302.RaceObjects.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Test for the XML Parser
 */
public class XMLParserTest {
    private XMLParser raceParser;
    private XMLParser boatParser;
    private XMLParser regattaParser;

    @Before
    public void setup() throws IOException {
        raceParser = new XMLParser(getRaceXML());
        boatParser = new XMLParser(getBoatsXML());
        regattaParser = new XMLParser(getRegattaXML());
    }

    @Test
    public void checkCourseLimitsCorrect() throws IOException {
        List<CourseLimit> cls = raceParser.getCourseLimits();
        Assert.assertTrue(cls.size() == 6);
    }

    @Test
    public void checkCourseLayoutsCorrect() throws IOException {
        List<CompoundMark> compoundMarks = raceParser.getCourseLayout();
        Assert.assertTrue(compoundMarks.size() == 9);
    }

    @Test
    public void checkCourseOrderCorrect() throws IOException {
        List<Leg> courseOrder = raceParser.getCourseOrder();
        Assert.assertTrue(courseOrder.size() == 9);
    }

    @Test
    public void checkBoats() throws IOException {
        Map<Integer, BoatInterface> boats = boatParser.getBoats(RaceMode.RACE);
        Assert.assertTrue(boats.size() == 6);
    }

    @Test
    public void checkRegatta() throws IOException {
        Regatta reg = regattaParser.getRegatta();
        Assert.assertTrue(reg.getUtcOffset() == -3);
    }

    @Test
    public void checkParticipants() throws IOException {
        List<Integer> participants = raceParser.getRaceParticipants();
        Assert.assertTrue(participants.size() == 2);
    }

    @Test
    public void checkStartTime() throws IOException {
        LocalDateTime startTime = raceParser.getRaceStartTime();
        LocalDateTime trial = LocalDateTime.of(2012, 12, 31, 11, 59);
        Assert.assertTrue(startTime.isAfter(trial));
    }

    private String getRaceXML() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Race>\n" +
                "    <CreationTimeDate>2017-05-16T16:00:45-03:00</CreationTimeDate>\n" +
                "    <RaceStartTime Start=\"2017-05-16T16:00:00-03:00\" Postpone=\"False\" />\n" +
                "    <RaceID>17051610</RaceID>\n" +
                "    <RaceType>Match</RaceType>\n" +
                "    <Participants>\n" +
                "        <Yacht SourceID=\"103\" Entry=\"Port\" />\n" +
                "        <Yacht SourceID=\"105\" Entry=\"Stbd\" />\n" +
                "    </Participants>\n" +
                "    <Course>\n" +
                "        <CompoundMark CompoundMarkID=\"1\" Name=\"Entry\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Entry Mark\" TargetLat=\"32.3124610\" TargetLng=\"-64.8371090\" SourceID=\"130\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"2\" Name=\"SL1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Start Line 1\" TargetLat=\"32.3109510\" TargetLng=\"-64.8389420\" SourceID=\"122\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Start Line 2\" TargetLat=\"32.3098420\" TargetLng=\"-64.8375940\" SourceID=\"123\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"3\" Name=\"M1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Mark 1\" TargetLat=\"32.3049220\" TargetLng=\"-64.8427680\" SourceID=\"131\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"4\" Name=\"LG1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"32.2979900\" TargetLng=\"-64.8309020\" SourceID=\"124\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"32.2968590\" TargetLng=\"-64.8320160\" SourceID=\"125\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"5\" Name=\"WG1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"WG1\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"126\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"32.3062510\" TargetLng=\"-64.8458040\" SourceID=\"127\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"6\" Name=\"LG1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"32.2979900\" TargetLng=\"-64.8309020\" SourceID=\"124\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"32.2968590\" TargetLng=\"-64.8320160\" SourceID=\"125\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"7\" Name=\"WG1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"WG1\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"126\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"32.3062510\" TargetLng=\"-64.8458040\" SourceID=\"127\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"8\" Name=\"LG1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"Lee Gate 1\" TargetLat=\"32.2979900\" TargetLng=\"-64.8309020\" SourceID=\"124\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Lee Gate 2\" TargetLat=\"32.2968590\" TargetLng=\"-64.8320160\" SourceID=\"125\" />\n" +
                "        </CompoundMark>\n" +
                "        <CompoundMark CompoundMarkID=\"9\" Name=\"WG1\">\n" +
                "            <Mark SeqID=\"1\" Name=\"WG1\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"126\" />\n" +
                "            <Mark SeqID=\"2\" Name=\"Wind Gate 2\" TargetLat=\"32.3074050\" TargetLng=\"-64.8444030\" SourceID=\"127\" />\n" +
                "        </CompoundMark>\n" +
                "    </Course>\n" +
                "    <CompoundMarkSequence>\n" +
                "        <Corner SeqID=\"1\" CompoundMarkID=\"1\" Rounding=\"Port\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"2\" CompoundMarkID=\"2\" Rounding=\"SP\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"3\" CompoundMarkID=\"3\" Rounding=\"Port\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"4\" CompoundMarkID=\"4\" Rounding=\"PS\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"5\" CompoundMarkID=\"5\" Rounding=\"SP\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"6\" CompoundMarkID=\"6\" Rounding=\"PS\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"7\" CompoundMarkID=\"7\" Rounding=\"SP\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"8\" CompoundMarkID=\"8\" Rounding=\"PS\" ZoneSize=\"3\" />\n" +
                "        <Corner SeqID=\"9\" CompoundMarkID=\"9\" Rounding=\"SP\" ZoneSize=\"3\" />\n" +
                "    </CompoundMarkSequence>\n" +
                "    <CourseLimit name=\"Boundary\" draw=\"1\" avoid=\"1\" fill=\"1\" lock=\"0\" colour=\"000000FF\" notes=\"5,45\">\n" +
                "        <Limit SeqID=\"1\" Lat=\"32.3090300\" Lon=\"-64.8466850\" />\n" +
                "        <Limit SeqID=\"2\" Lat=\"32.3121800\" Lon=\"-64.8424450\" />\n" +
                "        <Limit SeqID=\"3\" Lat=\"32.3000020\" Lon=\"-64.8270390\" />\n" +
                "        <Limit SeqID=\"4\" Lat=\"32.2962360\" Lon=\"-64.8299680\" />\n" +
                "        <Limit SeqID=\"5\" Lat=\"32.2934570\" Lon=\"-64.8338660\" />\n" +
                "        <Limit SeqID=\"6\" Lat=\"32.3054160\" Lon=\"-64.8498380\" />\n" +
                "    </CourseLimit>\n" +
                "</Race>";
    }

    public String getBoatsXML() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<BoatConfig>\n" +
                "    <Modified>2017-05-15T14:08:53-0400</Modified>\n" +
                "    <Version>12</Version>\n" +
                "    <Snapshot>279</Snapshot>\n" +
                "    <Settings>\n" +
                "        <RaceBoatType Type=\"AC50\"/>\n" +
                "        <BoatDimension BoatLength=\"15\" HullLength=\"15\"/>\n" +
                "        <ZoneSize MarkZoneSize=\"45\" CourseZoneSize=\"60\"/>\n" +
                "        <ZoneLimits Limit1=\"200\" Limit2=\"100\" Limit3=\"60\" Limit4=\"0\" Limit5=\"-100\"/>\n" +
                "    </Settings>\n" +
                "    <BoatShapes>\n" +
                "        <BoatShape ShapeID=\"0\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"3\" Y=\"25\" X=\"0\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"14\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"0\" X=\"-1\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"0.75\" X=\"-1\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"0.75\" X=\"-0.25\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"3.5\" X=\"-0.25\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"4.5\" X=\"-1\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"6.5\" X=\"-1\"/>\n" +
                "                <Vtx Seq=\"7\" Y=\"7\" X=\"-0.5\"/>\n" +
                "                <Vtx Seq=\"8\" Y=\"7\" X=\"0.5\"/>\n" +
                "                <Vtx Seq=\"9\" Y=\"6.5\" X=\"1\"/>\n" +
                "                <Vtx Seq=\"10\" Y=\"4.5\" X=\"1\"/>\n" +
                "                <Vtx Seq=\"11\" Y=\"3.5\" X=\"0.25\"/>\n" +
                "                <Vtx Seq=\"12\" Y=\"0.75\" X=\"0.25\"/>\n" +
                "                <Vtx Seq=\"13\" Y=\"0.75\" X=\"1\"/>\n" +
                "                <Vtx Seq=\"14\" Y=\"0\" X=\"1\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"18\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"0\" X=\"-1.04\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"0.11\" X=\"-1.18\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"0.42\" X=\"-1.28\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"3.74\" X=\"-1.29\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"5.36\" X=\"-1.21\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"6.29\" X=\"-1.08\"/>\n" +
                "                <Vtx Seq=\"7\" Y=\"7.15\" X=\"-0.84\"/>\n" +
                "                <Vtx Seq=\"8\" Y=\"7.63\" X=\"-0.62\"/>\n" +
                "                <Vtx Seq=\"9\" Y=\"7.94\" X=\"-0.34\"/>\n" +
                "                <Vtx Seq=\"10\" Y=\"8.06\" X=\"0\"/>\n" +
                "                <Vtx Seq=\"11\" Y=\"7.94\" X=\"0.34\"/>\n" +
                "                <Vtx Seq=\"12\" Y=\"7.63\" X=\"0.62\"/>\n" +
                "                <Vtx Seq=\"13\" Y=\"7.15\" X=\"0.84\"/>\n" +
                "                <Vtx Seq=\"14\" Y=\"6.29\" X=\"1.08\"/>\n" +
                "                <Vtx Seq=\"15\" Y=\"5.36\" X=\"1.21\"/>\n" +
                "                <Vtx Seq=\"16\" Y=\"3.74\" X=\"1.29\"/>\n" +
                "                <Vtx Seq=\"17\" Y=\"0.42\" X=\"1.28\"/>\n" +
                "                <Vtx Seq=\"18\" Y=\"0.11\" X=\"1.18\"/>\n" +
                "                <Vtx Seq=\"19\" Y=\"0\" X=\"1.04\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"20\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"-0.05\" X=\"-0.05\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"0.05\" X=\"-0.05\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"0.05\" X=\"0.05\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"-0.05\" X=\"0.05\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"24\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"0\" X=\"-2.5\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"7\" X=\"-2.5\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"12.6\" X=\"-2.2\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"12.6\" X=\"2.2\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"7\" X=\"2.5\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"0\" X=\"2.5\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"34\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"0\" X=\"-1.16\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"5.51\" X=\"-1.16\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"5.846\" X=\"-0.84\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"5.846\" X=\"0.84\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"5.51\" X=\"1.16\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"0\" X=\"1.16\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"35\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"0\" X=\"-1.461\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"6\" X=\"-1.461\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"7\" X=\"-1.44\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"8\" X=\"-1.38\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"9\" X=\"-1.17\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"10\" X=\"-0.76\"/>\n" +
                "                <Vtx Seq=\"7\" Y=\"10.6\" X=\"-0.34\"/>\n" +
                "                <Vtx Seq=\"8\" Y=\"10.61\" X=\"0\"/>\n" +
                "                <Vtx Seq=\"9\" Y=\"10.6\" X=\"0.34\"/>\n" +
                "                <Vtx Seq=\"10\" Y=\"10\" X=\"0.76\"/>\n" +
                "                <Vtx Seq=\"11\" Y=\"9\" X=\"1.17\"/>\n" +
                "                <Vtx Seq=\"12\" Y=\"8\" X=\"1.38\"/>\n" +
                "                <Vtx Seq=\"13\" Y=\"7\" X=\"1.44\"/>\n" +
                "                <Vtx Seq=\"14\" Y=\"6\" X=\"1.461\"/>\n" +
                "                <Vtx Seq=\"15\" Y=\"0\" X=\"1.461\"/>\n" +
                "            </Vertices>\n" +
                "        </BoatShape>\n" +
                "        <BoatShape ShapeID=\"40\">\n" +
                "            <Vertices>\n" +
                "                <Vtx Seq=\"1\" Y=\"0\" X=\"-4.004\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"0.42\" X=\"-4.019\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"1.14\" X=\"-4.094\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"1.55\" X=\"-4.172\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"2.034\" X=\"-4.219\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"2.5\" X=\"-4.235\"/>\n" +
                "                <Vtx Seq=\"7\" Y=\"8.138\" X=\"-4.235\"/>\n" +
                "                <Vtx Seq=\"8\" Y=\"8.581\" X=\"-4.205\"/>\n" +
                "                <Vtx Seq=\"9\" Y=\"9.316\" X=\"-4.124\"/>\n" +
                "                <Vtx Seq=\"10\" Y=\"11.813\" X=\"-4.013\"/>\n" +
                "                <Vtx Seq=\"11\" Y=\"13.897\" X=\"-3.89\"/>\n" +
                "                <Vtx Seq=\"12\" Y=\"14.569\" X=\"-3.818\"/>\n" +
                "                <Vtx Seq=\"13\" Y=\"15\" X=\"-3.735\"/>\n" +
                "                <Vtx Seq=\"14\" Y=\"15.001\" X=\"0\"/>\n" +
                "                <Vtx Seq=\"15\" Y=\"15\" X=\"3.735\"/>\n" +
                "                <Vtx Seq=\"16\" Y=\"14.569\" X=\"3.818\"/>\n" +
                "                <Vtx Seq=\"17\" Y=\"13.897\" X=\"3.89\"/>\n" +
                "                <Vtx Seq=\"18\" Y=\"11.813\" X=\"4.013\"/>\n" +
                "                <Vtx Seq=\"19\" Y=\"9.316\" X=\"4.124\"/>\n" +
                "                <Vtx Seq=\"20\" Y=\"8.581\" X=\"4.205\"/>\n" +
                "                <Vtx Seq=\"21\" Y=\"8.138\" X=\"4.235\"/>\n" +
                "                <Vtx Seq=\"22\" Y=\"2.5\" X=\"4.235\"/>\n" +
                "                <Vtx Seq=\"23\" Y=\"2.034\" X=\"4.219\"/>\n" +
                "                <Vtx Seq=\"24\" Y=\"1.55\" X=\"4.172\"/>\n" +
                "                <Vtx Seq=\"25\" Y=\"1.14\" X=\"4.094\"/>\n" +
                "                <Vtx Seq=\"26\" Y=\"0.42\" X=\"4.019\"/>\n" +
                "                <Vtx Seq=\"27\" Y=\"0\" X=\"4.004\"/>\n" +
                "            </Vertices>\n" +
                "            <Catamaran>\n" +
                "                <Vtx Seq=\"1\" Y=\"1.175\" X=\"-3.369\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"0.628\" X=\"-3.44\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"0\" X=\"-3.47\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"0\" X=\"-4.004\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"0.42\" X=\"-4.019\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"1.14\" X=\"-4.094\"/>\n" +
                "                <Vtx Seq=\"7\" Y=\"1.55\" X=\"-4.172\"/>\n" +
                "                <Vtx Seq=\"8\" Y=\"2.034\" X=\"-4.219\"/>\n" +
                "                <Vtx Seq=\"9\" Y=\"2.5\" X=\"-4.235\"/>\n" +
                "                <Vtx Seq=\"10\" Y=\"8.138\" X=\"-4.235\"/>\n" +
                "                <Vtx Seq=\"11\" Y=\"8.581\" X=\"-4.205\"/>\n" +
                "                <Vtx Seq=\"12\" Y=\"9.316\" X=\"-4.124\"/>\n" +
                "                <Vtx Seq=\"13\" Y=\"11.813\" X=\"-4.013\"/>\n" +
                "                <Vtx Seq=\"14\" Y=\"13.897\" X=\"-3.89\"/>\n" +
                "                <Vtx Seq=\"15\" Y=\"14.569\" X=\"-3.818\"/>\n" +
                "                <Vtx Seq=\"16\" Y=\"15\" X=\"-3.735\"/>\n" +
                "                <Vtx Seq=\"17\" Y=\"14.569\" X=\"-3.663\"/>\n" +
                "                <Vtx Seq=\"18\" Y=\"13.897\" X=\"-3.584\"/>\n" +
                "                <Vtx Seq=\"19\" Y=\"11.795\" X=\"-3.463\"/>\n" +
                "                <Vtx Seq=\"20\" Y=\"10.29\" X=\"-3.396\"/>\n" +
                "                <Vtx Seq=\"21\" Y=\"8.557\" X=\"-3.267\"/>\n" +
                "                <Vtx Seq=\"22\" Y=\"8.557\" X=\"3.267\"/>\n" +
                "                <Vtx Seq=\"23\" Y=\"10.29\" X=\"3.396\"/>\n" +
                "                <Vtx Seq=\"24\" Y=\"11.795\" X=\"3.463\"/>\n" +
                "                <Vtx Seq=\"25\" Y=\"13.897\" X=\"3.584\"/>\n" +
                "                <Vtx Seq=\"26\" Y=\"14.569\" X=\"3.663\"/>\n" +
                "                <Vtx Seq=\"27\" Y=\"15\" X=\"3.735\"/>\n" +
                "                <Vtx Seq=\"28\" Y=\"14.569\" X=\"3.818\"/>\n" +
                "                <Vtx Seq=\"29\" Y=\"13.897\" X=\"3.89\"/>\n" +
                "                <Vtx Seq=\"30\" Y=\"11.813\" X=\"4.013\"/>\n" +
                "                <Vtx Seq=\"31\" Y=\"9.316\" X=\"4.124\"/>\n" +
                "                <Vtx Seq=\"32\" Y=\"8.581\" X=\"4.205\"/>\n" +
                "                <Vtx Seq=\"33\" Y=\"8.138\" X=\"4.235\"/>\n" +
                "                <Vtx Seq=\"34\" Y=\"2.5\" X=\"4.235\"/>\n" +
                "                <Vtx Seq=\"35\" Y=\"2.034\" X=\"4.219\"/>\n" +
                "                <Vtx Seq=\"36\" Y=\"1.55\" X=\"4.172\"/>\n" +
                "                <Vtx Seq=\"37\" Y=\"1.14\" X=\"4.094\"/>\n" +
                "                <Vtx Seq=\"38\" Y=\"0.42\" X=\"4.019\"/>\n" +
                "                <Vtx Seq=\"39\" Y=\"0\" X=\"4.004\"/>\n" +
                "                <Vtx Seq=\"40\" Y=\"0\" X=\"3.47\"/>\n" +
                "                <Vtx Seq=\"41\" Y=\"0.628\" X=\"3.44\"/>\n" +
                "                <Vtx Seq=\"42\" Y=\"1.175\" X=\"3.369\"/>\n" +
                "            </Catamaran>\n" +
                "            <Bowsprit>\n" +
                "                <Vtx Seq=\"1\" Y=\"8.56\" X=\"-0.054\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"9.529\" X=\"-0.089\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"12.056\" X=\"-0.088\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"13.314\" X=\"-0.08\"/>\n" +
                "                <Vtx Seq=\"5\" Y=\"13.758\" X=\"0\"/>\n" +
                "                <Vtx Seq=\"6\" Y=\"13.314\" X=\"0.08\"/>\n" +
                "                <Vtx Seq=\"7\" Y=\"12.056\" X=\"0.088\"/>\n" +
                "                <Vtx Seq=\"8\" Y=\"9.529\" X=\"0.089\"/>\n" +
                "                <Vtx Seq=\"9\" Y=\"8.56\" X=\"0.054\"/>\n" +
                "            </Bowsprit>\n" +
                "            <Trampoline>\n" +
                "                <Vtx Seq=\"1\" Y=\"1.175\" X=\"-3.267\"/>\n" +
                "                <Vtx Seq=\"2\" Y=\"8.557\" X=\"-3.267\"/>\n" +
                "                <Vtx Seq=\"3\" Y=\"8.557\" X=\"3.267\"/>\n" +
                "                <Vtx Seq=\"4\" Y=\"1.175\" X=\"3.267\"/>\n" +
                "            </Trampoline>\n" +
                "        </BoatShape>\n" +
                "    </BoatShapes>\n" +
                "    <Boats>\n" +
                "        <Boat Type=\"RC\" SourceID=\"121\" ShapeID=\"35\" StoweName=\"PRO\" ShortName=\"PRO\" ShorterName=\"PRO\" BoatName=\"REGARDLESS\" HullNum=\"RG02\" Skipper=\"Iain Murray\" Helmsman=\"Iain Murray\" PeliID=\"121\" RadioIP=\"172.20.2.121\">\n" +
                "            <GPSposition Z=\"3.97\" Y=\"4.15\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"3.77\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"122\" ShapeID=\"34\" StoweName=\"SL1\" ShortName=\"SL1\" ShorterName=\"SL1\" BoatName=\"Start Line 1\" HullNum=\"Mark-02\" Skipper=\"\" PeliID=\"122\" RadioIP=\"172.20.2.122\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"123\" ShapeID=\"34\" StoweName=\"SL2\" ShortName=\"SL2\" ShorterName=\"SL2\" BoatName=\"Start Line 2\" HullNum=\"Mark-03\" Skipper=\"\" PeliID=\"123\" RadioIP=\"172.20.2.123\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"124\" ShapeID=\"34\" StoweName=\"LG1\" ShortName=\"LG1\" ShorterName=\"LG1\" BoatName=\"Lee Gate 1\" HullNum=\"Mark-04\" Skipper=\"\" PeliID=\"124\" RadioIP=\"172.20.2.124\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"125\" ShapeID=\"34\" StoweName=\"LG2\" ShortName=\"LG2\" ShorterName=\"LG2\" BoatName=\"Lee Gate 2\" HullNum=\"Mark-05\" Skipper=\"\" PeliID=\"125\" RadioIP=\"172.20.2.125\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"126\" ShapeID=\"34\" StoweName=\"WG1\" ShortName=\"WG1\" ShorterName=\"WG1\" BoatName=\"Wind Gate 1\" HullNum=\"WG1\" Skipper=\"\" PeliID=\"126\" RadioIP=\"172.20.2.126\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"127\" ShapeID=\"34\" StoweName=\"WG2\" ShortName=\"WG2\" ShorterName=\"WG2\" BoatName=\"Wind Gate 2\" HullNum=\"Mark-07\" Skipper=\"\" PeliID=\"127\" RadioIP=\"172.20.2.127\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"128\" ShapeID=\"34\" StoweName=\"FL1\" ShortName=\"FL1\" ShorterName=\"FL1\" BoatName=\"Finish Line 1\" HullNum=\"Mark-08\" Skipper=\"\" PeliID=\"128\" RadioIP=\"172.20.2.128\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"129\" ShapeID=\"34\" StoweName=\"FL2\" ShortName=\"FL2\" ShorterName=\"FL2\" BoatName=\"Finish Line 2\" HullNum=\"Mark-09\" Skipper=\"\" PeliID=\"129\" RadioIP=\"172.20.2.129\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"130\" ShapeID=\"34\" StoweName=\"SP1\" ShortName=\"SP1\" ShorterName=\"Sp1\" BoatName=\"Entry Mark\" HullNum=\"Mark-10\" Skipper=\"\" PeliID=\"130\" RadioIP=\"172.20.2.130\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"131\" ShapeID=\"34\" StoweName=\"M1\" ShortName=\"M1\" ShorterName=\"M1\" BoatName=\"Mark 1\" HullNum=\"Mark 1\" Skipper=\"\" PeliID=\"131\" RadioIP=\"172.20.2.131\">\n" +
                "            <GPSposition Z=\"5.573\" Y=\"1.12\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"0.74\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Marshall\" SourceID=\"133\" ShapeID=\"18\" StoweName=\"MIS\" ShortName=\"MIS\" ShorterName=\"MIS\" BoatName=\"MIS\" HullNum=\"Mischief\" Skipper=\"\" PeliID=\"133\" RadioIP=\"172.20.2.133\">\n" +
                "            <GPSposition Z=\"4.82\" Y=\"3.8\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"3.45\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Yacht\" SourceID=\"101\" ShapeID=\"40\" StoweName=\"USA\" ShortName=\"ORACLE\" ShorterName=\"USA\" BoatName=\"ORACLE TEAM USA\" HullNum=\"AC5001\" Skipper=\"SPITHILL\" Helmsman=\"SLINGSBY\" Country=\"USA\" PeliID=\"101\" RadioIP=\"172.20.2.101\">\n" +
                "            <GPSposition Z=\"1.871\" Y=\"-1.067\" X=\"0\"/>\n" +
                "            <MastTop Z=\"25\" Y=\"4.77\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"7.5\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Yacht\" SourceID=\"102\" ShapeID=\"40\" StoweName=\"SWE\" ShortName=\"ARTEMIS\" ShorterName=\"SWE\" BoatName=\"ARTEMIS RACING\" HullNum=\"AC5002\" Skipper=\"OUTTERIDGE\" Helmsman=\"OUTTERIDGE\" Country=\"SWE\" PeliID=\"102\" RadioIP=\"172.20.2.102\">\n" +
                "            <GPSposition Z=\"1.871\" Y=\"-1.067\" X=\"0\"/>\n" +
                "            <MastTop Z=\"25\" Y=\"4.77\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"7.5\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Yacht\" SourceID=\"103\" ShapeID=\"40\" StoweName=\"NZL\" ShortName=\"ETNZ\" ShorterName=\"NZL\" BoatName=\"EMIRATES TEAM NZ\" HullNum=\"AC5003\" Skipper=\"ASHBY\" Helmsman=\"BURLING\" Country=\"NZL\" PeliID=\"103\" RadioIP=\"172.20.2.103\">\n" +
                "            <GPSposition Z=\"1.871\" Y=\"-1.067\" X=\"0\"/>\n" +
                "            <MastTop Z=\"25\" Y=\"4.77\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"7.5\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Yacht\" SourceID=\"104\" ShapeID=\"40\" StoweName=\"JPN\" ShortName=\"JAPAN\" ShorterName=\"JPN\" BoatName=\"SOFTBANK TEAM JAPAN\" HullNum=\"AC5004\" Skipper=\"BARKER\" Helmsman=\"BARKER\" Country=\"JPN\" PeliID=\"104\" RadioIP=\"172.20.2.104\">\n" +
                "            <GPSposition Z=\"1.871\" Y=\"-1.067\" X=\"0\"/>\n" +
                "            <MastTop Z=\"25\" Y=\"4.77\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"7.5\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Yacht\" SourceID=\"105\" ShapeID=\"40\" StoweName=\"FRA\" ShortName=\"FRANCE\" ShorterName=\"FRA\" BoatName=\"GROUPAMA TEAM FRANCE\" HullNum=\"AC5005\" Skipper=\"CAMMAS\" Helmsman=\"CAMMAS\" Country=\"FRA\" PeliID=\"105\" RadioIP=\"172.20.2.105\">\n" +
                "            <GPSposition Z=\"1.871\" Y=\"-1.067\" X=\"0\"/>\n" +
                "            <MastTop Z=\"25\" Y=\"4.77\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"7.5\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Yacht\" SourceID=\"106\" ShapeID=\"40\" StoweName=\"GBR\" ShortName=\"GBR\" ShorterName=\"GBR\" BoatName=\"LAND ROVER BAR\" HullNum=\"AC5006\" Skipper=\"AINSLIE\" Helmsman=\"AINSLIE\" Country=\"GBR\" PeliID=\"106\" RadioIP=\"172.20.2.106\">\n" +
                "            <GPSposition Z=\"1.871\" Y=\"-1.067\" X=\"0\"/>\n" +
                "            <MastTop Z=\"25\" Y=\"4.77\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"7.5\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Marshall\" SourceID=\"109\" ShapeID=\"18\" StoweName=\"RGR\" ShortName=\"RANGER\" ShorterName=\"RNGR\" BoatName=\"Ranger\" HullNum=\"XR04\" Skipper=\"\" PeliID=\"109\" RadioIP=\"172.20.2.109\">\n" +
                "            <GPSposition Z=\"4.82\" Y=\"3.804\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"3.426\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Marshall\" SourceID=\"111\" ShapeID=\"18\" StoweName=\"SHA\" ShortName=\"SHA\" ShorterName=\"SHA\" BoatName=\"SHAMROCK\" HullNum=\"XR01\" Skipper=\"\" PeliID=\"111\" RadioIP=\"172.20.2.111\">\n" +
                "            <GPSposition Z=\"4.82\" Y=\"3.804\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"3.426\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Umpire\" SourceID=\"113\" ShapeID=\"18\" StoweName=\"U1\" ShortName=\"U1\" ShorterName=\"U1\" BoatName=\"VIGILENT\" HullNum=\"XR02\" Skipper=\"\" PeliID=\"113\" RadioIP=\"172.20.2.113\">\n" +
                "            <GPSposition Z=\"3.94\" Y=\"3.804\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Umpire\" SourceID=\"114\" ShapeID=\"18\" StoweName=\"U2\" ShortName=\"U2\" ShorterName=\"U2\" BoatName=\"RESOLUTE\" HullNum=\"XR03\" Skipper=\"\" PeliID=\"114\" RadioIP=\"172.20.2.114\">\n" +
                "            <GPSposition Z=\"4.7\" Y=\"3.804\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"151\" ShapeID=\"20\" StoweName=\"FM1\" ShortName=\"FakeMark1\" ShorterName=\"FM1\" BoatName=\"FakeMark1\" HullNum=\"FM01\" Skipper=\"Bernard Fokke\">\n" +
                "            <GPSposition Z=\"5.095\" Y=\"3.378\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"3\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Mark\" SourceID=\"152\" ShapeID=\"20\" StoweName=\"FM2\" ShortName=\"FakeMark2\" ShorterName=\"FM2\" BoatName=\"FakeMark2\" HullNum=\"FM02\" Skipper=\"Bernard Fokke\">\n" +
                "            <GPSposition Z=\"5.095\" Y=\"3.378\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"3\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Helicopter\" SourceID=\"140\" ShapeID=\"14\" StoweName=\"HL1\" ShortName=\"HEL1\" ShorterName=\"HL1\" BoatName=\"HELICOPTER\" PeliID=\"140\" RadioIP=\"172.20.2.140\"/>\n" +
                "        <Boat Type=\"Marshall\" SourceID=\"137\" ShapeID=\"24\" StoweName=\"CAM\" ShortName=\"Cambria\" ShorterName=\"CAM\" BoatName=\"Cambria\" HullNum=\"TV01\" PeliID=\"137\" RadioIP=\"172.20.2.137\">\n" +
                "            <GPSposition Z=\"3.3\" Y=\"6\" X=\"0\"/>\n" +
                "            <FlagPosition Z=\"0\" Y=\"6\" X=\"0\"/>\n" +
                "        </Boat>\n" +
                "        <Boat Type=\"Helicopter\" SourceID=\"142\" ShapeID=\"14\" StoweName=\"HL2\" ShortName=\"HEL2\" ShorterName=\"Heli2\" BoatName=\"Helicopter\" PeliID=\"142\" RadioIP=\"172.20.2.142\"/>\n" +
                "    </Boats>\n" +
                "</BoatConfig>";
    }

    public String getRegattaXML() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RegattaConfig>\n" +
                "    <RegattaID>32</RegattaID>\n" +
                "    <RegattaName>LVAC Qualifiers 2017</RegattaName>\n" +
                "    <CourseName>Bermuda</CourseName>\n" +
                "    <CentralLatitude>32.30</CentralLatitude>\n" +
                "    <CentralLongitude>-64.84</CentralLongitude>\n" +
                "    <CentralAltitude>0.00</CentralAltitude>\n" +
                "    <UtcOffset>-3</UtcOffset>\n" +
                "    <MagneticVariation>-14.76</MagneticVariation>\n" +
                "    <ShorelineName>bermuda_shoreline</ShorelineName>\n" +
                "</RegattaConfig>";
    }
}
