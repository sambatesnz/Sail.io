package seng302.Client.Messages;

import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.RaceStatus;
import seng302.PacketParsing.PacketParserUtils;
import seng302.PacketParsing.XMLParser;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.Race;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Class to read in packets from a socket stream and distribute them to their relative
 * message handler.
 */
public class Message {
    private MessageType messageType;
    private int messageLen;
    private byte body[];
    private byte crc[];
    private Race race;

    private static boolean boatsSet = false;
    private static boolean regattaSet = false;
    private static boolean raceSet = false;
    ClientSideMessageParser messageParser;


    public static void resetData() {
        boatsSet = false;
        regattaSet = false;
        raceSet = false;
    }

    /**
     * Constructor for the class. Takes an array of bytes, extracts information from the header
     * (such as the message type and length) and removes it from the rest of the data,
     * along with the CRC, in preparation for the message to be given to it's relative handler.
     * @param data The array of bytes containing the header, message and CRC
     * @param race the race being run
     */
    public Message(byte[] data, Race race){
        this.race = race;
        messageType =  PacketParserUtils.getMessageType(data);
        messageLen =  PacketParserUtils.getMessageBodyLength(data);

        body = new byte[messageLen];
        System.arraycopy(data,15, body,0, messageLen);
        crc = new byte[4];
        System.arraycopy(data,15 + messageLen, crc,0, 4);
    }


    /**
     * Gives the packet to the relative message handler based on the messageType
     * @throws UnsupportedEncodingException when the message is not correct.
     */
    public void parseMessage() throws UnsupportedEncodingException {
        switch (messageType) {
            case HEART_BEAT:
                break;
            case RACE_STATUS:
                new RaceStatusMessage(body, race);
                break;
            case DISPLAY_TEXT_MESSAGE:
                break;
            case XML_MESSAGE:
                XMLMessage xmlMessage = new XMLMessage(body);
                MessageType subType = MessageType.getType(xmlMessage.getXmlMessageSubtype());
                passXML(xmlMessage.getXmlString(), subType);
                break;
            case RACE_START_STATUS:
                break;
            case YACHT_EVENT:
                messageParser = new YachtEventMessage(body);
                messageParser.updateRace(race);
                break;
            case BOAT_LOCATION:
                new LocationMessage(body, race);
                break;
            case PARTICIPANT_CONFIRMATION:
                messageParser = new ClientParticipantConfirmationMessage(body);
                messageParser.updateRace(race);
        }
    }

    private void passXML(String xmlString, MessageType subType) {
        try {
            XMLParser xmlParser = new XMLParser(xmlString);
            switch(subType) {
                case REGATTA:
                    race.setRegatta(xmlParser.getRegatta());
                    regattaSet = true;
                    break;
                case BOAT:
                    if (race.getRaceStatus() == RaceStatus.WARNING || race.getRaceStatus() == RaceStatus.START_TIME_NOT_SET || (race.getClientSourceId() == 0)) {
                        if (race.getClientSourceId() != 0) { //Spectating
                            race.setBoats(xmlParser.getBoats());
                        } else if (race.getBoatsMap() == null){
                            race.setBoats(xmlParser.getBoats());
                        } else if (race.getBoatsMap().size() != xmlParser.getBoats().size()){
                            race.setBoats(xmlParser.getBoats());
                        }
                        boatsSet = true;
                    }
                    break;
                case RACE:
                    race.setParticipants(xmlParser.getRaceParticipants());
                    race.setBoundaries(xmlParser.getCourseLimits());
                    List<CompoundMark> compoundMarks = xmlParser.getCourseLayout();
                    race.setCompoundMarks(compoundMarks);
                    race.setMarks(xmlParser.getMarks());
                    race.setGates(compoundMarks);
                    race.setCourseOrder(xmlParser.getCourseOrder());
                    race.setStartTime(xmlParser.getRaceStartTime());
                    race.setRaceXMLReceived(true);
                    race.setViewReady(true);
                    raceSet = true;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (regattaSet && boatsSet && raceSet) {
            race.setRaceReady(true);
        }
    }
}
