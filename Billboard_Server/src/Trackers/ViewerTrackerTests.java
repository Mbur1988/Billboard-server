package Trackers;

import Trackers.ViewerTracker;

import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewerTrackerTests {

    private ViewerTracker viewerTracker = new ViewerTracker();
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

//    @BeforeEach
//    public void clearClientTracker() {
//        viewerTracker.activeViewers.clear();
//    }
//
//    @Test
//    public void testAdd() throws ViewerTrackerException {
//        assertEquals(viewerTracker.activeViewers.size(), 0);
//        viewerTracker.Add(new ViewerHandler(socket, dis, dos));
//        assertEquals(viewerTracker.activeViewers.size(), 1);
//    }
//
//    @Test
//    public void testRemove() throws ViewerTrackerException {
//        viewerTracker.Add(new ViewerHandler(socket, dis, dos));
//        assertEquals(viewerTracker.activeViewers.size(), 1);
//        viewerTracker.Add(new ViewerHandler(socket, dis, dos));
//        assertEquals(viewerTracker.activeViewers.size(), 2);
//
//        assertEquals(viewerTracker.activeViewers.size(), 0);
//    }
//
//    @Test
//    public void testSameUUID() throws ViewerTrackerException {
//        viewerTracker.Add(new ViewerHandler(socket, dis, dos));
//        assertEquals(viewerTracker.activeViewers.size(), 1);
//        assertThrows(ViewerTrackerException.class, () -> {
//            viewerTracker.Add(new ViewerHandler(socket, dis, dos));
//        });
//    }
}
