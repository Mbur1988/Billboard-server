import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.net.UnknownHostException;

public class ViewerTests {

    private Viewer viewer;

    @BeforeEach
    public void newServer() {
        viewer = new Viewer();
    }

    @Test
    public void getPort() {
        assertEquals(viewer.getPort(), 0);
    }

    @Test
    public void setPort() {
        viewer.setPort(99999);
        assertEquals(viewer.getPort(), 99999);
    }

    @Test
    public void getip() throws UnknownHostException {
    viewer.setIp("localhost");
    assertEquals(viewer.getIp(), "127.0.0.1");
    }

    @Test
    public void setip() throws UnknownHostException {
        viewer.setIp("192.168.001.254");
        assertEquals(viewer.getIp(), "192.168.1.254");
    }
}