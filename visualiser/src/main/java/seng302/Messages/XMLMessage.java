package seng302.Messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.w3c.dom.*;

import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnmappableCharacterException;


public class XMLMessage {
    private int xmlMessageSubtype;
    private int xmlMessageLen;
    private byte[] xmlMessage;
    private String xmlString;


    public XMLMessage(byte[] data) throws UnsupportedEncodingException{
        xmlMessageSubtype = Message.byteArrayToInt(data, 9, 1);
        xmlMessageLen = Message.byteArrayToInt(data, 12, 2);
        xmlMessageLen = Message.byteArrayToInt(data, 12, 2);
        xmlMessage = new byte[xmlMessageLen];
        System.arraycopy(data,14, xmlMessage,0, xmlMessageLen);
        xmlString = new String(xmlMessage, "UTF-8");
        System.out.println(xmlString);


    }

    public void parseBoats() throws ParserConfigurationException, UnsupportedEncodingException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String boatsString = new String(xmlMessage, "UTF-8");
        //print


    }

}
