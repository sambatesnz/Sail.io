//package seng302;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class NodeJsWrapper {
//    private Socket socket = null;
//
//    String ip;
//    int port;
//
//    public NodeJsWrapper(String ip, int port) throws IOException {
//        this.ip = ip;
//        this.port = port;
//        socketConnect(ip, port);
//
//    }
//
//
//    // make the connection with the socket
//    public void socketConnect(String ip, int port) throws UnknownHostException, IOException {
//        System.out.println("[Connecting to socket...]");
//        this.socket = new Socket(ip, port);
//    }
//
//
//    // writes and receives the full message int the socket (String)
//    public String echo(String message) {
//        try {
//            // out & in
//            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
//
//
//            // writes str in the socket and read
//            out.println(message);
//            String returnStr = in.readLine();
//            //echo(returnStr);
//            return returnStr;
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return null;
//    }
//
//    public void listen() throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
//        String out = in.readLine();
//
//        while (out != null){
//            System.out.println(out);
//            out = in.readLine();
//        }
//    }
//
//
//    // get the socket instance
//    private Socket getSocket() {
//        return socket;
//    }
//}
