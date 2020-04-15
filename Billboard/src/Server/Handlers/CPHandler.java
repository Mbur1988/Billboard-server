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

public class CPHandler extends ConnectionHandler {

    MariaDB mariaDB;

    /**
     * Class constructor
     *
     * @param socket the socket reference to use
     * @param dis    the existing data input stream
     * @param dos    the existing data output stream
     */
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
        mariaDB = new MariaDB();
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket + " control panel handler started");

        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamer objectStreamer = new ObjectStreamer(socket);

        Log.Message("Login attempt received from control panel");

        try {
            User user = (User) objectStreamer.Receive();
            AttemptAuthentication(user);
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
    }

    private User AttemptAuthentication(User user) {
        String username = user.getUsername();
        try {
            byte[] salt = mariaDB.users.GetUserSalt(username);
            String password = HashCredentials.Hash(username, salt);
            if (password == mariaDB.users.GetUserPassword(username)) {
                user.setVerified(true);
                UUID uuid = UUID.randomUUID();
                authorised.Add(username, uuid);
                user.setId(uuid);
                Log.Message(user.getId().toString());
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }
}