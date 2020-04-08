package LoginInterface;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// The Billboard Control Panel Login Interface.

public class LoginInterface {

    public static void loginScreen() {


         // May be able to use a dialog window instead of a frame. *****


        JFrame loginScreen = new JFrame("Billboard Control Panel"); // Login window.
        loginScreen.setSize(400,400);

        JLabel labelTest = new JLabel(); // Testing label that displays input at bottom of window.
        labelTest.setBounds(20,150, 200,50);

        JLabel label_Password = new JLabel("Password:");    // Password label.
        label_Password.setBounds(20,50, 80,20);

        JPasswordField pw = new JPasswordField(); // Password entry field.
        pw.setBounds(150, 50, 150, 20);

        JLabel label_Username = new JLabel("Username:"); // Username label.
        label_Username.setBounds(20,20, 80,20);

        JTextField un = new JTextField(); // Username entry field.
        un.setBounds(150, 20, 150, 20);

        JButton b_Login = new JButton("Login"); // Login button.
        b_Login.setBounds(150, 100, 100, 30);

        // Add elements to the screen.
        loginScreen.add(pw);
        loginScreen.add(label_Username);
        loginScreen.add(labelTest);
        loginScreen.add(label_Password);
        loginScreen.add(b_Login);
        loginScreen.add(un);


        loginScreen.setLocationRelativeTo(null);
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
                    credentials += ", Password: " + enteredPassword;
                    labelTest.setText(credentials);
                    loginScreen.dispose();
                }
                else {
                    labelTest.setText("Incorrect password"); // This label is mainly for testing purposes.
                }
            }

        });

    }

}
