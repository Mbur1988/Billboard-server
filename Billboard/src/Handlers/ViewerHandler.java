package Handlers;

import Tools.HashCredentials;
import Tools.Log;
import java.io.*;
import java.net.Socket;
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

    /////////// remember to remove
    HashCredentials hashCredentials = new HashCredentials();

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket + " viewer handler started");

        MariaDB db = new MariaDB();
        db.Connect();
        try {
            db.AddUser("test1", "password1", 1, hashCredentials.CreateSalt());
            db.AddUser("test2", "password2", 2, hashCredentials.CreateSalt());
            db.AddUser("test3", "password3", 3, hashCredentials.CreateSalt());
            System.out.println(db.GetUserSalt("test1"));
            db.EditUser("test1", "password4", 4, hashCredentials.CreateSalt());
            //db.DeleteUser("test2");
            System.out.println(db.GetUserSalt("test1"));

        } catch (SQLException e) {
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