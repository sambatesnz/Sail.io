package seng302;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;

/**
 * Created by jtr44 on 1/05/17.
 *
 */
public class Interpreter {

    private Socket clientSocket;
    private DataInputStream streamInput;
    private String serverName;
    private int port;
    private byte[] data;
    String output = "";

    public Interpreter() {

        data = new byte[1000];
        clientSocket = null;
        streamInput = null;
        AppConfig config = new AppConfig();
        serverName = config.getProperty(AppConfig.DATA_HOST_NAME);
        port = Integer.parseInt(config.getProperty(AppConfig.DATA_HOST_PORT));

    }

    public void retrieveData() {
        try {
            String host = new URL(serverName).getHost();
            System.out.println("Attempting to connect...");
            clientSocket = new Socket(host, port);
            System.out.println("Connected.");
            streamInput = new DataInputStream(clientSocket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while (clientSocket != null && streamInput != null) {
            try {
                int result = streamInput.read(data);
                output += new String(data, 0, result);
                System.out.println(output);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            streamInput.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

