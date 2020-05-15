package Clients.ControlPanel.ControlPanelInterface;

import SerializableObjects.User;
import Tools.HashCredentials;
import Tools.Log;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static Clients.ControlPanel.ControlPanel.*;

public class ChangePWPanel extends ControlPanelInterface {

    public static void changePWScreen() {

        passwordPanel.setLayout(null);

        JLabel label_editUser = new JLabel("Change Password");
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        label_editUser.setBounds(0,0,450,60);
        passwordPanel.add(label_editUser);

        Font font = new Font("Courier", Font.PLAIN, 20);

        JLabel lbl_old = new JLabel("Current Password");
        lbl_old.setBounds(10, 100, 300, 50);
        lbl_old.setFont(font);
        passwordPanel.add(lbl_old);

        JPasswordField tf_old = new JPasswordField();
        tf_old.setBounds(250, 100, 300, 50);
        tf_old.setFont(font);
        passwordPanel.add(tf_old);

        JLabel lbl_new = new JLabel("New Password");
        lbl_new.setBounds(10, 150, 300, 50);
        lbl_new.setFont(font);
        passwordPanel.add(lbl_new);

        JPasswordField tf_new = new JPasswordField();
        tf_new.setBounds(250, 150, 300, 50);
        tf_new.setFont(font);
        passwordPanel.add(tf_new);

        JLabel lbl_confirm = new JLabel("Confirm New Password");
        lbl_confirm.setBounds(10, 200, 300, 50);
        lbl_confirm.setFont(font);
        passwordPanel.add(lbl_confirm);

        JPasswordField tf_confirm = new JPasswordField();
        tf_confirm.setBounds(250, 200, 300, 50);
        tf_confirm.setFont(font);
        passwordPanel.add(tf_confirm);

        JButton b_confirm = new JButton("Confirm");
        b_confirm.setBounds(250, 300, 150, 30);
        passwordPanel.add(b_confirm);

        JButton b_clear = new JButton("Clear");
        b_clear.setBounds(400, 300, 150, 30);
        passwordPanel.add(b_clear);

        JLabel lbl_message = new JLabel("");
        lbl_message.setBounds(250, 350, 300, 50);
        lbl_message.setFont(font);

        b_confirm.addActionListener(event -> {
            if (!tf_new.getPassword().equals(tf_confirm.getPassword())) {
                tf_old.setText("");
                tf_new.setText("");
                tf_confirm.setText("");
                lbl_message.setText("New password fields must match");
                passwordPanel.add(lbl_message);
                return;
            }
            tf_old.setText("");
            tf_new.setText("");
            tf_confirm.setText("");
            String password = new String(tf_new.getPassword());
            password = HashCredentials.Hash(password);
            User newUser = new User(user.getUsername(), password, user.getAccess());
            user.setAction("editUser");
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
                        Log.Confirmation("Password changed successfully");
                        lbl_message.setText("Password changed");
                        passwordPanel.add(lbl_message);
                    }
                    else {
                        Log.Error("Error when changing password");
                        lbl_message.setText("Unable to change password");
                        passwordPanel.add(lbl_message);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Log.Error("Password change failed");
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
            tf_old.setText("");
            tf_new.setText("");
            tf_confirm.setText("");
            passwordPanel.remove(lbl_message);
        });
    }
}
