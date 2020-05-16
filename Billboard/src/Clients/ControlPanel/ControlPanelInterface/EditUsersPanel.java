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

    private static JTextField tf_username;
    private static JTextField tf_password;
    private static JCheckBox cb_createNew;
    private static JCheckBox cb_editBoard;
    private static JCheckBox cb_schedule;
    private static JCheckBox cb_editUsers;

    public static void editUserScreen() {

        editUserPanel.setLayout(null);

        JLabel label_editUser = new JLabel("User details");
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        label_editUser.setBounds(0,0,350,50);
        editUserPanel.add(label_editUser);

        Font font = new Font("Courier", Font.PLAIN, 20);

        JLabel lbl_username = new JLabel("Username");
        lbl_username.setBounds(10, 100, 300, 50);
        lbl_username.setFont(font);
        editUserPanel.add(lbl_username);

        tf_username = new JTextField();
        tf_username.setBounds(150, 100, 300, 50);
        tf_username.setFont(font);
        editUserPanel.add(tf_username);

        JLabel lbl_password = new JLabel("Password");
        lbl_password.setBounds(10, 150, 300, 50);
        lbl_password.setFont(font);
        editUserPanel.add(lbl_password);

        tf_password = new JTextField();
        tf_password.setBounds(150, 150, 300, 50);
        tf_password.setFont(font);
        editUserPanel.add(tf_password);

        JLabel lbl_privileges = new JLabel("Privileges");
        lbl_privileges.setBounds(10, 200, 300, 50);
        lbl_privileges.setFont(font);
        editUserPanel.add(lbl_privileges);

        cb_createNew = new JCheckBox("Create New Billboard");
        cb_createNew.setBounds(150, 200, 300, 50);
        cb_createNew.setFont(font);
        editUserPanel.add(cb_createNew);

        cb_editBoard = new JCheckBox("Edit Existing Billboard");
        cb_editBoard.setBounds(150, 250, 300, 50);
        cb_editBoard.setFont(font);
        editUserPanel.add(cb_editBoard);

        cb_schedule = new JCheckBox("Schedule Billboards");
        cb_schedule.setBounds(150, 300, 300, 50);;
        cb_schedule.setFont(font);
        editUserPanel.add(cb_schedule);

        cb_editUsers = new JCheckBox("Edit Users");
        cb_editUsers.setBounds(150, 350, 300, 50);
        cb_editUsers.setFont(font);
        editUserPanel.add(cb_editUsers);

        JLabel lbl_users = new JLabel("List of all users");
        lbl_users.setBounds(screenWidth/2, 0, 400, 50);
        lbl_users.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(lbl_users);

        DefaultListModel model = new DefaultListModel();
        model.addAll(lists.users);

        // This forms the list and adds it.
        JList list = new JList(model);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth/2, 100, 300, 300);
        editUserPanel.add(scrollPane);

        JButton b_add = new JButton("Add User");
        b_add.setBounds(150, 400, 150, 30);
        editUserPanel.add(b_add);

        JButton b_save = new JButton("Save User");
        b_save.setBounds(150, 400, 150, 30);

        JButton b_clear = new JButton("Clear");
        b_clear.setBounds(300, 400, 150, 30);
        editUserPanel.add(b_clear);

        JButton b_load = new JButton("Edit User");
        b_load.setBounds(screenWidth/2, 400, 150, 30);
        editUserPanel.add(b_load);

        JButton b_delete = new JButton("Delete User");
        b_delete.setBounds(screenWidth/2 + 150, 400, 150, 30);
        editUserPanel.add(b_delete);

        b_add.addActionListener(event -> {
            String password = tf_password.getText();
            password = HashCredentials.Hash(password);
            int access = UserAccess.bool2dec(
                    cb_createNew.isSelected(),
                    cb_editBoard.isSelected(),
                    cb_schedule.isSelected(),
                    cb_editUsers.isSelected());
            User newUser = new User(tf_username.getText(), password, access);
            user.setAction("addUser");

            // Attempt connection to server
            if (AttemptConnect()) {
                // Try a login attempt
                try {
                    // Send user object to server
                    objectStreamer.Send(user);
                    objectStreamer.Send(newUser);
                    // Await returned object from server
                    boolean userAdded = dis.readBoolean();
                    if (userAdded) {
                        lists.users.add(newUser.getUsername());
                        Collections.sort(lists.users);
                        model.clear();
                        model.addAll(lists.users);
                        Log.Confirmation("New user added successfully");
                        resetFields();
                    }
                    else {
                        Log.Error("Error when attempting to add new user");
                        resetFields();
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
        });

        b_save.addActionListener(event -> {

        });

        b_clear.addActionListener(event -> {
            editUserPanel.remove(b_save);
            editUserPanel.add(b_add);
            resetFields();
        });

        b_delete.addActionListener(event -> {
            String username = (String) list.getSelectedValue();
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
                        Log.Confirmation("User successfully deleted");
                    }
                    else {
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
        });

        b_load.addActionListener(event -> {

        });
    }

    private static void resetFields() {
        tf_username.setEnabled(true);
        tf_username.setText("");
        tf_password.setText("");
        cb_createNew.setSelected(false);
        cb_editBoard.setSelected(false);
        cb_schedule.setSelected(false);
        cb_editUsers.setSelected(false);
    }
}
