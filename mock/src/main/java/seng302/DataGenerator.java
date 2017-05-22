package seng302;

/**
 * Created by sha162 on 3/05/17.
 */
public class DataGenerator {

    public String loadFile(String xml){
        java.util.Scanner s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream(xml)).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
