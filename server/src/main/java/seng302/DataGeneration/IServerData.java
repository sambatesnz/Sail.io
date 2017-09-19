package seng302.DataGeneration;


import seng302.Modes.Race;

import java.io.IOException;

public interface IServerData {

    Race getRace();

    byte[] getDataForAll();

    byte[] getDataForOne();

    void addSingleMessage(byte[] message);

    boolean finished();

    boolean broadcastReady();

    boolean singleMessageReady();

    void beginGeneratingData();

    void finishGeneratingData();

    void addXMLPackets() throws IOException;

}
