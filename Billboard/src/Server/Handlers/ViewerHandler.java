package Server.Handlers;

import SerializableObjects.Billboard;
import Tools.Log;
import Tools.ObjectStreamer;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import static Server.Server.mariaDB;

public class ViewerHandler extends ConnectionHandler {

    private ObjectStreamer objectStreamer;

    /**
     * Class constructor
     * @param socket the socket reference to use
     * @param dis the existing data input stream
     * @param dos the existing data output stream
     */
    public ViewerHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
        objectStreamer = new ObjectStreamer(socket);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket + " viewer handler started");
        Billboard billboard = null;
        try {
            billboard = mariaDB.getCurrentBillboard();
            if (billboard == null) {
                dos.writeBoolean(false);
            }
            else {
                dos.writeBoolean(true);
                objectStreamer.Send(billboard);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }



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