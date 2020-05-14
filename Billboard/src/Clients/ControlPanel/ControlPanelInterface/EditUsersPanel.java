package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.UserAccess;
import SerializableObjects.User;
import Tools.HashCredentials;
import Tools.Log;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static Clients.ControlPanel.ControlPanel.*;

public class EditUsersPanel extends ControlPanelInterface {

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

        JTextField tf_username = new JTextField();
        tf_username.setBounds(150, 100, 300, 50);
        tf_username.setFont(font);
        editUserPanel.add(tf_username);

        JLabel lbl_password = new JLabel("Password");
        lbl_password.setBounds(10, 150, 300, 50);
        lbl_password.setFont(font);
        editUserPanel.add(lbl_password);

        JTextField tf_password = new JTextField();
        tf_password.setBounds(150, 150, 300, 50);
        tf_password.setFont(font);
        editUserPanel.add(tf_password);

        JLabel lbl_privileges = new JLabel("Privileges");
        lbl_privileges.setBounds(10, 200, 300, 50);
        lbl_privileges.setFont(font);
        editUserPanel.add(lbl_privileges);

        JCheckBox cb_createNew = new JCheckBox("Create New Billboard");
        cb_createNew.setBounds(150, 200, 300, 50);
        cb_createNew.setFont(font);
        editUserPanel.add(cb_createNew);

        JCheckBox cb_editBoard = new JCheckBox("Edit Existing Billboard");
        cb_editBoard.setBounds(150, 250, 300, 50);
        cb_editBoard.setFont(font);
        editUserPanel.add(cb_editBoard);

        JCheckBox cb_schedule = new JCheckBox("Schedule Billboards");
        cb_schedule.setBounds(150, 300, 300, 50);;
        cb_schedule.setFont(font);
        editUserPanel.add(cb_schedule);

        JCheckBox cb_editUsers = new JCheckBox("Edit Users");
        cb_editUsers.setBounds(150, 350, 300, 50);
        cb_editUsers.setFont(font);
        editUserPanel.add(cb_editUsers);

        JLabel lbl_users = new JLabel("List of all users");
        lbl_users.setBounds(screenWidth/2, 0, 400, 50);
        lbl_users.setFont(new Font("Courier", Font.PLAIN, 50));
        editUserPanel.add(lbl_users);

        // Build a list of all the users.
        DefaultListModel<String> list_allUsers = new DefaultListModel<>();
        list_allUsers.addElement("user1");
        list_allUsers.addElement("user2");
        list_allUsers.addElement("user3");
        list_allUsers.addElement("user4");
        list_allUsers.addElement("user5");
        list_allUsers.addElement("user6");
        list_allUsers.addElement("user7");

        // This forms the list and adds it.
        JList<String> list = new JList<>(list_allUsers);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(screenWidth/2, 100, 300, 300);
        editUserPanel.add(scrollPane);

        JButton b_add = new JButton("Add User");
        b_add.setBounds(150, 400, 150, 30);
        editUserPanel.add(b_add);

        JButton b_save = new JButton("Add User");
        b_save.setBounds(150, 400, 150, 30);
        //editUserPanel.add(b_save);

        JButton b_clear = new JButton("Clear");
        b_clear.setBounds(300, 400, 150, 30);
        editUserPanel.add(b_clear);

        JButton b_load = new JButton("Edit User");
        b_load.setBounds(screenWidth/2, 400, 150, 30);
        editUserPanel.add(b_load);

        JButton b_delete = new JButton("Delete User");
        b_delete.setBounds(screenWidth/2 + 150, 400, 150, 30);
        editUserPanel.add(b_delete);

        b_save.addActionListener(event -> {
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
                        Log.Confirmation("New user added successfully");
                    }
                    else {
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
        });

        b_clear.addActionListener(event -> {
            editUserPanel.remove(b_save);
            editUserPanel.add(b_add);
            tf_username.setEnabled(true);
            tf_username.setText("");
            tf_password.setText("");
            cb_createNew.setSelected(false);
            cb_editBoard.setSelected(false);
            cb_schedule.setSelected(false);
            cb_editUsers.setSelected(false);
        });
    }
}
