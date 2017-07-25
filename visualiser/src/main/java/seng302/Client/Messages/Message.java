package seng302.Client.Messages;

import seng302.Race.CompoundMark;
import seng302.Race.Race;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to read in packets from a socket stream and distribute them to their relative
 * message handler.
 */
public class Message {
    private static final int HEARTBEAT = 1;
    private static final int RACE_STATUS = 12;
    private static final int XML_MESSAGE = 26;
    private static final int BOAT_LOCATION = 37;
    private static final int DISPLAY = 20;
    private static final int RACE_START = 27;
    private final int REGATTA = 5;
    private final int RACE = 6;
    private final int BOAT = 7;
    private int syncByte1;
    private int syncByte2;
    private int messageType;
    private int messageLen;
    private byte body[];
    private byte crc[];
    private Race race;

    private static boolean boatsSet = false;
    private static boolean regattaSet = false;
    private static boolean raceSet = false;



    /**
     * Constructor for the class. Takes an array of bytes, extracts information from the header
     * (such as the message type and length) and removes it from the rest of the data,
     * along with the CRC, in preparation for the message to be given to it's relative handler.
     * @param data The array of bytes containing the header, message and CRC
     */
    public Message(byte[] data, Race race){
        this.race = race;
        syncByte1 = byteArrayToInt(data, 0, 1);
        syncByte2 = byteArrayToInt(data, 1,1);
        messageType = byteArrayToInt(data, 2,1);
        messageLen = byteArrayToInt(data, 13,2);

        body = new byte[messageLen];
        System.arraycopy(data,15, body,0, messageLen);
        crc = new byte[4];
        System.arraycopy(data,15 + messageLen, crc,0, 4);
    }

    /**
     * Converts a section from an array of bytes into an integer.
     * @param bytes The array to convert bytes from
     * @param pos The starting index of the bytes desired to be converted
     * @param len The number of bytes to be converted (from the given index)
     * @return An integer, converted from the given bytes
     */
    public static int byteArrayToInt(byte[] bytes, int pos, int len){
        byte[] intByte = new byte[4];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Converts a section from an array of bytes into a long.
     * @param bytes The array to convert bytes from
     * @param pos The starting index of the bytes desired to be converted
     * @param len The number of bytes to be converted (from the given index)
     * @return A long, converted from the given bytes
     */
    public static long byteArrayToLong(byte[] bytes, int pos, int len) {
        byte[] intByte = new byte[8];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }


    /**
     * Gives the packet to the relative message handler based on the messageType
     * @throws UnsupportedEncodingException
     */
    public void parseMessage() throws UnsupportedEncodingException {
//        System.out.println("packet about to be parsed!\n");  //Testing; To remove
        switch (messageType) {
            case HEARTBEAT:
                break;
            case RACE_STATUS:
                new RaceStatusMessage(body, race);
                break;
            case DISPLAY:
                break;
            case XML_MESSAGE:
                XMLMessage xmlMessage = new XMLMessage(body);
                passXML(xmlMessage.getXmlString(), xmlMessage.getXmlMessageSubtype());
                break;
            case RACE_START:
                break;
            case 29:                                            //Yacht Event Code
                break;
            case 31:                                            //Yacht Action Code
                break;
            case 36:                                            //Chatter Text
                break;
            case BOAT_LOCATION:                                 //Boat Location
                new LocationMessage(body, race);
                break;
            case 38:                                            //Mark Rounding
                break;
            case 44:                                            //Course Wind
                break;
            case 47:                                            //Avg Wind
                break;
        }
    }

    public void passXML(String xmlString, int subType) {
        LocalDateTime startTime = LocalDateTime.now();
        try {
            XMLParser xmlParser = new XMLParser(xmlString);
            switch(subType) {
                case REGATTA:
                    race.setRegatta(xmlParser.getRegatta());
                    regattaSet = true;
                    break;
                case BOAT:
                    // TODO Bug fix so we can connect to csse stream 4941. Need to throttle the rate packets get processed to properly fix this
                    if (!race.isRaceReady()) {
                        race.setBoats(xmlParser.getBoats());
                    }
                    boatsSet = true;
                    break;
                case RACE:
                    race.setParticipants(xmlParser.getRaceParticipants());
                    race.setBoundaries(xmlParser.getCourseLimits());
                    List<CompoundMark> compoundMarks = xmlParser.getCourseLayout();
                    race.setCompoundMarks(compoundMarks);
                    race.setGates(compoundMarks);
                    race.setCourseOrder(xmlParser.getCourseOrder());
                    startTime = xmlParser.getRaceStartTime();
                    race.setRaceXMLReceived(true);
                    //race.setViewParams();
                    race.setViewReady(true);
                    raceSet = true;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (regattaSet && boatsSet && raceSet) {
//            int zid = race.getRegatta().getUtcOffset();
//            String zidStr = String.valueOf(zid);
//            if (zid > 0) {
//                zidStr = "+" + String.valueOf(zid);
//            }
//            ZoneId zoneId = ZoneId.of(zidStr);
//            long epoch = startTime.atZone(zoneId).toEpochSecond();
//            race.setExpectedStartTime(epoch);
//            race.setViewParams();
            race.setRaceReady(true);
        }
    }
}
