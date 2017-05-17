package seng302;

import javafx.geometry.Pos;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import seng302.Messages.LocationMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.Location;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Parser to interpret and extract data from an input stream
 */
public class InputStreamParser {


    private Race race;


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
//    public void parseInput(int type, byte[] data) {
//
//        switch (type) {
//            case 1:                                             //Heartbeat
//                break;
//            case 12: //race.setRaceStatus(data[11]);            //Race Status
//                break;
//            case 20:                                            //Display
//                break;
//            case 26:                                            //XML Message
//                break;
//            case 27:                                            //Race Start Status
//                break;
//            case 29:                                            //Yacht Event Code
//                break;
//            case 31:                                            //Yacht Action Code
//                break;
//            case 36:                                            //Chatter Text
//                break;
//            case 37:                                            //Boat Location
//                LocationMessage location = new LocationMessage(data);
//
//                //Retrieve the boat that this message is about
//                //Boat boat = race.getBoatByID(ByteBuffer.wrap(sourceID).order(ByteOrder.LITTLE_ENDIAN).getInt());
//
//                //Set the boat speed the the value given
//                //boat.setSpeed(ByteBuffer.wrap(sourceID).order(ByteOrder.LITTLE_ENDIAN).getDouble());
//
//
//                break;
//            case 38:                                            //Mark Rounding
//                break;
//            case 44:                                            //Course Wind
//                break;
//            case 47:                                            //Avg Wind
//                break;
//
//
//        }
//    }

}