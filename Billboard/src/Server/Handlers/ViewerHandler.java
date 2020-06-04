package Server.Handlers;

import SerializableObjects.Billboard;
import Tools.Log;
import Tools.ObjectStreamer;
import Tools.ProjectPath;

import java.awt.*;
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
                billboard = new Billboard("No billboard scheduled",
                        "Error Billboard",
                        "Nothing Scheduled",
                        null,
                        billboard.ConvertImageToData(
                                ProjectPath.RootString() + "\\Resources\\Images\\Oops.jpg"),
                        Color.red,
                        Color.white,
                        Color.red,
                        null,
                        null);
            }
            objectStreamer.Send(billboard);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

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