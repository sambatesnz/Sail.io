package seng302.DataGeneration;


import seng302.Race;

public interface IServerData {

    Race getRace();

    byte[] getData();

    boolean finished();

    boolean ready();

    void beginGeneratingData();

    void finishGeneratingData();


}
