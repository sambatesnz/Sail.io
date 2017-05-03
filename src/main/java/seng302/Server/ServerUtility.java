package seng302.Server;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by tjg73 on 1/05/17.
 */
public final class ServerUtility {

    /**
     * Private constructor so that The class cannot be instantiated
     */
    private ServerUtility(){
    }

    public static String getLocalIpAddress() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }
}
