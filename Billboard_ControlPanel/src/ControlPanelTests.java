import static org.junit.jupiter.api.Assertions.*;
import CustomExceptions.InvalidPortException;
import org.junit.jupiter.api.*;
import java.net.UnknownHostException;

public class ControlPanelTests {

    private ControlPanel cp;

    @BeforeEach
    public void newServer() {
        cp = new ControlPanel();
    }

    @Test
    public void getPort() {
        assertEquals(cp.getPort(), 0);
    }

    @Test
    public void setPort() throws InvalidPortException {
        cp.setPort(99999);
        assertEquals(cp.getPort(), 99999);
    }

    @Test
    public void getip() throws UnknownHostException {
        cp.setIp("localhost");
        assertEquals(cp.getIp(), "127.0.0.1");
    }

    @Test
    public void setip() throws UnknownHostException {
        cp.setIp("192.168.001.254");
        assertEquals(cp.getIp(), "192.168.1.254");
    }
}