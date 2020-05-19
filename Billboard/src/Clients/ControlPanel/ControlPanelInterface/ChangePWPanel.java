package Clients.ControlPanel.ControlPanelInterface;

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
        tf_old.setBounds(190, 100, 300, 50);
        tf_old.setFont(font);
        passwordPanel.add(tf_old);

        JLabel lbl_new = new JLabel("New Password");
        lbl_new.setBounds(10, 150, 300, 50);
        lbl_new.setFont(font);
        passwordPanel.add(lbl_new);

        JPasswordField tf_new = new JPasswordField();
        tf_new.setBounds(190, 150, 300, 50);
        tf_new.setFont(font);
        passwordPanel.add(tf_new);

        JLabel lbl_confirm = new JLabel("Confirm Password");
        lbl_confirm.setBounds(10, 200, 300, 50);
        lbl_confirm.setFont(font);
        passwordPanel.add(lbl_confirm);

        JPasswordField tf_confirm = new JPasswordField();
        tf_confirm.setBounds(190, 200, 300, 50);
        tf_confirm.setFont(font);
        passwordPanel.add(tf_confirm);

        JButton b_confirm = new JButton("Confirm");
        b_confirm.setBounds(190, 300, 150, 30);
        passwordPanel.add(b_confirm);

        JButton b_clear = new JButton("Clear");
        b_clear.setBounds(340, 300, 150, 30);
        passwordPanel.add(b_clear);

        JLabel lbl_message = new JLabel("");
        lbl_message.setBounds(190, 350, 300, 50);
        lbl_message.setFont(font);
        passwordPanel.add(lbl_message);

        b_confirm.addActionListener(event -> {
            String oldPassword = new String(tf_old.getPassword());
            String newPass = new String(tf_new.getPassword());
            String confirmPassword = new String(tf_confirm.getPassword());
            if (oldPassword.equals("") || newPass.equals("") || confirmPassword.equals((""))) {
                lbl_message.setText("All fields must be populated");
                return;
            }
            if (!newPass.equals(confirmPassword)) {
                tf_old.setText("");
                tf_new.setText("");
                tf_confirm.setText("");
                lbl_message.setText("New password fields must match");
                return;
            }
            Log.Confirmation("New password fields match");
            oldPassword = HashCredentials.Hash(oldPassword);
            user.setAction("changePassword");
            if (AttemptConnect()) {
                // Try a login attempt
                try {
                    // Send user object to server
                    objectStreamer.Send(user);
                    dos.writeUTF(oldPassword);
                    boolean success = dis.readBoolean();
                    if (success) {
                        Log.Confirmation("Password check successful");
                        String newPassword = new String(tf_new.getPassword());
                        newPassword = HashCredentials.Hash(newPassword);
                        dos.writeUTF(newPassword);
                        // Await returned object from server
                        success = dis.readBoolean();
                        if (success) {
                            Log.Confirmation("Password changed successfully");
                            lbl_message.setText("Password changed");
                        } else {
                            Log.Error("Error when changing password");
                            lbl_message.setText("Unable to change password");
                        }
                    } else {
                        Log.Confirmation("Password check failed");
                        lbl_message.setText("Existing password incorrect");
                    }
                    // Disconnect from server
                    AttemptDisconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.Error("Server unavailable");
                }
            }
            tf_old.setText("");
            tf_new.setText("");
            tf_confirm.setText("");
        });


        b_clear.addActionListener(event -> {
            tf_old.setText("");
            tf_new.setText("");
            tf_confirm.setText("");
            lbl_message.setText("");
        });
    }
}
