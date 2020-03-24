package Server.ViewerHandler;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.UUID;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ViewerHandlerTests {

    private ViewerHandler viewerHandler;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
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
        viewerHandler = new ViewerHandler(socket, dis, dos, uuid);
        assertEquals(dis, viewerHandler.dis);
        assertEquals(dos, viewerHandler.dos);
        assertEquals(socket, viewerHandler.socket);
        assertEquals(uuid, viewerHandler.uuid);
    }

    @Test
    public void testConstructorFail() throws IOException {
        viewerHandler = new ViewerHandler(socket, dis, dos, uuid);
        InputStream is = new ByteArrayInputStream("hi I am test".getBytes(Charset.forName("UTF-8")));
        dis = new DataInputStream(is);
        factory = SSLSocketFactory.getDefault();
        socket = factory.createSocket();
        uuid = UUID.randomUUID().toString();
        assertNotEquals(dis, viewerHandler.dis);
        assertNotEquals(socket, viewerHandler.socket);
        assertNotEquals(uuid, viewerHandler.uuid);
    }
}
