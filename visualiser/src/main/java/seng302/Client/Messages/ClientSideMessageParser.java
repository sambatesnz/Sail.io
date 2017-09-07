package seng302.Client.Messages;
import seng302.RaceObjects.Race;

/**
 * Parses client side messages (a binary message wrapped in a header)
 */
public abstract class ClientSideMessageParser {
    private byte[] body;

    public ClientSideMessageParser(byte[] packet) {
        this.body = body;
    }

    public abstract void updateRace(Race race);

}

