package seng302;

import java.io.File;
import java.io.FileNotFoundException;

public class DataGenerator {

    public String loadFile(String xml){
        java.util.Scanner s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream(xml)).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String loadTestFile(String path) throws FileNotFoundException {
        java.util.Scanner s = new java.util.Scanner(new File(path)).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
