package seng302;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;

/**
 * Created by sha162 on 3/05/17.
 */
public class DataGenerator {

    public DataGenerator(String xml){
        loadFile(xml);
    }

    void loadFile(String xml){
        System.out.println(xml);

        String location = String.valueOf(getClass().getClassLoader().getResource(xml));

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(location)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        StringBuilder sb = new StringBuilder();

    }


}
