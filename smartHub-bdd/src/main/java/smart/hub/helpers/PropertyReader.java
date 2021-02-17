package smart.hub.helpers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public static String Read(String file, String key) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(file)) {
            // load a properties file
            prop.load(input);

            // get the property value and print it out

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
