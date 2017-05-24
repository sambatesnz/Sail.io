package seng302;

import seng302.Server.Server;

import java.io.IOException;

/**
 * Runs the Mock server
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Server server = new Server(4941);
    }
}   