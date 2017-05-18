package seng302;

import org.junit.Test;

/**
 * Created by jtr44 on 10/05/17.
 */
public class StreamParseTest {
    StreamClient client = new StreamClient(new Race());

    @Test
    public void ParseTest() {
        client.connect();
        client.retrieveData();

    }
}
