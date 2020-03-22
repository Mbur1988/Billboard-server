package Server;

import java.io.*;
import java.net.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class ClientTrackerTests {

    private ClientTracker clientTracker = new ClientTracker();;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String uuid1 = UUID.randomUUID().toString();
    private String uuid2 = UUID.randomUUID().toString();

    @BeforeEach
    public void clearClientTracker() {
        clientTracker.activeClients.clear();
    }

    @Test
    public void testAdd() throws ClientTrackerException {
        assertEquals(clientTracker.activeClients.size(), 0);
        clientTracker.Add(uuid1, new ClientHandler(socket, dis, dos, uuid1));
        assertEquals(clientTracker.activeClients.size(), 1);
    }

    @Test
    public void testRemove() throws ClientTrackerException {
        clientTracker.Add(uuid1, new ClientHandler(socket, dis, dos, uuid1));
        assertEquals(clientTracker.activeClients.size(), 1);
        clientTracker.Add(uuid2, new ClientHandler(socket, dis, dos, uuid2));
        assertEquals(clientTracker.activeClients.size(), 2);
        clientTracker.Remove(uuid1);
        assertEquals(clientTracker.activeClients.size(), 1);
        clientTracker.Remove(uuid2);
        assertEquals(clientTracker.activeClients.size(), 0);
    }

    @Test
    public void testSameUUID() throws ClientTrackerException {
        clientTracker.Add(uuid1, new ClientHandler(socket, dis, dos, uuid1));
        assertEquals(clientTracker.activeClients.size(), 1);
        assertThrows(ClientTrackerException.class, () -> {
            clientTracker.Add(uuid1, new ClientHandler(socket, dis, dos, uuid1));
        });
    }
}
