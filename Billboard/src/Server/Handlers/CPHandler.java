package Server.Handlers;

import SerializableObjects.User;
import Server.Trackers.Authorised;
import Tools.HashCredentials;
import Tools.Log;
import Tools.ObjectStreamer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.UUID;
import static Server.Server.mariaDB;

public class CPHandler extends ConnectionHandler {

    private ObjectStreamer objectStreamer;
    private User user;

    /**
     * Class constructor
     * @param socket the socket reference to use
     * @param dis    the existing data input stream
     * @param dos    the existing data output stream
     */
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        // Update inherited variables
        super(socket, dis, dos);
        // Create new user class to check user access
        user = new User();
        // Create a new ObjectStreamHandler to send and receive objects
        objectStreamer = new ObjectStreamer(socket);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket + " control panel handler started");
        // Attempt to communicate with the control panel
        try {
            user = (User) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            // If the current control panel user is verified then handle the requested action
            if (user.isVerified() && Authorised.Check(user.getUsername(), user.getId())) {
                // Next, receive action command from control panel
                switch(user.getAction()) {
                    // Handle requested action
                    case ("userExit"):
                        UserExit();
                        break;
                    case ("addUser"):
                        AddNewUser();
                        break;
                    case ("editUser"):
                        EditUser();
                        break;
                    case ("deleteUser"):
                        DeleteUser();
                        break;
                    case ("changePassword"):
                        ChangePassword();
                        break;
                    case ("addNewBillboard"):
                        AddNewBillboard();
                        break;
                    case ("editBillboard"):
                        EditBillboard();
                        break;
                    case ("deleteBillboard"):
                        DeleteBillboard();
                        break;
                    case ("addNewSchedule"):
                        AddNewSchedule();
                        break;
                    case ("editSchedule"):
                        EditSchedule();
                        break;
                    case ("deleteSchedule"):
                        DeleteSchedule();
                        break;
                }
            }
            // If the current control panel user is not verified then check credential validity
            else if (!user.isVerified() && user.getAction().equals("loginAttempt")) {
                Log.Message("Login attempt received from control panel");
                AttemptAuthentication();
                objectStreamer.Send(user);
                Log.Message("User object sent to control panel");
            }
            // Close connection to control panel nicely
            socket.close();
            this.dis.close();
            this.dos.close();
            Log.Confirmation(socket.toString() + " closed successfully");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to validate the current user class instance.
     * The password is salt-hashed using the salt stored in the database for that user.
     * Once salt-hashed, if the password matches that stored in the database for that user then the user is validated
     * and a confirmation log message will be printed to console.
     * Validation comprises; setting the user verified boolean to true, assigning a random UUID to the user and
     * updating the access level. The assigned UUID and username is also added to the 'authorised' list of the server.
     * If the credentials of the current user can not be validated then a warning log entry is printed to console.
     * Regardless of whether the user is validated or not, the password variable of the current user instance will be
     * cleared for security.
     */
    private void AttemptAuthentication() {
        // Get login credentials from user instance
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            // get the relevant salt for the user from the database
            byte[] salt = mariaDB.users.GetUserSalt(username);
            // salt-hash the password using the relevant salt
            String toCheck = HashCredentials.Hash(password, salt);
            // check whether the salt-hashed password matches that stored on the database
            if (toCheck.equals(mariaDB.users.GetUserPassword(username))) {
                // if passwords match then validate user and update authorised list
                user.setVerified(true);
                UUID uuid = UUID.randomUUID();
                Authorised.Add(username, uuid);
                user.setId(uuid);
                user.setAccess(mariaDB.users.GetUserAccess(username));
                // print confirmation log message
                Log.Confirmation("User credentials validated");
            }
            else {
                // user could not be validated - print warning log message
                Log.Warning("User credentials could not be validated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // clear user password variable for security
        user.setPassword("");
    }

    private void UserExit() {

    }

    /**
     * Method to add a new user to the users table in the database
     */
    private void AddNewUser() {
        try {
            User newUser = (User) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            byte[] salt = HashCredentials.CreateSalt();
            String password = HashCredentials.Hash(newUser.getPassword(), salt);
            dos.writeBoolean(mariaDB.users.AddUser(
                    newUser.getUsername(),
                    password,
                    newUser.getAccess(),
                    salt));
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void EditUser() {

    }

    private void DeleteUser() {

    }

    private void ChangePassword() {

    }

    private void AddNewBillboard() {

    }

    private void EditBillboard() {

    }

    private void DeleteBillboard() {

    }

    private void AddNewSchedule() {

    }

    private void EditSchedule() {

    }

    private void DeleteSchedule() {

    }
}