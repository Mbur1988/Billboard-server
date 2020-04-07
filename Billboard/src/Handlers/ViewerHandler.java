package Handlers;

import Tools.Log;
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
        Log.Message(socket.toString() + " viewer handler started");

        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamHandler stream = new ObjectStreamHandler(socket);

        //
        // Send currently scheduled billboard to client here
        //

        // Close connection nicely
        try {
            socket.close();
            dis.close();
            dos.close();
            Log.Confirmation(socket.toString() + " closed successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}