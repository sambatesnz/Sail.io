package seng302;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

/**
 * Created by sha162 on 3/05/17.
 */
public class DataGenerator {

    public DataGenerator(String xml){
        loadFile(xml);
    }

    void loadFile(String xml){
        URL location = getClass().getClassLoader().getResource(xml);
        BufferedReader br;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(new File(location.toURI())));
            String line = "";
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NullPointerException n){
            n.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = sb.toString();

        Message
    }


}
