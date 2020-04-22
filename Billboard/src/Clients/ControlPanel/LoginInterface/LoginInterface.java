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
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenHeight;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;

import Clients.ControlPanel.ControlPanelTools.Tools;

// The Billboard Control Panel Login Interface

public class LoginInterface {

    public static JFrame loginScreen = new JFrame(); // Login window.

    public static void loginScreen() {

        Tools.addExitButton(screenWidth - 105, screenHeight - 35, 100, 30);

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int screenWidth = screenSize.width;
//        int screenHeight = screenSize.height;

        loginScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginScreen.setResizable(false);

        JLabel labelMessage = new JLabel(); // Testing label that displays input at bottom of window.
        labelMessage.setBounds((screenWidth/2) - 100, screenHeight/2 + 60, 200, 30);
        labelMessage.setHorizontalAlignment(JLabel.CENTER);

        Tools.addLabel_frame(LoginInterface.loginScreen, "lbl_pw", "Password:", (screenWidth/2)-80,
                screenHeight/2, 160, 20, "Courier", 1, 20, 0, 0);

//        JLabel label_Password = new JLabel("Password:");    // Password label.
//        label_Password.setFont(new Font("Courier", Font.BOLD, 20));
//        label_Password.setHorizontalAlignment(JLabel.CENTER);
//        label_Password.setVerticalAlignment(JLabel.CENTER);
//        label_Password.setBounds((screenWidth/2)-80,screenHeight/2, 160,20);

        JPasswordField pw = new JPasswordField(); // Password entry field.
        pw.setBounds((screenWidth/2) - 50, screenHeight/2 + 30, 100, 30);

        JLabel label_Username = new JLabel("Username:"); // Username label.
        label_Username.setFont(new Font("Courier", Font.BOLD, 20));
        label_Username.setHorizontalAlignment(JLabel.CENTER);
        label_Username.setVerticalAlignment(JLabel.CENTER);
        label_Username.setBounds((screenWidth/2)-80,(screenHeight/2) - 100, 160,20);

        JTextField un = new JTextField(); // Username entry field.
        un.setBounds((screenWidth/2) - 50, (screenHeight/2) - 60, 100, 30);

//        Tools.addButton_frame(LoginInterface.loginScreen, "b_Login", "Login", (screenWidth/2) - 50,
//                screenHeight/2 + 100, 100, 30);
        JButton b_Login = new JButton("Login"); // Login button.
        b_Login.setBounds((screenWidth/2) - 50, screenHeight/2 + 100, 100, 30);

//        JButton b_Exit = new JButton("Exit"); // Exit button.
//        b_Exit.setBounds(screenWidth - 105, screenHeight - 35, 100, 30);

        // Add elements to the screen.
        loginScreen.getContentPane().add(pw);
        loginScreen.getContentPane().add(label_Username);
        loginScreen.getContentPane().add(labelMessage);
        //loginScreen.getContentPane().add(label_Password);
        //loginScreen.getContentPane().add(b_Login);
        //loginScreen.getContentPane().add(b_Exit);
        loginScreen.getContentPane().add(un);

        loginScreen.setLayout(null);
        loginScreen.setUndecorated(true);
        loginScreen.setVisible(true);

//        b_Exit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {            // Close the screen on exit.
//                loginScreen.dispose();
//            }
//        });

        b_Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // Retrieve username and password from user input
                String username = un.getText();
                String password = new String(pw.getPassword());
                // Hash the entered password before it is sent over the network
                password = HashCredentials.Hash(password);
                // Set the username and password variables of the static instance of user in ControlPanel class
                user.setUsername(username);
                user.setPassword(password);
                // Clear username and password fields for added security
                un.setText("");
                pw.setText("");
                // Attempt connection to server
                if (AttemptConnect()) {
                    // Try a login attempt
                    try {
                        // Send user object to server
                        objectStreamer.Send(user);
                        // Await returned object from server
                        user = (User)objectStreamer.Receive();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        Log.Error("Login attempt request failed");
                    }
                    // Disconnect from server
                    AttemptDisconnect();
                    // Check whether the user has been verified
                    if (user.isVerified() && user.getId() != null) {
                        Log.Confirmation("User credentials verified by server");
                        Log.Message("Opening control panel interface");
                        // Open control panel screen
                        ControlPanelInterface.controlPanelScreen();
                        // Nicely close login screen
                        Log.Message("Closing login screen");
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
