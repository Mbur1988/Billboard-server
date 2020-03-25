import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ControlPanelTests {

    private ControlPanel controlPanel;

    @BeforeEach
    public void newControlPanel() {
        controlPanel.port = 5056;
    }

    @Test
    public void testSetPort() {
        assertEquals(controlPanel.port, 5056);
        controlPanel.setPort(99999);
        assertNotEquals(controlPanel.port, 5056);
        assertEquals(controlPanel.port, 99999);
    }

    @Test
    public void testGetPort() {
        assertEquals(controlPanel.getPort(), 5056);
        controlPanel.setPort(99999);
        assertNotEquals(controlPanel.getPort(), 5056);
        assertEquals(controlPanel.getPort(), 99999);
    }
}