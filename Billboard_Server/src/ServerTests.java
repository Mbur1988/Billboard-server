import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {

    private Server server;

    @BeforeEach
    public void newServer() {
        server.port = 5056;
    }

    @Test
    public void testSetPort() {
        assertEquals(server.port, 5056);
        server.setPort(99999);
        assertNotEquals(server.port, 5056);
        assertEquals(server.port, 99999);
    }

    @Test
    public void testGetPort() {
        assertEquals(server.getPort(), 5056);
        server.setPort(99999);
        assertNotEquals(server.getPort(), 5056);
        assertEquals(server.getPort(), 99999);
    }
}
