package Tools;

import org.junit.jupiter.api.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertyReaderTest {

    private static Properties original = new Properties();
    private static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        FileInputStream in = new FileInputStream("Resources/client.props");
        original.load(in);
        in.close();
        properties = (Properties)original.clone();
        properties.setProperty("IpAddress", "192.168.001.254");
        properties.setProperty("Port", "99999");
        FileOutputStream out = new FileOutputStream("Resources/client.props");
        properties.store(out, null);
        out.close();
    }

    @Test
    void getProperties1() throws IOException {
        assertEquals(PropertyReader.GetProperties(), properties);
    }

    @Test
    void getProperty1() throws IOException {
        assertEquals(PropertyReader.GetProperty("IpAddress"), "192.168.001.254");
    }

    @Test
    void getProperty2() throws IOException {
        assertEquals(PropertyReader.GetProperty("Port"), "99999");
    }

    @Test
    void getProperty3() throws IOException {
        assertEquals(PropertyReader.GetProperty("IpAddress"), properties.getProperty("IpAddress"));
    }

    @Test
    void getProperty4() throws IOException {
        assertEquals(PropertyReader.GetProperty("Port"), properties.getProperty("Port"));
    }

    @AfterAll
    static void cleanUp() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("Resources/client.props");
        original.store(fileOut, null);
        fileOut.close();
    }
}