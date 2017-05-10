package seng302;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Parser to interpret and extract data from an input stream
 */
public class InputStreamParser {

    private byte messageVersionNumber;
    private byte xmlMessageSubType;
    private byte deviceType;

    private byte[] ackNumber;
    private byte[] timeStamp;
    private byte[] seqNumber;
    private byte[] xmlLength;
    private byte[] xmlMessage;
    private byte[] time;
    private byte[] sourceID;
    private byte[] latitude;
    private byte[] longitude;
    private byte[] altitude;
    private byte[] heading;
    private byte[] pitch;
    private byte[] roll;
    private byte[] boatSpeed;
    private byte[] courseOverGround;
    private byte[] speedOverGround;
    private byte[] apparentWindSpeed;
    private byte[] apparentWindAngle;
    private byte[] trueWindSpeed;
    private byte[] trueWindDirection;
    private byte[] trueWindAngle;
    private byte[] currentDrift;
    private byte[] currentSet;
    private byte[] rudderAngle;

    private Race race;

    public byte getMessageVersionNumber() {
        return messageVersionNumber;
    }

    public byte getXmlMessageSubType() {
        return xmlMessageSubType;
    }

    public byte getDeviceType() {
        return deviceType;
    }

    public byte[] getAckNumber() {
        return ackNumber;
    }

    public byte[] getTimeStamp() {
        return timeStamp;
    }

    public byte[] getSeqNumber() {
        return seqNumber;
    }

    public byte[] getXmlLength() {
        return xmlLength;
    }

    public byte[] getXmlMessage() {
        return xmlMessage;
    }

    public byte[] getTime() {
        return time;
    }

    public byte[] getSourceID() {
        return sourceID;
    }

    public byte[] getLatitude() {
        return latitude;
    }

    public byte[] getLongitude() {
        return longitude;
    }

    public byte[] getAltitude() {
        return altitude;
    }

    public byte[] getHeading() {
        return heading;
    }

    public byte[] getPitch() {
        return pitch;
    }

    public byte[] getRoll() {
        return roll;
    }

    public byte[] getBoatSpeed() {
        return boatSpeed;
    }

    public byte[] getCourseOverGround() {
        return courseOverGround;
    }

    public byte[] getSpeedOverGround() {
        return speedOverGround;
    }

    public byte[] getApparentWindSpeed() {
        return apparentWindSpeed;
    }

    public byte[] getApparentWindAngle() {
        return apparentWindAngle;
    }

    public byte[] getTrueWindSpeed() {
        return trueWindSpeed;
    }

    public byte[] getTrueWindDirection() {
        return trueWindDirection;
    }

    public byte[] getTrueWindAngle() {
        return trueWindAngle;
    }

    public byte[] getCurrentDrift() {
        return currentDrift;
    }

    public byte[] getCurrentSet() {
        return currentSet;
    }

    public byte[] getRudderAngle() {
        return rudderAngle;
    }

    public Race getRace() {
        return race;
    }

    public InputStreamParser(Race race) {
        this.race = race;
    }

    /**
     * Parses the data received, getting the relevant data from its position in the byte array.
     * @param type data type.
     * @param data the data to be parsed.
     */
    public void parseInput(int type, byte[] data) {

        switch (type) {
            case 1:                                             //Heartbeat
                break;
            case 12: //race.setRaceStatus(data[11]);            //Race Status
                break;
            case 20:                                            //Display
                break;
            case 26:                                            //XML Message
                messageVersionNumber = data[0];
                ackNumber = Arrays.copyOfRange(data, 1, 3);
                timeStamp = Arrays.copyOfRange(data, 3, 9);
                xmlMessageSubType = data[9];
                seqNumber = Arrays.copyOfRange(data, 10, 12);
                xmlLength = Arrays.copyOfRange(data, 12, 14);

                xmlMessage = Arrays.copyOfRange(data, 14, data.length);
                System.out.println(xmlMessage);

//                Document doc = null;
//                try {
//                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//                    doc = dBuilder.newDocument();
//                    Element toAdd = xmlMessage.
//                    doc.getDocumentElement().normalize();
//                } catch (Exception e) {
//                    throw new Error(e);
//                }
//
//                if (xmlMessageSubType == 5) {             //Regatta
//
//                } else if (xmlMessageSubType == 6) {      //Race
//
//                } else if (xmlMessageSubType == 7) {      //Boat
//
//                }
                break;
            case 27:                                            //Race Start Status
                break;
            case 29:                                            //Yacht Event Code
                break;
            case 31:                                            //Yacht Action Code
                break;
            case 36:                                            //Chatter Text
                break;
            case 37:                                            //Boat Location
                messageVersionNumber = data[0];
                time = Arrays.copyOfRange(data, 1, 7);
                sourceID = Arrays.copyOfRange(data, 7, 11);
                seqNumber = Arrays.copyOfRange(data, 11, 15);
                deviceType = data[15];
                latitude = Arrays.copyOfRange(data, 16, 20);
                longitude = Arrays.copyOfRange(data, 20, 24);
                altitude = Arrays.copyOfRange(data, 24, 28);
                heading = Arrays.copyOfRange(data, 28, 30);
                pitch = Arrays.copyOfRange(data, 30, 32);
                roll = Arrays.copyOfRange(data, 32, 34);
                boatSpeed = Arrays.copyOfRange(data, 34, 36);
                courseOverGround = Arrays.copyOfRange(data, 36, 38);
                speedOverGround = Arrays.copyOfRange(data, 38, 40);
                apparentWindSpeed = Arrays.copyOfRange(data, 40, 42);
                apparentWindAngle = Arrays.copyOfRange(data, 42, 44);
                trueWindSpeed = Arrays.copyOfRange(data, 44, 46);
                trueWindDirection = Arrays.copyOfRange(data, 46, 48);
                trueWindAngle = Arrays.copyOfRange(data, 48, 50);
                currentDrift = Arrays.copyOfRange(data, 50, 52);
                currentSet = Arrays.copyOfRange(data, 52, 54);
                rudderAngle = Arrays.copyOfRange(data, 54, 56);

                //Retrieve the boat that this message is about
                //Boat boat = race.getBoatByID(ByteBuffer.wrap(sourceID).order(ByteOrder.LITTLE_ENDIAN).getInt());

                //Set the boat speed the the value given
                //boat.setSpeed(ByteBuffer.wrap(sourceID).order(ByteOrder.LITTLE_ENDIAN).getDouble());


                break;
            case 38:                                            //Mark Rounding
                break;
            case 44:                                            //Course Wind
                break;
            case 47:                                            //Avg Wind
                break;


        }
    }

}