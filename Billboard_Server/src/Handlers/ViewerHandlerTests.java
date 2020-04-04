package Handlers;

import java.io.*;
import java.net.*;
import javax.net.SocketFactory;

public class ViewerHandlerTests {

    private ViewerHandler viewerHandler;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private SocketFactory factory;

//    @BeforeEach
//    public void newClientHandler() throws IOException {
//        InputStream is = new ByteArrayInputStream("hi I am test".getBytes(Charset.forName("UTF-8")));
//        dis = new DataInputStream(is);
//        factory = SSLSocketFactory.getDefault();
//        socket = factory.createSocket();
//    }
//
//    @Test
//    public void testConstructor() {
//        viewerHandler = new ViewerHandler(socket, dis, dos);
//        assertEquals(dis, viewerHandler.dis);
//        assertEquals(dos, viewerHandler.dos);
//        assertEquals(socket, viewerHandler.socket);
//    }

//    @Test
//    public void testConstructorFail() throws IOException {
//        viewerHandler = new ViewerHandler(socket, dis, dos);
//        InputStream is = new ByteArrayInputStream("hi I am test".getBytes(Charset.forName("UTF-8")));
//        dis = new DataInputStream(is);
//        factory = SSLSocketFactory.getDefault();
//        socket = factory.createSocket();
//        uuid = UUID.randomUUID().toString();
//        assertNotEquals(dis, viewerHandler.dis);
//        assertNotEquals(socket, viewerHandler.socket);
//    }
}
