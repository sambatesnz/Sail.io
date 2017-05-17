package seng302.Messages;

import javax.xml.parsers.*;
import java.io.*;

/**
 * Class that extracts an XML message from an XML packet, and packages it into
 * a string for the parser to use.
 */

public class XMLMessage {
    private int xmlMessageSubtype;
    private int xmlMessageLen;
    private byte[] xmlMessage;
    private String xmlString;


    /**
     * Constructor for the class. Takes an array of bytes from an XML packet as
     * a parameter, and extracts the relevant information from the XML header (type of XML,
     * length of the XML message). Then creates a string from the XML message for use by
     * the parser.
     * @param data The array of bytes from the body of an XML messsage packet
     * @throws UnsupportedEncodingException
     */
    public XMLMessage(byte[] data) throws UnsupportedEncodingException{
        xmlMessageSubtype = Message.byteArrayToInt(data, 9, 1);
        xmlMessageLen = Message.byteArrayToInt(data, 12, 2);
        xmlMessageLen = Message.byteArrayToInt(data, 12, 2);
        xmlMessage = new byte[xmlMessageLen];
        System.arraycopy(data,14, xmlMessage,0, xmlMessageLen);
        xmlString = new String(xmlMessage, "UTF-8");
//        System.out.println(xmlString);
    }

    public void parseBoats() throws ParserConfigurationException, UnsupportedEncodingException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String boatsString = new String(xmlMessage, "UTF-8");
    }

}
