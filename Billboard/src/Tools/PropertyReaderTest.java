package Tools;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyReaderTest {

    private static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        properties.setProperty("Port", "99999");
        properties.setProperty("IpAddress", "192.168.001.254");
        FileOutputStream out = new FileOutputStream("Resources\\test.props");
        properties.store(out, "last test:");
        out.close();
    }

    @AfterAll
    static void cleanUp() {
        File file = new File(ProjectPath.FileString("Resources\\test.props"));
        file.delete();
    }

    @Test
    void getProperties1() throws IOException {
        assertEquals(PropertyReader.GetProperties("test"), properties);
    }

    @Test
    void getProperty1() throws IOException {
        assertEquals(PropertyReader.GetProperty("test", "Port"), "99999");
    }

    @Test
    void getProperty2() throws IOException {
        assertEquals(PropertyReader.GetProperty("test", "IpAddress"), "192.168.001.254");
    }

    @Test
    void getProperty3() throws IOException {
        assertEquals(PropertyReader.GetProperty("test", "Port"), properties.getProperty("Port"));
    }

    @Test
    void getProperty4() throws IOException {
        assertEquals(PropertyReader.GetProperty("test", "IpAddress"), "192.168.001.254");
    }
}