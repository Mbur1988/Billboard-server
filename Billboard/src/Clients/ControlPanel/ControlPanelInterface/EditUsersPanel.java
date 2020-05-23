package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.UserAccess;
import SerializableObjects.User;
import Tools.HashCredentials;
import Tools.Log;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

class EditUsersPanel extends ControlPanelInterface {

    // Variables required by the class
    private static JLabel lbl_password;
    private static JLabel lbl_message;
    private static JTextField tf_username;
    private static JPasswordField tf_password;
    private static JPasswordField tf_confirm;
    private static JCheckBox cb_createNew;
    private static JCheckBox cb_editBoard;
    private static JCheckBox cb_schedule;
    private static JCheckBox cb_editUsers;
    private static DefaultListModel model;
    private static JList list;
    private static JButton b_add;
    private static JButton b_save;

    /**
     * The main method of the edit user class populates the GUI page with all required objects
     */
    protected static void editUserScreen() {

        editUserPanel.setLayout(null);

        // Add title labels
        // Title label for user details
        JLabel label_editUser = new JLabel("User details");
        label_editUser.setBounds(0,0,350,50);
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(label_editUser);

        // Title label for the list of users
        JLabel lbl_users = new JLabel("List of all users");
        lbl_users.setBounds(screenWidth/2 - 100, 0, 400, 50);
        lbl_users.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(lbl_users);

        // Create labels
        JLabel lbl_username = new JLabel("Username");
        lbl_password = new JLabel("Password");
        JLabel lbl_confirm = new JLabel("Confirm Password");
        JLabel lbl_privileges = new JLabel("Privileges");
        lbl_message = new JLabel("");

        // Add labels to the panel
        addLabel(editUserPanel, lbl_username, 10, 100, 300, 50);
        addLabel(editUserPanel, lbl_password, 10, 150, 300, 50);
        addLabel(editUserPanel, lbl_confirm, 10, 200, 300, 50);
        addLabel(editUserPanel, lbl_privileges, 10, 250, 300, 50);
        addLabel(editUserPanel, lbl_message, 190, 500, 340, 50);

        // Create text field
        tf_username = new JTextField();
        // Add text field to the panel
        addTextfield(editUserPanel, tf_username, 190, 105, 300, 40);

        // Create password fields
        tf_password = new JPasswordField();
        tf_confirm = new JPasswordField();

        // Add password field to the panel
        addPasswordField(editUserPanel, tf_password, 190, 155, 300, 40);
        addPasswordField(editUserPanel, tf_confirm, 190, 205, 300, 40);

        // Create checkboxes
        cb_createNew = new JCheckBox("Create New Billboard");
        cb_editBoard = new JCheckBox("Edit Existing Billboard");
        cb_schedule = new JCheckBox("Schedule Billboards");
        cb_editUsers = new JCheckBox("Edit Users");

        // Add checkboxes to the panel
        addCheckBox(editUserPanel, cb_createNew, 190, 250, 300, 50);
        addCheckBox(editUserPanel, cb_editBoard, 190, 300, 300, 50);
        addCheckBox(editUserPanel, cb_schedule, 190, 350, 300, 50);
        addCheckBox(editUserPanel, cb_editUsers, 190, 400, 300, 50);

        // Create and add a default list model
        model = new DefaultListModel();
        model.addAll(lists.users);

        // Create a new JList
        list = new JList(model);

        // Create and add JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth/2 - 100, 100, 300, 350);
        editUserPanel.add(scrollPane);

        // Create buttons
        b_add = new JButton("Add");
        b_save = new JButton("Save");
        JButton b_clear = new JButton("Clear");
        JButton b_load = new JButton("Edit");
        JButton b_delete = new JButton("Delete");

        // Add buttons to panel
        addButton(editUserPanel, b_add, 190, 450, 150, 30);
        addButton(editUserPanel, b_save, 190, 450, 150, 30);
        b_save.setVisible(false);
        addButton(editUserPanel, b_clear, 340, 450, 150, 30);
        addButton(editUserPanel, b_load, screenWidth/2 - 100, 450, 150, 30);
        addButton(editUserPanel, b_delete, screenWidth/2 + 50, 450, 150, 30);

        // Handle button events
        b_add.addActionListener(event -> addUser());
        b_save.addActionListener(event -> saveUser());
        b_clear.addActionListener(event -> clearFields());
        b_load.addActionListener(event -> loadUser());
        b_delete.addActionListener(event -> deleteUser());
    }

