package Server.Handlers;

import SerializableObjects.User;
import Tools.Log;
import Tools.ObjectStreamer;

import java.io.*;
import java.net.Socket;

public class CPHandler extends ConnectionHandler {

    /**
     * Class constructor
     *
     * @param socket the socket reference to use
     * @param dis    the existing data input stream
     * @param dos    the existing data output stream
     */
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket + " control panel handler started");

        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamer objectStreamer = new ObjectStreamer(socket);

        Log.Message("Login attempt received from control panel");

        Object received = null;
        try {
            received = objectStreamer.Receive();
            // If statement to identify the received object as an instance of User class
            if (received instanceof User) {
                // Cast the received object to its correct class
                User user = (User) received;
                user.setVerified(true);
                objectStreamer.Send(user);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Close connection nicely
        try {
            socket.close();
            this.dis.close();
            this.dos.close();
            Log.Confirmation(socket.toString() + " closed successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}