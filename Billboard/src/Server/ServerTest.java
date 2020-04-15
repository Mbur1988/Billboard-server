package Server;

import CustomExceptions.InvalidPortException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    private Server server;

    @BeforeEach
    public void newServer() {
        server = new Server();
    }

    @Test
    public void getPort() {
        assertEquals(server.getPort(), 0);
    }

    @Test
    public void setPort() throws InvalidPortException {
        server.setPort(99999);
        assertEquals(server.getPort(), 99999);
    }
}