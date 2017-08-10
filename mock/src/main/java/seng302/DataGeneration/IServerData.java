package seng302.DataGeneration;


import seng302.Race;

public interface IServerData {

    Race getRace();

    byte[] getDataForAll();

    byte[] getDataForOne();

    boolean finished();

    boolean ready();

    void beginGeneratingData();

    void finishGeneratingData();

    void addXMLPackets();

}
