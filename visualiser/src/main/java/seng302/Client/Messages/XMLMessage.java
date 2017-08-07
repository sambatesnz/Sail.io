package seng302.Client.Messages;

import seng302.PacketParsing.PacketParserUtils;

import java.io.UnsupportedEncodingException;

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
        xmlMessageSubtype = PacketParserUtils.byteArrayToInt(data, 9, 1);
        xmlMessageLen = PacketParserUtils.byteArrayToInt(data, 12, 2);
        xmlMessage = new byte[xmlMessageLen];
        System.arraycopy(data,14, xmlMessage,0, xmlMessageLen);
        xmlString = new String(xmlMessage, "UTF-8");
//        System.out.println(xmlMessageSubtype);
//        System.out.println(xmlString);
    }

    public int getXmlMessageSubtype() {
        return xmlMessageSubtype;
    }

    public String getXmlString() {
        return xmlString;
    }
}
