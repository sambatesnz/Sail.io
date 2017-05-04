package seng302;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private Properties prop = new Properties();
    public static final String COURSE_FILE_LOCATION= "courseFileLocation";
    public static final String DATA_HOST_NAME = "testACDataHostname";
    public static final String DATA_HOST_PORT = "testACDataPort";

    private static final String CONFIG_FILE_LOCATION = "src/config.properties";

    public AppConfig(){
        loadConfigFile(CONFIG_FILE_LOCATION);
    }

    private void loadConfigFile(String location){
        InputStream input = null;
        try {
            input = new FileInputStream(location);
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
