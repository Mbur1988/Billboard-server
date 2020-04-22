package Server.Handlers;

import Tools.Log;
import Tools.ObjectStreamer;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Random;
import static Server.Server.mariaDB;

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
        Log.Message(socket + " viewer handler started");




        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamer stream = new ObjectStreamer(socket);

        //
        // Send currently scheduled billboard to client here
        //

        // Close connection nicely
        try {
            socket.close();
            dis.close();
            dos.close();
            Log.Confirmation(socket + " closed successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}