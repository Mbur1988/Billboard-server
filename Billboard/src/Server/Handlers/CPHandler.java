package Server.Handlers;

import SerializableObjects.User;
import Tools.HashCredentials;
import Tools.Log;
import Tools.ObjectStreamer;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

import static Server.Server.authorised;
import static Server.Server.main;

public class CPHandler extends ConnectionHandler {

    private MariaDB mariaDB;
    private ObjectStreamer objectStreamer;
    private User user;

    /**
     * Class constructor
     *
     * @param socket the socket reference to use
     * @param dis    the existing data input stream
     * @param dos    the existing data output stream
     */
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        // Update inherited variables
        super(socket, dis, dos);
        // Create new user class to check user access
        user = new User();
        // Create new database handler for communication with the database
        mariaDB = new MariaDB(true, false, false);
        // Create a new ObjectStreamHandler to send and receive objects
        objectStreamer = new ObjectStreamer(socket);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket + " control panel handler started");
        // Connect to the database
        mariaDB.Connect();
        // Attempt to communicate with the control panel
        try {
            user = (User) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            // If the current control panel user is verified then handle the requested action
            if (user.isVerified()) {
                // Must first check users uuid is valid
                // Next, receive action command from control panel
                // Handle requested action
            }
            // If the current control panel user is not verified then check credential validity
            else {
                Log.Message("Login attempt received from control panel");
                AttemptAuthentication();
                objectStreamer.Send(user);
                Log.Message("User object sent to control panel");
            }
            // Close connection to database
            mariaDB.Disconnect();
            // Close connection to control panel nicely
            socket.close();
            this.dis.close();
            this.dos.close();
            Log.Confirmation(socket.toString() + " closed successfully");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private User AttemptAuthentication() {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            byte[] salt = mariaDB.users.GetUserSalt(username);
            String toCheck = HashCredentials.Hash(password, salt);
            if (toCheck.equals(mariaDB.users.GetUserPassword(username))) {
                user.setVerified(true);
                UUID uuid = UUID.randomUUID();
                authorised.Add(username, uuid);
                user.setId(uuid);
                user.setAccess(mariaDB.users.GetUserAccess(username));
                Log.Message("User credentials validated");
            }
            else {
                Log.Message("User credentials could not be validated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setPassword("");
        return user;
    }
}