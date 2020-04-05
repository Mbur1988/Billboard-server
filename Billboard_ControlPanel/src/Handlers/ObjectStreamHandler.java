package Handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStreamHandler {

    // Declare class variables
    private Socket socket;

    /**
     * Class constructor
     * @param socket the socket reference to use
     */
    public ObjectStreamHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Sends a serializable object over a socket connection using an ObjectOutputStream
     * @param object the serializable object to send
     * @throws IOException
     */
    public void Send(Object object) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(object);
        oos.flush();
    }

    /**
     * Receives a serializable object over a socket connection using an ObjectInputStream
     * @return the received serializable object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object Receive() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return ois.readObject();
    }
}