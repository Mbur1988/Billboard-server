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
    private User user;

    /**
     * Class constructor
     *
     * @param socket the socket reference to use
     * @param dis    the existing data input stream
     * @param dos    the existing data output stream
     */
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
        user = new User();
        mariaDB = new MariaDB(true, false, false);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        mariaDB.Connect();

        Log.Message(socket + " control panel handler started");

        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamer objectStreamer = new ObjectStreamer(socket);

        Log.Message("Login attempt received from control panel");

        try {
            user = (User)objectStreamer.Receive();
            AttemptAuthentication();
            objectStreamer.Send(user);

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
        mariaDB.Disconnect();
    }

    private User AttemptAuthentication() {
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            byte[] salt = mariaDB.users.GetUserSalt(username);
            String toCheck = HashCredentials.Hash(password, salt);
            if(salt != null) {
                Log.Message(salt.toString());
                Log.Message(HashCredentials.Hash(HashCredentials.Hash("default"), salt));
                Log.Message(toCheck);
                Log.Message(mariaDB.users.GetUserPassword(username));
            }

            if (toCheck == mariaDB.users.GetUserPassword(username)) {
                user.setVerified(true);
                UUID uuid = UUID.randomUUID();
                authorised.Add(username, uuid);
                user.setId(uuid);
                Log.Message(user.getId().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}