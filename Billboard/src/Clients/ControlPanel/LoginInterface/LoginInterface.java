package Clients.ControlPanel.LoginInterface;

import Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface;
import SerializableObjects.User;
import Tools.HashCredentials;
import Tools.Log;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import static Clients.ControlPanel.ControlPanel.*;

// The Billboard Control Panel Login Interface
public class LoginInterface {

    public static void loginScreen() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // May be able to use a dialog window instead of a frame. *****

        JFrame loginScreen = new JFrame(); // Login window.
        loginScreen.setSize(screenWidth, screenHeight);

        JLabel labelMessage = new JLabel(); // Testing label that displays input at bottom of window.
        labelMessage.setBounds((screenWidth / 2) - 100, screenHeight / 2 + 60, 200, 30);
        labelMessage.setHorizontalAlignment(JLabel.CENTER);

        JLabel label_Password = new JLabel("Password:");    // Password label.
        label_Password.setFont(new Font("Courier", Font.BOLD, 20));
        label_Password.setHorizontalAlignment(JLabel.CENTER);
        label_Password.setVerticalAlignment(JLabel.CENTER);
        label_Password.setBounds((screenWidth / 2) - 80, screenHeight / 2, 160, 20);

        JPasswordField pw = new JPasswordField(); // Password entry field.
        pw.setBounds((screenWidth / 2) - 50, screenHeight / 2 + 30, 100, 30);

        JLabel label_Username = new JLabel("Username:"); // Username label.
        label_Username.setFont(new Font("Courier", Font.BOLD, 20));
        label_Username.setHorizontalAlignment(JLabel.CENTER);
        label_Username.setVerticalAlignment(JLabel.CENTER);
        label_Username.setBounds((screenWidth / 2) - 80, (screenHeight / 2) - 100, 160, 20);

        JTextField un = new JTextField(); // Username entry field.
        un.setBounds((screenWidth / 2) - 50, (screenHeight / 2) - 60, 100, 30);

        JButton b_Login = new JButton("Login"); // Login button.
        b_Login.setBounds((screenWidth / 2) - 50, screenHeight / 2 + 100, 100, 30);

        // Add elements to the screen.
        loginScreen.add(pw);
        loginScreen.add(label_Username);
        loginScreen.add(labelMessage);
        loginScreen.add(label_Password);
        loginScreen.add(b_Login);
        loginScreen.add(un);

        loginScreen.setLayout(null);
        loginScreen.setUndecorated(true);
        loginScreen.setVisible(true);

        b_Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // Retrieve username and password from user input
                user.setUsername(un.getText());
                String password = new String(pw.getPassword());
                try {
                    password = HashCredentials.Hash(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                user.setPassword(password);
                // Clear username and password fields
                un.setText("");
                pw.setText("");
                // Attempt connection to server
                if (AttemptConnect()) {
                    // Try a login attempt
                    try {
                        // Send user object to server
                        objectStreamer.Send(user);
                        Log.Message("User object sent to server");
                        // Await returned object from server
                        user = (User)objectStreamer.Receive();
                        Log.Message("User object received from server");
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        Log.Error("Login attempt request failed");
                    }
                    // Disconnect from server
                    AttemptDisconnect();
                    user.showDetails();
                    // Check whether the user has been verified
                    if (user.isVerified() && user.getId() != null) {
                        // Open control panel screen
                        ControlPanelInterface.controlPanelScreen();
                        // Nicely close login screen
                        loginScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        loginScreen.dispose();
                    }
                    // Post message to user if username of password was incorrect
                    else {
                        labelMessage.setText("Incorrect username or password");
                    }
                }
                // Post message to user if unable to connect to server
                else {
                    labelMessage.setText("Unable to connect to server");
                }
            }
        });
    }

}
