package Handlers;

import Tools.HashCredentials;
import Tools.Log;
import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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

        MariaDB db = new MariaDB();
        db.Connect();
        try {
            String password = HashCredentials.Hash("default");
            password = HashCredentials.Hash(password, db.GetUserSalt("admin"));
            System.out.println(password);
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        }
        db.Disconnect();
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
            Log.Confirmation(socket + " closed successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}