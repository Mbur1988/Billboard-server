package LoginInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ControlPanelInterface.ControlPanelInterface;


// The Billboard Control Panel Login Interface.

public class LoginInterface {

    public static void loginScreen() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

         // May be able to use a dialog window instead of a frame. *****

        JFrame loginScreen = new JFrame(); // Login window.
        loginScreen.setSize(screenWidth,screenHeight);

        JLabel labelTest = new JLabel(); // Testing label that displays input at bottom of window.
        labelTest.setBounds((screenWidth/2) - 100, screenHeight/2 + 60, 200, 30);
        labelTest.setHorizontalAlignment(JLabel.CENTER);

        JLabel label_Password = new JLabel("Password:");    // Password label.
        label_Password.setFont(new Font("Courier", Font.BOLD, 20));
        label_Password.setHorizontalAlignment(JLabel.CENTER);
        label_Password.setVerticalAlignment(JLabel.CENTER);
        label_Password.setBounds((screenWidth/2)-80,screenHeight/2, 160,20);

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
        loginScreen.add(pw);
        loginScreen.add(label_Username);
        loginScreen.add(labelTest);
        loginScreen.add(label_Password);
        loginScreen.add(b_Login);
        loginScreen.add(un);

        loginScreen.setLayout(null);
        loginScreen.setUndecorated(true);
        loginScreen.setVisible(true);

        String correctPassword = "0000"; // Mock password for testing comparison of input.

        String userName = un.getText();

        b_Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                String enteredPassword = new String(pw.getPassword());

                if (correctPassword.equals(enteredPassword)) {
                    String credentials = "Username " + userName;
                   // credentials += ", Password: " + enteredPassword;
                    //ControlPanelInterface.controlPanelScreen();               Can run this from main or here...
                    loginScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    loginScreen.dispose();
                }
                else {
                    labelTest.setText("Incorrect password"); // This label is mainly for testing purposes.
                }
            }

        });

    }

}
