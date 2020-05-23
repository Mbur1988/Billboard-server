package Clients.ControlPanel.ControlPanelInterface;

import Tools.HashCredentials;
import Tools.Log;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

class ChangePWPanel extends ControlPanelInterface {

    // Variables required by the class
    private static JPasswordField tf_old;
    private static JPasswordField tf_new;
    private static JPasswordField tf_confirm;
    private static JLabel lbl_message;

    /**
     * The main method of the change password class populates the GUI page with all required objects
     */
    protected static void changePWScreen() {

        passwordPanel.setLayout(null);

        // Add title label
        // Title label for change password details
        JLabel label_editUser = new JLabel("Change Password");
        label_editUser.setFont(new Font("Courier", Font.PLAIN, 50));
        label_editUser.setBounds(0, 0, 450, 60);
        passwordPanel.add(label_editUser);

        // Create labels
        JLabel lbl_old = new JLabel("Current Password");
        JLabel lbl_new = new JLabel("New Password");
        JLabel lbl_confirm = new JLabel("Confirm Password");
        lbl_message = new JLabel("");

        // Add labels to the panel
        addLabel(passwordPanel, lbl_old, 10, 100, 300, 50);
        addLabel(passwordPanel, lbl_new, 10, 150, 300, 50);
        addLabel(passwordPanel, lbl_confirm, 10, 200, 300, 50);
        addLabel(passwordPanel, lbl_message, 190, 350, 300, 50);

        // Create password fields
        tf_old = new JPasswordField();
        tf_new = new JPasswordField();
        tf_confirm = new JPasswordField();

        // Add password fields to the panel
        addPasswordField(passwordPanel, tf_old, 190, 105, 300, 40);
        addPasswordField(passwordPanel, tf_new, 190, 155, 300, 40);
        addPasswordField(passwordPanel, tf_confirm, 190, 205, 300, 40);

        // Create buttons
        JButton b_confirm = new JButton("Confirm");
        JButton b_clear = new JButton("Clear");

        // Add buttons to panel
        addButton(passwordPanel, b_confirm, 190, 300, 150, 30);
        addButton(passwordPanel, b_clear, 340, 300, 150, 30);

        // Handle button press events
        b_confirm.addActionListener(event -> confirm());
        b_clear.addActionListener(event -> clearAll());
    }

    /**
     * Changes the users password
     */
    private static void confirm() {
        // retrieve user input from text fields
        String oldPassword = new String(tf_old.getPassword());
        String newPass = new String(tf_new.getPassword());
        String confirmPassword = new String(tf_confirm.getPassword());
        // check that all fields are populated
        if (oldPassword.equals("") || newPass.equals("") || confirmPassword.equals((""))) {
            lbl_message.setText("All fields must be populated");
            return;
        }
        // check that the new password matches the confirmed password string
        if (!newPass.equals(confirmPassword)) {
            // if new password fields dont match then clear fields, display user message and return
            tf_old.setText("");
            tf_new.setText("");
            tf_confirm.setText("");
            lbl_message.setText("New password fields must match");
            return;
        }
        Log.Confirmation("New password fields match");
        // hash the new password
        oldPassword = HashCredentials.Hash(oldPassword);
        // set the action request to the server
        user.setAction("changePassword");
        // attempt connection to the server
        if (AttemptConnect()) {
            // Try a login attempt
            try {
                // Send user object to server
                objectStreamer.Send(user);
                // hash and send the old password to the server
                dos.writeUTF(oldPassword);
                // receive whether the old password entered by the user is valid
                boolean success = dis.readBoolean();
                // if the old password is correct then attempt password change
                if (success) {
                    Log.Confirmation("Password check successful");
                    // hash and send the new password to the server
                    String newPassword = new String(tf_new.getPassword());
                    newPassword = HashCredentials.Hash(newPassword);
                    dos.writeUTF(newPassword);
                    // Await confirmation from the server
                    success = dis.readBoolean();
                    // display user message whether the password was changed successfully or not
                    if (success) {
                        Log.Confirmation("Password changed successfully");
                        lbl_message.setText("Password changed");
                    } else {
                        Log.Error("Error when changing password");
                        lbl_message.setText("Unable to change password");
                    }
                }
                // if the old password entered by the user is incorrect then send message to the user
                else {
                    Log.Confirmation("Password check failed");
                    lbl_message.setText("Existing password incorrect");
                }
                // Disconnect from server
                AttemptDisconnect();
            }
            // catch any unanticipated exceptions and print to console
            catch (IOException e) {
                e.printStackTrace();
                Log.Error("Server unavailable");
            }
        }
        // clear fields
        clear();
    }

    /**
     * Clears all fields including the message to the user
     */
    private static void clearAll() {
        clear();
        lbl_message.setText("");
    }

    /**
     * clears all user input fields
     */
    private static void clear() {
        tf_old.setText("");
        tf_new.setText("");
        tf_confirm.setText("");
    }
}