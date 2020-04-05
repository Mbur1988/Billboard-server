package Tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    /**
     * Retreives the requested property from the configuration .props file
     * @param property Requested property
     * @return Property value
     * @throws IOException
     */
    public static String GetProperty(String property) throws IOException {
        Properties prop = new Properties();
        FileInputStream ip= new FileInputStream("Resources/client.props");
        prop.load(ip);
        return prop.getProperty(property);
    }
}