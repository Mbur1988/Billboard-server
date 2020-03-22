package Server;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class ClientHandlerTests {

    private ClientHandler clientHandler;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private String uuid;
    private SocketFactory factory;

    @BeforeEach
    public void newClientHandler() throws IOException {
        InputStream is = new ByteArrayInputStream("hi I am test".getBytes(Charset.forName("UTF-8")));
        dis = new DataInputStream(is);
        factory = SSLSocketFactory.getDefault();
        socket = factory.createSocket();
        uuid = UUID.randomUUID().toString();
    }

    @Test
    public void testConstructor() {
        clientHandler = new ClientHandler(socket, dis, dos, uuid);
        assertEquals(dis, clientHandler.dis);
        assertEquals(dos, clientHandler.dos);
        assertEquals(socket, clientHandler.socket);
        assertEquals(uuid, clientHandler.uuid);
    }

    @Test
    public void testConstructorFail() throws IOException {
        clientHandler = new ClientHandler(socket, dis, dos, uuid);
        InputStream is = new ByteArrayInputStream("hi I am test".getBytes(Charset.forName("UTF-8")));
        dis = new DataInputStream(is);
        factory = SSLSocketFactory.getDefault();
        socket = factory.createSocket();
        uuid = UUID.randomUUID().toString();
        assertNotEquals(dis, clientHandler.dis);
        assertNotEquals(socket, clientHandler.socket);
        assertNotEquals(uuid, clientHandler.uuid);
    }
}
