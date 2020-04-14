package Tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    /**
     * Retreives all properties from the configuration .props file
     * @return Properties
     * @throws IOException
     */
    public static Properties GetProperties(String file) throws IOException {
        Properties properties = new Properties();
        FileInputStream ip= new FileInputStream(ProjectPath.RootString() + "\\Resources/"+file+".props");
        properties.load(ip);
        return properties;
    }

    /**
     * Retreives the requested property from the configuration .props file
     * @param property Requested property
     * @return Property value
     * @throws IOException
     */
    public static String GetProperty(String file, String property) throws IOException {
        Properties prop = new Properties();
<<<<<<< Updated upstream
        FileInputStream ip= new FileInputStream(ProjectPath.RootString() + "\\Resources\\" + file + ".props");
=======
        FileInputStream ip= new FileInputStream(ProjectPath.ResourcesPath() + "\\" + file +".props");
>>>>>>> Stashed changes
        prop.load(ip);
        return prop.getProperty(property);
    }
}