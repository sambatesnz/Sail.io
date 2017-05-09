package seng302;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private Properties prop = new Properties();
    public static final String COURSE_FILE_LOCATION = "courseFileLocation";
    public static final String DATA_HOST_NAME = "testACDataHostname";
    public static final String DATA_HOST_PORT = "testACDataPort";

    private final String CONFIG_FILE_LOCATION = "config.properties";

    public AppConfig(){
        loadConfigFile();
    }

    private void loadConfigFile(){
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_LOCATION);
            System.out.println(input);
            this.prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     public String getProperty(String property){
        return this.prop.getProperty(property);
    }
}
