package Handlers;

import SerializableObjects.User;
import java.io.*;
import java.net.Socket;

public class ViewerHandler extends ConnectionHandler {

    /**
     * Class constructor
     * @param socket the socket reference to use
     * @param dis the existing data input stream
     * @param dos the existing data output stream
     */
    public ViewerHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamHandler stream = new ObjectStreamHandler(socket);
        while (true)
        {
            // Attempt to send and receive data and object streams and handle any exceptions
            try {
                // Create new user class to send as a test
                User user = new User("test", "test");
                // Send newly created user class
                stream.Send(user);

                // Receive class from the viewer
                // Note: the Receive method will hang if there is no object waiting to be received on the socket
                Object received = stream.Receive();
                // If statement to identify the received object as an instance of User class
                if (received instanceof User)
                {
                    // Cast the received object to its correct class
                    User test = (User) received;
                    // Not that the object is cast to its correct class, it's methods can be used
                    test.showDetails();
                }

                // Test data input and output streams
                dos.writeUTF("handshake from server");
                System.out.println(dis.readUTF());

                // Close connection nicely
                socket.close();
                dis.close();
                dos.close();
                break;
            }

            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}