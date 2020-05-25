package Server.Handlers;

import SerializableObjects.Billboard;
import SerializableObjects.Lists;
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

    /**
     * Override of the run function of parent class
     */
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
                        Authorised.Remove(user.getUsername());
                        break;
                    case ("addUser"):
                        AddUser();
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
                    case ("getAccess"):
                        GetAccess();
                        break;
                    case ("addBillboard"):
                        AddBillboard();
                        break;
                    case ("getBillboard"):
                        GetBillboard();
                        break;
                    case ("saveBillboard"):
                        SaveBillboard();
                        break;
                    case ("deleteBillboard"):
                        DeleteBillboard(false);
                        break;
                    case ("delBillboard"):
                        DeleteBillboard(true);
                        break;
                    case ("addSchedule"):
                        AddSchedule();
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
                AttemptLogin();
                objectStreamer.Send(user);
                Log.Message("User object sent to control panel");
                if (user.isVerified()) {
                    Lists lists = new Lists(mariaDB.users.getAllUsernames(),
                            mariaDB.billboards.getAllBillboards(),
                            mariaDB.billboards.getAllBillboardsCurrent(user.getUsername()),
                            null);
                    objectStreamer.Send(lists);
                    Log.Message("Lists object sent to control panel");
                }
            }
            // Close connection to control panel nicely
            socket.close();
            this.dis.close();
            this.dos.close();
            Log.Confirmation(socket.toString() + " closed successfully");
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
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
    private void AttemptLogin() {
        // Get login credentials from user instance
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            // get the relevant salt for the user from the database
            byte[] salt = mariaDB.users.getSalt(username);
            // salt-hash the password using the relevant salt
            String toCheck = HashCredentials.Hash(password, salt);
            // check whether the salt-hashed password matches that stored on the database
            if (toCheck.equals(mariaDB.users.getPassword(username))) {
                // if passwords match then validate user and update authorised list
                UUID uuid = UUID.randomUUID();
                Authorised.Add(username, uuid);
                user.setId(uuid);
                user.setAccess(mariaDB.users.getAccess(username));
                user.setVerified(true);
                // print confirmation log message
                Log.Confirmation("User credentials validated");
            }
            else {
                // user could not be validated - print warning log message
                user.setVerified(false);
                Log.Error("User credentials could not be validated");
            }
        } catch (SQLException e) {
            user.setVerified(false);
            e.printStackTrace();
        }
        // clear user password variable for security
        user.setPassword("");
    }

    /**
     * Method to add a new user to the users table in the database
     */
    private void AddUser() {
        try {
            User newUser = (User) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            byte[] salt = HashCredentials.CreateSalt();
            String password = HashCredentials.Hash(newUser.getPassword(), salt);
            dos.writeBoolean(mariaDB.users.add(
                    newUser.getUsername(),
                    password,
                    newUser.getAccess(),
                    salt));
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Method to edit an existing user in the database
     */
    private void EditUser() {
        try {
            String username = dis.readUTF();
            String password = dis.readUTF();
            int access = dis.read();
            boolean confirm = true;
            if (!password.equals("")) {
                byte[] salt = HashCredentials.CreateSalt();
                password = HashCredentials.Hash(password, salt);
                confirm = mariaDB.users.edit(username, password, salt);
            }
            dos.writeBoolean(mariaDB.users.edit(username, access) && confirm);
        } catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Method to delete an existing user from the database
     */
    private void DeleteUser() {
        try {
            String received = dis.readUTF();
            Log.Message("String data received from control panel");
            dos.writeBoolean(mariaDB.users.delete(received));
        }
        catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Method to change an existing users password
     */
    private void ChangePassword() {
        try {
            String username = user.getUsername();
            String password = dis.readUTF();
            Log.Confirmation("message received from control panel");
            byte[] salt = mariaDB.users.getSalt(username);
            String toCheck = HashCredentials.Hash(password, salt);
            if (toCheck.equals(mariaDB.users.getPassword(username))) {
                Log.Confirmation("password correct");
                dos.writeBoolean(true);
                salt = HashCredentials.CreateSalt();
                password = dis.readUTF();
                Log.Confirmation("message received from control panel");
                password = HashCredentials.Hash(password, salt);
                dos.writeBoolean(mariaDB.users.edit(user.getUsername(), password, salt));
            }
            else {
                Log.Confirmation("password incorrect");
                dos.writeBoolean(false);
            }
        } catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the access level of a user and sends it to the control panel
     */
    private void GetAccess() {
        try {
            dos.write(mariaDB.users.getAccess(dis.readUTF()));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new billboard to the database
     */
    private void AddBillboard() {
        try {
            Billboard newBillboard = (Billboard) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            dos.writeBoolean(mariaDB.billboards.AddBillboard(newBillboard));
        }
        catch (IOException | ClassNotFoundException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Gets an existing billboard from the database and sends it to the control panel
     */
    private void GetBillboard() {
        try {
            String name = dis.readUTF();
            Billboard billboard = mariaDB.billboards.getBillboard(name);
            boolean confirm = (billboard != null);
            dos.writeBoolean(confirm);
            if (confirm) {
                objectStreamer.Send(billboard);
            }
        } catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Saves changes to an existing billboard
     */
    private void SaveBillboard() {
        try {
            Billboard newBillboard = (Billboard) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            dos.writeBoolean(mariaDB.billboards.edit(newBillboard));
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Deletes a billboard
     * @param skip boolean true to skip scheduled check
     */
    private void DeleteBillboard(boolean skip) {
        try {
            String name = dis.readUTF();
            Log.Message("String data received from control panel");
            if (skip) {
                dos.writeBoolean(mariaDB.billboards.DeleteBillboard(name));
                return;
            }
            // check to see if billboard is scheduled
            boolean scheduled = mariaDB.billboards.getBillboardSchedule(name);
            dos.writeBoolean(scheduled);
            if (!scheduled) {
                dos.writeBoolean(mariaDB.billboards.DeleteBillboard(name));
            }
        }
        catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Adds a new schedule to the database
     */
    private void AddSchedule() {

    }

    /**
     * Edits an existing schedule from the database
     */
    private void EditSchedule() {

    }

    /**
     * Deletes and existing schedule from the database
     */
    private void DeleteSchedule() {

    }

    /**
     * Sends a false boolean over data output stream to indicate that an action has failed
     */
    private void sendFalse() {
        try {
            dos.writeBoolean(false);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}