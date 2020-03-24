import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ViewerTests {

    private Viewer viewer;

    @BeforeEach
    public void newServer() {
        viewer.port = 5056;
    }

    @Test
    public void testSetPort() {
        assertEquals(viewer.port, 5056);
        viewer.setPort(99999);
        assertNotEquals(viewer.port, 5056);
        assertEquals(viewer.port, 99999);
    }

    @Test
    public void testGetPort() {
        assertEquals(viewer.getPort(), 5056);
        viewer.setPort(99999);
        assertNotEquals(viewer.getPort(), 5056);
        assertEquals(viewer.getPort(), 99999);
    }
}