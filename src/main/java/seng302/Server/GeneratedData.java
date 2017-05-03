package seng302.Server;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by sba136 on 3/05/17.
 */
public class GeneratedData implements IServerData {
    private Queue<byte[]> bytes = new ArrayBlockingQueue<byte[]>();

    @Override
    public byte[] getData() {
        return new byte[0];
    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public boolean ready() {

        return false;
    }

    //addData()
}
