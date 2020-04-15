package Tools;

import SerializableObjects.User;
import Tools.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStreamer {

    // Declare class variables
    private Socket socket;

    /**
     * Class constructor
     * @param socket the socket reference to use
     */
    public ObjectStreamer(Socket socket) {
        this.socket = socket;
    }

    /**
     * Sends a serializable object over a socket connection using an ObjectOutputStream
     * @param object the serializable object to send
     * @throws IOException
     */
    public void Send(Object object) throws IOException {
        Log.Message("Attempting to send object on " + socket);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(object);
        oos.flush();
        Log.Confirmation("Object sent on " + socket);
    }

    /**
     * Receives a serializable object over a socket connection using an ObjectInputStream
     * @return the received serializable object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object Receive() throws IOException, ClassNotFoundException {
        Log.Message("Attempting to receive object on " + socket);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Log.Confirmation("Object received on " + socket);
        return ois.readObject();
    }
}

/*
    ///// This is an example using this class /////

    // Create new User class to send as a test
    User user = new User("test", "test");

    // Send newly created user class
    stream.Send(user);

    // Receive class
    // Note: the Receive method will hang if there is no object waiting to be received on the socket
    Object received = stream.Receive();

    // If statement to identify the received object as an instance of User class
    if(received instanceof User) {

        // Cast the received object to its correct class
        User test = (User) received;

        // Not that the object is cast to its correct class, it's methods can be used
        test.showDetails();
    }
*/