    /**
     * Adds a new user to the database
     */
    private static void addUser() {
        // get user input
        String username = tf_username.getText();
        String password = String.valueOf(tf_password.getPassword());
        String confirm = String.valueOf(tf_confirm.getPassword());
        // Check that all fields are populated
        if (username.equals("") || password.equals("") || confirm.equals((""))) {
            lbl_message.setText("All fields must be populated");
            return;
        }
        // check that the password fields match
        if (!password.equals(confirm)) {
            resetFields();
            lbl_message.setText("Password fields must match");
            return;
        }
        // hash the password
        password = HashCredentials.Hash(password);
        // get access level as integer
        int access = UserAccess.bool2dec(
                cb_createNew.isSelected(),
                cb_editBoard.isSelected(),
                cb_schedule.isSelected(),
                cb_editUsers.isSelected());
        // populate the user class instance
        User newUser = new User(username, password, access);
        // set the action request to the server
        user.setAction("addUser");
        // attempt connection to the server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // Send new user object to server
                objectStreamer.Send(newUser);
                // Await confirmation that the user was added successfully
                if (dis.readBoolean()) {
                    // add new user to the list of the current user's and re-sort it alphabetically
                    lists.users.add(newUser.getUsername());
                    Collections.sort(lists.users);
                    model.clear();
                    model.addAll(lists.users);
                    // display confirmation message to the user and post log confirmation
                    lbl_message.setText("User added");
                    Log.Confirmation("New user added successfully");
                }
                // If user not added then display message to the user
                else {
                    lbl_message.setText("User already exists");
                    Log.Error("Error when attempting to add new user");
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Add user attempt request failed");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
        // clear all user input fields
        resetFields();
    }

    /**
     * Saves any changes made to an existing user
     */
    private static void saveUser() {
        // get user input
        String password = String.valueOf(tf_password.getPassword());
        String confirm = String.valueOf(tf_confirm.getPassword());
        // check that the password fields match
        if (!password.equals(confirm)) {
            resetFields();
            lbl_message.setText("Password fields must match");
            return;
        }
        // get access level as integer
        int access = UserAccess.bool2dec(
                cb_createNew.isSelected(),
                cb_editBoard.isSelected(),
                cb_schedule.isSelected(),
                cb_editUsers.isSelected());
        // return if there are no changes to be made
        if (access == user.getAccess() && password.equals("")) { return; }
        // ensure there is a password entered and hash it
        if (!password.equals("")) {
            password = HashCredentials.Hash(password);
        }
        // set the action request to the server
        user.setAction("editUser");
        // Attempt connection to server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the username of the user to edit
                dos.writeUTF(tf_username.getText());
                // send the password to edit
                dos.writeUTF(password);
                // send the access level to edit
                dos.write(access);
                // Await confirmation from server
                if (dis.readBoolean()) {
                    // display confirmation message to the user and post log confirmation
                    lbl_message.setText("User edited");
                    Log.Confirmation("User edited successfully");
                    user.setAccess(access);
                }
                // If user not edited then display message to the user
                else {
                    lbl_message.setText("User not edited");
                    Log.Error("Error when attempting to edit user");
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Edit user attempt request failed");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
        // set add button visible
        b_save.setVisible(false);
        b_add.setVisible(true);
        // clear all user input fields
        resetFields();
    }

    /**
     * method used to reset the user input fields, clear the message and set add button visible
     */
    private static void clearFields() {
        b_save.setVisible(false);
        b_add.setVisible(true);
        resetFields();
        lbl_message.setText("");
    }

    /**
     * Loads the credentials of the selected user from the database via the server
     */
    private static void loadUser() {
        String username = (String) list.getSelectedValue();
        user.setAction("getAccess");
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the username of the user to load
                dos.writeUTF(username);
                // read the access level of the user
                boolean[] key = UserAccess.dec2bool(dis.read());
                // populate user inout fields with the credentials of the user
                tf_username.setEnabled(false);
                tf_username.setText(username);
                tf_password.setText("");
                cb_createNew.setSelected(key[0]);
                cb_editBoard.setSelected(key[1]);
                cb_schedule.setSelected(key[2]);
                cb_editUsers.setSelected(key[3]);
                b_add.setVisible(false);
                b_save.setVisible(true);
                lbl_password.setText("New Password");
                cb_editUsers.setEnabled(!username.equals(user.getUsername()));
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Failed to retrieve user credentials");
            }
            // Disconnect from server
            AttemptDisconnect();
            // clear message to the user
            lbl_message.setText("");
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
    }

    /**
     * Deletes the selected user from the database provided that it is not scheduled
     */
    private static void deleteUser() {
        String username = (String) list.getSelectedValue();
        if (username.equals(user.getUsername())) {
            lbl_message.setText("Unable to delete your own account");
            return;
        }
        user.setAction("deleteUser");
        // Attempt connection to server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // send the username of the user to delete
                dos.writeUTF(username);
                // await confirmation that the user has been successfully deleted
                if (dis.readBoolean()) {
                    // remove user from list
                    lists.users.remove(username);
                    model.removeElement(username);
                    // display message to the user
                    lbl_message.setText("User deleted");
                    Log.Confirmation("User successfully deleted");
                }
                // Post message to user if unable to delete user
                else {
                    lbl_message.setText("User not deleted");
                    Log.Error("Error when attempting to delete user");
                }
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Failed to delete user");
            }
            // Disconnect from server
            AttemptDisconnect();
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
        // clear all user input fields
        resetFields();
    }

    /**
     * reset the user input fields to their initial status
     */
    private static void resetFields() {
        tf_username.setEnabled(true);
        tf_username.setText("");
        tf_password.setText("");
        tf_confirm.setText("");
        cb_createNew.setSelected(false);
        cb_editBoard.setSelected(false);
        cb_schedule.setSelected(false);
        cb_editUsers.setSelected(false);
        cb_editUsers.setEnabled(true);
        lbl_password.setText("Password");
    }
}
