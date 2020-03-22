package Basic_GUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The Billboard Control Panel.
 */

public class Basic_GUI {

    private JPanel panel1;

    /**
     * The login screen.
     */
    public static void Screen() {

        /**
         * May be able to use a dialog window instead of a frame.
         */

        JFrame loginScreen = new JFrame("Billboard Control Panel"); // Login window.

        final JLabel labelTest = new JLabel(); // Testing label that displays input at bottom of window.
        labelTest.setBounds(20,150, 200,50);

        JLabel label_Password = new JLabel("Password:");    // Password label.
        label_Password.setBounds(20,50, 80,20);

        final JPasswordField pw = new JPasswordField(); // Password entry field.
        pw.setBounds(150, 50, 150, 20);

        JLabel label_Username = new JLabel("Username:"); // Username label.
        label_Username.setBounds(20,20, 80,20);

        final JTextField un = new JTextField(); // Username entry field.
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

        loginScreen.setSize(400,400);

        loginScreen.setLayout(null);
        loginScreen.setVisible(true);

        String correctPassword = "0000"; // Mock password for testing comparison of input.

        b_Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String enteredPassword = new String(pw.getPassword());

                if (correctPassword.equals(enteredPassword)){
                    String data = "Username " + un.getText();
                data += ", Password: "
                        + enteredPassword;
                labelTest.setText(data);
                }
                else {
                    labelTest.setText("Incorrect password"); // This label is mainly for testing purposes.
                }
            }
        });

    }

    }
