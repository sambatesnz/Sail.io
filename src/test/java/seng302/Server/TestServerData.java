package seng302.Server;

import java.util.ArrayList;

/**
 * Created by tjg73 on 1/05/17.
 */
public class TestServerData implements IServerData {


    private ArrayList<byte[]> bytes;

    public TestServerData(){
        bytes = new ArrayList<>();
    }


    void add(byte[] data){
        bytes.add(data);
    }

    @Override
    public byte[] getData() {
        byte[] toSend = bytes.get(bytes.size() - 1);
        bytes.remove(toSend);
        return toSend;
    }

    @Override
    public boolean finished() {
        return bytes.size() == 0;
    }

    @Override
    public boolean ready() {
        return bytes.size() > 0;
    }


}
