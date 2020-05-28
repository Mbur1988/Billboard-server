package Server.Handlers;

import SerializableObjects.*;
import Server.Trackers.Authorised;
import Tools.HashCredentials;
import Tools.Log;
import Tools.ObjectStreamer;
import Tools.UserAccess;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
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
            // If the current control panel user is not verified then check credential validity
            if (!user.isVerified() && user.getAction().equals("LoginAttempt")) {
                Log.Message("Login attempt received from control panel");
                AttemptLogin();
                objectStreamer.Send(user);
                Log.Message("User object sent to control panel");
                if (user.isVerified()) {
                    String request = dis.readUTF();
                    if (request.equals("List Users Billboards")) {
                        ListUsersBillboards();
                        request = dis.readUTF();
                    }
                    if (request.equals("List Billboards")) {
                        ListBillboards();
                        request = dis.readUTF();
                    }
                    if (request.equals("List Schedules")) {
                        ListSchedules();
                        request = dis.readUTF();
                    }
                    if (request.equals("List Users")) {
                        ListUsers();
                    }
                }
            }
            // If the current control panel user is verified then handle the requested action
            else if (user.isVerified() && Authorised.Check(user.getUsername(), user.getId())) {
                // Next, receive action command from control panel
                switch(user.getAction()) {
                    // Handle requested action
                    case ("List Users Billboards"):
                        ListUsersBillboards();
                        break;
                    case ("List Billboards"):
                        ListBillboards();
                        break;
                    case ("Get Billboard Information"):
                        GetBillboardInformation();
                        break;
                    case ("Create Billboard"):
                        CreateBillboard();
                        break;
                    case ("Edit Billboard"):
                        EditBillboard();
                        break;
                    case ("Delete Billboard"):
                        DeleteBillboard();
                        break;
                    case ("List Schedules"):
                        ListSchedules();
                        break;
                    case ("View Schedule"):
                        ViewSchedule();
                        break;
                    case ("Schedule Billboard"):
                        ScheduleBillboard();
                        break;
                    case ("Remove Billboard"):
                        RemoveBillboard();
                        break;
                    case ("List Users"):
                        ListUsers();
                        break;
                    case ("Create User"):
                        CreateUser();
                        break;
                    case ("Edit User"):
                        EditUser();
                        break;
                    case ("Get User Permissions"):
                        GetUserPermissions();
                        break;
                    case ("Set User Password"):
                        SetUserPassword();
                        break;
                    case ("Delete User"):
                        DeleteUser();
                        break;
                    case ("Log Out"):
                        LogOut();
                        break;
                }
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

    private void ListUsersBillboards() {
        try {
            boolean[] access = UserAccess.dec2bool(user.getAccess());
            if (!access[0]) {
                sendFalse();
                return;
            }
            sendTrue();
            ArrayList<String> list = (mariaDB.billboards.getAllBillboardsCurrent(user.getUsername()));
            objectStreamer.Send(list);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void ListBillboards() {
        try {
            ArrayList<String> list = (mariaDB.billboards.getAllBillboards());
            objectStreamer.Send(list);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an existing billboard from the database and sends it to the control panel
     */
    private void GetBillboardInformation() {
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
     * Adds a new billboard to the database
     */
    private void CreateBillboard() {
        try {
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            Billboard newBillboard = (Billboard) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            // to create a billboard, must have “Create Billboards” permission
            if (Access[0]) {
                dos.writeBoolean(mariaDB.billboards.AddBillboard(newBillboard));
            }
            else {
                sendFalse();
            }
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Saves changes to an existing billboard
     */
    private void EditBillboard() {
        try {
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            Billboard newBillboard = (Billboard) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            // To edit own billboard, as long as it is not currently scheduled, must have “Create Billboards” permission
            if (user.getUsername() == newBillboard.getCreatedBy() && !newBillboard.getScheduled() && Access[0]) {
                dos.writeBoolean(mariaDB.billboards.edit(newBillboard));
            }
            // To edit a billboard that is currently scheduled, must have “Edit All Billboards” permission
            else if (newBillboard.getScheduled() && Access[1]) {
                dos.writeBoolean(mariaDB.billboards.edit(newBillboard));
            }
            // To edit another user’s billboard, must have “Edit All Billboards” permission
            else if (user.getUsername() != newBillboard.getCreatedBy() && Access[1]) {
                dos.writeBoolean(mariaDB.billboards.edit(newBillboard));
            }
            else {
                sendFalse();
            }
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Deletes a billboard
     */
    private void DeleteBillboard() {
        try {
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            String name = dis.readUTF();
            Log.Message("String data received from control panel");
            Billboard billboard = mariaDB.billboards.getBillboard(name);
            // To delete any other billboards, including those currently scheduled, must have “Edit All Billboards” permission.
            if (billboard.getScheduled() && Access[1]) {
                dos.writeBoolean(mariaDB.billboards.DeleteBillboard(name));
                return;
            }
            // To edit another user’s billboard, must have “Edit All Billboards” permission
            else if (user.getUsername() != billboard.getCreatedBy() && Access[1]) {
                dos.writeBoolean(mariaDB.billboards.DeleteBillboard(name));
                return;
            } else if (!billboard.getScheduled() && Access[0]) {
                sendFalse();
                dos.writeBoolean(mariaDB.billboards.DeleteBillboard(name));
            } else {
                sendFalse();
            }
        }
        catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    private void ListSchedules() {
        try {
            boolean[] access = UserAccess.dec2bool(user.getAccess());
            if (!access[2]) {
                sendFalse();
                return;
            }
            sendTrue();
            ArrayList<String> list = (mariaDB.scheduling.getAllSchedules());
            objectStreamer.Send(list);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void ViewSchedule() {
    }

    /**
     * Adds a new schedule to the database
     */
    private void ScheduleBillboard() {
        try {
            Schedule newSchedule = (Schedule) objectStreamer.Receive();
            Log.Message("User object received from control panel");
            dos.writeBoolean(mariaDB.scheduling.AddSchedule(newSchedule));
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    /**
     * Deletes and existing schedule from the database
     */
    private void RemoveBillboard() {

    }

    private void ListUsers() {
        try {
            boolean[] access = UserAccess.dec2bool(user.getAccess());
            if (!access[3]) {
                sendFalse();
                return;
            }
            sendTrue();
            ArrayList<String> list = (mariaDB.users.getAllUsernames());
            objectStreamer.Send(list);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add a new user to the users table in the database
     */
    private void CreateUser() {
        try {
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            if (!Access[3]) {
                sendFalse();
                return;
            }
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
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            if (!Access[3]) {
                sendFalse();
                return;
            }
            String username = dis.readUTF();
            String password = dis.readUTF();
            access = dis.read();
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
     * Retrieves the access level of a user and sends it to the control panel
     */
    private void GetUserPermissions() {
        try {
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            if (!Access[3]) {
                sendFalse();
                return;
            }
            dos.write(mariaDB.users.getAccess(dis.readUTF()));
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change an existing users password
     */
    private void SetUserPassword() {
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
                sendFalse();
            }
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
            int access = mariaDB.users.getAccess(user.getUsername());
            boolean[] Access = UserAccess.dec2bool(access);
            if (!Access[3]) {
                sendFalse();
                return;
            }
            String received = dis.readUTF();
            Log.Message("String data received from control panel");
            dos.writeBoolean(mariaDB.users.delete(received));
        }
        catch (IOException | SQLException e) {
            sendFalse();
            e.printStackTrace();
        }
    }

    private void LogOut() {
        Authorised.Remove(user.getUsername());
        try {
            dos.writeBoolean(!Authorised.Check(user.getUsername(), user.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Sends a true boolean over data output stream to indicate that an action has failed
     */
    private void sendTrue() {
        try {
            dos.writeBoolean(true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}