package Clients.ControlPanel.LoginInterface;

import Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface;
import SerializableObjects.*;
import Tools.HashCredentials;
import Tools.Log;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static Tools.UserAccess.dec2bool;
import static Clients.ControlPanel.ControlPanel.*;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenHeight;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;

import Clients.ControlPanel.ControlPanelTools.Tools;

// The Billboard Control Panel Login Interface

public class LoginInterface {

    public static JFrame loginScreen = new JFrame(); // Login window.

    public static void loginScreen() {

        user = new User();

        Tools.addExitButton(screenWidth - 105, screenHeight - 60, 100, 30);

        loginScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginScreen.setResizable(false);

        JLabel labelMessage = new JLabel(); // Testing label that displays input at bottom of window.
        labelMessage.setBounds((screenWidth/2) - 100, screenHeight/2 + 60, 200, 30);
        labelMessage.setHorizontalAlignment(JLabel.CENTER);

        Tools.addLabel_frame(LoginInterface.loginScreen, "lbl_pw", "Password:", (screenWidth/2)-80,
                screenHeight/2, 160, 20, "Courier", 1, 20, 0, 0);

        JPasswordField pw = new JPasswordField(); // Password entry field.
        pw.setBounds((screenWidth/2) - 50, screenHeight/2 + 30, 100, 30);

        JLabel label_Username = new JLabel("Username:"); // Username label.
        label_Username.setFont(new Font("Courier", Font.BOLD, 20));
        label_Username.setHorizontalAlignment(JLabel.CENTER);
        label_Username.setVerticalAlignment(JLabel.CENTER);
        label_Username.setBounds((screenWidth/2)-80,(screenHeight/2) - 100, 160,20);

        JTextField un = new JTextField(); // Username entry field.
        un.setBounds((screenWidth/2) - 50, (screenHeight/2) - 60, 100, 30);

        JButton b_Login = new JButton("Login"); // Login button.
        b_Login.setBounds((screenWidth/2) - 50, screenHeight/2 + 100, 100, 30);

        // Add elements to the screen.
        loginScreen.getContentPane().add(pw);
        loginScreen.getContentPane().add(label_Username);
        loginScreen.getContentPane().add(labelMessage);
        //loginScreen.getContentPane().add(label_Password);
        loginScreen.getContentPane().add(b_Login);
        //loginScreen.getContentPane().add(b_Exit);
        loginScreen.getContentPane().add(un);

        loginScreen.setLayout(null);
        loginScreen.setUndecorated(true);
        loginScreen.setVisible(true);

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
                user.setAction("LoginAttempt");
                // Clear username and password fields for added security
                un.setText("");
                pw.setText("");
                // Attempt connection to server
                if (AttemptConnect()) {
                    // Try a login attempt
                    try {
                        // Send user object to server
                        objectStreamer.Send(user);
                        Log.Message("User object sent to control panel");
                        // Await returned object from server
                        user = (User) objectStreamer.Receive();
                        Log.Message("User object received form server");
                        // Check whether the user has been verified
                        if (user.isVerified() && user.getId() != null) {
                            Log.Confirmation("User credentials verified by server");
                            Log.Message("Opening control panel interface");
                            getLists();
                            Log.Message("Lists object received form server");
                            // Open control panel screen
                            Log.Message("Opening control panel screen");
                            ControlPanelInterface.controlPanelScreen();
                            // Nicely close login screen
                            Log.Message("Closing login screen");
                            loginScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                            loginScreen.dispose();
                        }
                        // Post message to user if username of password was incorrect
                        else {
                            Log.Error("User credentials could not be verified by server");
                            labelMessage.setText("Incorrect username or password");
                        }
                    }
                    catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        Log.Error("Login attempt request failed");
                    }
                    // Disconnect from server
                    AttemptDisconnect();
                }
                // Post message to user if unable to connect to server
                else {
                    Log.Error("Unable to connect to server");
                    labelMessage.setText("Unable to connect to server");
                }
            }
        });

        loginScreen.getRootPane().setDefaultButton(b_Login);
    }

    private static void getLists() {
        boolean[] access = dec2bool(user.getAccess());
        try {
            if (access[0]) {
                getUserBillboardsList();
            }
            getBillboardsList();
            if (access[2]) {
                getSchedulesList();
            }
            if (access[3]) {
                getUsersList();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getUserBillboardsList() throws IOException, ClassNotFoundException {
        dos.writeUTF("List Users Billboards");
        if (dis.readBoolean()) {
            ArrayList<String> list = (ArrayList<String>) objectStreamer.Receive();
            listUserBillboards = new ListUserBillboards(list);
        }
    }

    private static void getBillboardsList() throws IOException, ClassNotFoundException {
        dos.writeUTF("List Billboards");
        ArrayList<String> list = (ArrayList<String>) objectStreamer.Receive();
        listBillboards = new ListBillboards(list);
    }

    public static void getSchedulesList() throws IOException, ClassNotFoundException {
        dos.writeUTF("List Schedules");
        if (dis.readBoolean()) {
            ArrayList<String> list = (ArrayList<String>) objectStreamer.Receive();
            listSchedules = new ListSchedules(list);
        }
    }

    private static void getUsersList() throws IOException, ClassNotFoundException {
        dos.writeUTF("List Users");
        if (dis.readBoolean()) {
            ArrayList<String> list = (ArrayList<String>) objectStreamer.Receive();
            listUsers = new ListUsers(list);
        }
    }

}
