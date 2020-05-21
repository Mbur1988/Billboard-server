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

public class EditUsersPanel extends ControlPanelInterface {

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

    public static void editUserScreen() {

        editUserPanel.setLayout(null);

        // Add title labels
        JLabel label_editUser = new JLabel("User details");
        label_editUser.setBounds(0,0,350,50);
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(label_editUser);

        JLabel lbl_users = new JLabel("List of all users");
        lbl_users.setBounds(screenWidth/2 - 100, 0, 400, 50);
        lbl_users.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(lbl_users);

        // Add labels
        JLabel lbl_username = new JLabel("Username");
        lbl_password = new JLabel("Password");
        JLabel lbl_confirm = new JLabel("Confirm Password");
        JLabel lbl_privileges = new JLabel("Privileges");
        lbl_message = new JLabel("");

        addLabel(editUserPanel, lbl_username, 10, 100, 300, 50);
        addLabel(editUserPanel, lbl_password, 10, 150, 300, 50);
        addLabel(editUserPanel, lbl_confirm, 10, 200, 300, 50);
        addLabel(editUserPanel, lbl_privileges, 10, 250, 300, 50);
        addLabel(editUserPanel, lbl_message, 190, 500, 340, 50);

        // Add text fields
        tf_username = new JTextField();
        addTextfield(editUserPanel, tf_username, 190, 105, 300, 40);

        // Add password fields
        tf_password = new JPasswordField();
        tf_confirm = new JPasswordField();

        addPasswordField(editUserPanel, tf_password, 190, 155, 300, 40);
        addPasswordField(editUserPanel, tf_confirm, 190, 205, 300, 40);

        // Add checkboxes
        cb_createNew = new JCheckBox("Create New Billboard");
        cb_editBoard = new JCheckBox("Edit Existing Billboard");
        cb_schedule = new JCheckBox("Schedule Billboards");
        cb_editUsers = new JCheckBox("Edit Users");

        addCheckBox(editUserPanel, cb_createNew, 190, 250, 300, 50);
        addCheckBox(editUserPanel, cb_editBoard, 190, 300, 300, 50);
        addCheckBox(editUserPanel, cb_schedule, 190, 350, 300, 50);
        addCheckBox(editUserPanel, cb_editUsers, 190, 400, 300, 50);

        // Add default list model
        model = new DefaultListModel();
        model.addAll(lists.users);

        // Add JList
        list = new JList(model);

        // Add JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth/2 - 100, 100, 300, 350);
        editUserPanel.add(scrollPane);

        // Add buttons
        b_add = new JButton("Add");
        b_save = new JButton("Save");
        JButton b_clear = new JButton("Clear");
        JButton b_load = new JButton("Edit");
        JButton b_delete = new JButton("Delete");

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

    private static void addUser() {
        String username = tf_username.getText();
        String password = String.valueOf(tf_password.getPassword());
        String confirm = String.valueOf(tf_confirm.getPassword());
        if (username.equals("") || password.equals("") || confirm.equals((""))) {
            lbl_message.setText("All fields must be populated");
            return;
        }
        if (!password.equals(confirm)) {
            resetFields();
            lbl_message.setText("Password fields must match");
            return;
        }
        password = HashCredentials.Hash(password);
        int access = UserAccess.bool2dec(
                cb_createNew.isSelected(),
                cb_editBoard.isSelected(),
                cb_schedule.isSelected(),
                cb_editUsers.isSelected());
        User newUser = new User(username, password, access);
        user.setAction("addUser");
        // Attempt connection to server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                objectStreamer.Send(newUser);
                // Await returned object from server
                if (dis.readBoolean()) {
                    lists.users.add(newUser.getUsername());
                    Collections.sort(lists.users);
                    model.clear();
                    model.addAll(lists.users);
                    lbl_message.setText("User added");
                    Log.Confirmation("New user added successfully");
                }
                else {
                    lbl_message.setText("User already exists");
                    Log.Error("Error when attempting to add new user");
                }
            }
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
        resetFields();
    }

    private static void saveUser() {
        String password = String.valueOf(tf_password.getPassword());
        String confirm = String.valueOf(tf_confirm.getPassword());
        if (!password.equals(confirm)) {
            resetFields();
            lbl_message.setText("Password fields must match");
            return;
        }
        int access = UserAccess.bool2dec(
                cb_createNew.isSelected(),
                cb_editBoard.isSelected(),
                cb_schedule.isSelected(),
                cb_editUsers.isSelected());
        if (access == user.getAccess() && password.equals("")) { return; }
        if (!password.equals("")) {
            password = HashCredentials.Hash(password);
        }
        user.setAction("editUser");
        // Attempt connection to server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                dos.writeUTF(tf_username.getText());
                dos.writeUTF(password);
                dos.write(access);
                // Await returned object from server
                if (dis.readBoolean()) {
                    lbl_message.setText("User edited");
                    Log.Confirmation("User edited successfully");
                    user.setAccess(access);
                }
                else {
                    lbl_message.setText("User not edited");
                    Log.Error("Error when attempting to edit user");
                }
            }
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
        b_save.setVisible(false);
        b_add.setVisible(true);
        resetFields();
    }

    private static void clearFields() {
        b_save.setVisible(false);
        b_add.setVisible(true);
        resetFields();
        lbl_message.setText("");
    }

    private static void loadUser() {
        String username = (String) list.getSelectedValue();
        user.setAction("getAccess");
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                dos.writeUTF(username);
                boolean[] key = UserAccess.dec2bool(dis.read());
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
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Failed to retrieve user credentials");
            }
            // Disconnect from server
            AttemptDisconnect();
            lbl_message.setText("");
        }
        // Post message to user if unable to connect to server
        else {
            Log.Error("Unable to connect to server");
        }
    }

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
                dos.writeUTF(username);
                if (dis.readBoolean()) {
                    lists.users.remove(username);
                    model.removeElement(username);
                    lbl_message.setText("User deleted");
                    Log.Confirmation("User successfully deleted");
                }
                else {
                    lbl_message.setText("User not deleted");
                    Log.Error("Error when attempting to delete user");
                }
            }
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
        resetFields();
    }

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
