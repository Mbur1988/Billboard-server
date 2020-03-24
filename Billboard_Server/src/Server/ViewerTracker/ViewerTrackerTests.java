package Server.ViewerTracker;

import java.io.*;
import java.net.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Server.ViewerHandler.ViewerHandler;
import org.junit.jupiter.api.*;

public class ViewerTrackerTests {

    private ViewerTracker viewerTracker = new ViewerTracker();
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String uuid1 = UUID.randomUUID().toString();
    private String uuid2 = UUID.randomUUID().toString();

    @BeforeEach
    public void clearClientTracker() {
        viewerTracker.activeViewers.clear();
    }

    @Test
    public void testAdd() throws ViewerTrackerException {
        assertEquals(viewerTracker.activeViewers.size(), 0);
        viewerTracker.Add(uuid1, new ViewerHandler(socket, dis, dos, uuid1));
        assertEquals(viewerTracker.activeViewers.size(), 1);
    }

    @Test
    public void testRemove() throws ViewerTrackerException {
        viewerTracker.Add(uuid1, new ViewerHandler(socket, dis, dos, uuid1));
        assertEquals(viewerTracker.activeViewers.size(), 1);
        viewerTracker.Add(uuid2, new ViewerHandler(socket, dis, dos, uuid2));
        assertEquals(viewerTracker.activeViewers.size(), 2);
        viewerTracker.Remove(uuid1);
        assertEquals(viewerTracker.activeViewers.size(), 1);
        viewerTracker.Remove(uuid2);
        assertEquals(viewerTracker.activeViewers.size(), 0);
    }

    @Test
    public void testSameUUID() throws ViewerTrackerException {
        viewerTracker.Add(uuid1, new ViewerHandler(socket, dis, dos, uuid1));
        assertEquals(viewerTracker.activeViewers.size(), 1);
        assertThrows(ViewerTrackerException.class, () -> {
            viewerTracker.Add(uuid1, new ViewerHandler(socket, dis, dos, uuid1));
        });
    }
}
