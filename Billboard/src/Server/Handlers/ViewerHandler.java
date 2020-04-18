package Server.Handlers;

import Tools.Log;
import Tools.ObjectStreamer;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Random;

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
        byte[] b = new byte[200];
        new Random().nextBytes(b);
        try {
           db.billboards.addBillboardName("Billboard 1", "Test 1", "test1info", "data", b, "red", "Blue", "black");

     /*       db.users.AddUser("test1", "password1", 1, hashCredentials.CreateSalt());
            db.users.AddUser("test2", "password2", 2, hashCredentials.CreateSalt());
            db.users.AddUser("test3", "password3", 3, hashCredentials.CreateSalt());
            System.out.println(db.users.GetUserSalt("test1"));
            db.users.EditUser("test1", "password4", 4, hashCredentials.CreateSalt());
            //db.DeleteUser("test2");
            System.out.println(db.users.GetUserSalt("test1"));
*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.Disconnect();

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