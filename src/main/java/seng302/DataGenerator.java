package seng302;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

/**
 * Created by sha162 on 3/05/17.
 */
public class DataGenerator {

    public String loadFile(String xml){
        URL location = getClass().getClassLoader().getResource(xml);
        BufferedReader br;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(new File(location.toURI())));
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
