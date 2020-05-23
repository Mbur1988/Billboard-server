package Clients.ControlPanel.ControlPanelTools;

import Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface;
import Clients.ControlPanel.LoginInterface.LoginInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Tools.Log;
import static Clients.Client.objectStreamer;
import static Clients.ControlPanel.ControlPanel.*;

/**
 List of tools:
 addExitButton adds the exit button to each panel.
 addLabel single line command to add a label.
 */
public class Tools {

    private static final Font font = new Font("Courier", Font.PLAIN, 20);

    /**
     * addExitButton will add the exit button to each panel and utilise the one action
     * listener to perform the close action.
     *
     * Called once in ControlPanelInterface.java.
     *
     * The parameters will ensure the button is always in the same location and size on every panel.
     * @param x location of the button.
     * @param y location of the button.
     * @param width of the button.
     * @param height of the button.
     */
    public static void addExitButton(int x, int y, int width, int height){

        class ExitButton implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.isVerified()) {
                    user.setAction("userExit");
                    // Attempt connection to server
                    if (AttemptConnect()) {
                        // Try a login attempt
                        try {
                            // Send user object to server
                            objectStreamer.Send(user);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            Log.Error("User attempt request failed");
                        }
                        // Disconnect from server
                        AttemptDisconnect();
                    }
                    // Post message to user if unable to connect to server
                    else {
                        Log.Error("Unable to connect to server");
                    }
                    ControlPanelInterface.controlPanelScreen.dispose();
                }
                else {
                    LoginInterface.loginScreen.dispose();
                }
            }
        }

        // An individual button needs to be created for each panel.
        // Java does not allow a button to have more than 1 parent class, meaning only the last
        // .add() called would be implemented. The previous would be overwritten.

        JButton b_ExitCreate = new JButton("Exit");
        b_ExitCreate.setBounds(x, y, width, height);
        b_ExitCreate.addActionListener(new ExitButton());

        JButton b_ExitList = new JButton("Exit");
        b_ExitList.setBounds(x, y, width, height);
        b_ExitList.addActionListener(new ExitButton());

        JButton b_ExitSched = new JButton("Exit");
        b_ExitSched.setBounds(x, y, width, height);
        b_ExitSched.addActionListener(new ExitButton());

        JButton b_ExitPW = new JButton("Exit");
        b_ExitPW.setBounds(x, y, width, height);
        b_ExitPW.addActionListener(new ExitButton());

        JButton b_ExitEU = new JButton("Exit");
        b_ExitEU.setBounds(x, y, width, height);
        b_ExitEU.addActionListener(new ExitButton());

        JButton b_ExitLogin = new JButton("Exit");

        // Different y-value due to different size content pane in login screen to control panel screens
        // caused by the tabbed pane on the control panel.
        b_ExitLogin.setBounds(x, y, width, height);
        b_ExitLogin.addActionListener(new ExitButton());

        ControlPanelInterface.createPanel.add(b_ExitCreate);
        ControlPanelInterface.listPanel.add(b_ExitList);
        ControlPanelInterface.editPanel.add(b_ExitList);
        ControlPanelInterface.schedulePanel.add(b_ExitSched);
        ControlPanelInterface.passwordPanel.add(b_ExitPW);
        ControlPanelInterface.editUserPanel.add(b_ExitEU);
        LoginInterface.loginScreen.add(b_ExitLogin);

    }

    /**
     * addLabel places a label of chosen size, location and text onto the desired screen.
     *
     * @param frame Name of frame to add the label to in the format: class.frameName
     * @param name Name of the label.
     * @param text Text to appear on screen.
     * @param x location of the label.
     * @param y location of the label.
     * @param width of the label.
     * @param height of the label.
     * @param font of the text.
     * @param weight Enter 0 for plain, 1 for bold, and 2 for italic.
     * @param size of text.
     * @param x_align Enter 0 for center alignment, 2 for left, and 4 for right.
     * @param y_align Enter 0 for center alignment, 1 for top, and 3 for bottom.
     */
    public static void addLabel_frame(JFrame frame, String name, String text, int x, int y, int width, int height,
                                String font, int weight, int size, int x_align, int y_align){
       JLabel label = new JLabel();
       label.setName(name);
       label.setText(text);
       label.setFont(new Font(font, weight, size));
       label.setHorizontalAlignment(x_align);
       label.setVerticalAlignment(y_align);
       label.setBounds(x, y, width, height);
       frame.add(label);
    }

/**
 * addLabel places a label of chosen size, location and text onto the desired screen.
 *
 * @param panel Name of panel to add the label to in the format: class.panelName
 * @param name Name of the label.
 * @param text Text to appear on screen.
 * @param x location of the label.
 * @param y location of the label.
 * @param width of the label.
 * @param height of the label.
 * @param font of the text.
 * @param weight Enter 0 for plain, 1 for bold, and 2 for italic.
 * @param size of text.
 * @param x_align Enter 0 for center alignment, 2 for left, and 4 for right.
 * @param y_align Enter 0 for center alignment, 1 for top, and 3 for bottom.
 * */

    public static void addLabel_panel(JPanel panel, String name, String text, int x, int y, int width, int height,
                                      String font, int weight, int size, int x_align, int y_align){
        JLabel label = new JLabel();
        label.setName(name);
        label.setText(text);
        label.setFont(new Font(font, weight, size));
        label.setHorizontalAlignment(x_align);
        label.setVerticalAlignment(y_align);
        label.setBounds(x, y, width, height);
        panel.add(label);
    }

    /**
     *  @param frame  Name of frame to add the button to in the format: class.frameName
     * @param name Button name.
     * @param text Shown on button
     * @param x location of the button.
     * @param y location of the button.
     * @param width of the button.
     * @param height of the button.
     *
     */
    public static void addButton_frame(JFrame frame, String name, String text, int x, int y, int width, int height){
        JButton butt = new JButton(text);
        butt.setName(name);
        butt.setBounds(x,y,width,height);
        frame.add(butt);
    }

    /**
     * Adds a text field to the desired panel
     * @param panel
     * @param textField
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addTextfield(JPanel panel, JTextField textField, int x, int y, int width, int height) {
        textField.setBounds(x, y, width, height);
        textField.setFont(font);
        panel.add(textField);
    }

    /**
     * Adds a password field to the desired panel
     * @param panel
     * @param passwordField
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addPasswordField(JPanel panel, JPasswordField passwordField, int x, int y, int width, int height) {
        passwordField.setBounds(x, y, width, height);
        passwordField.setFont(font);
        panel.add(passwordField);
    }

    /**
     * Adds a checkbox to the desired panel
     * @param panel
     * @param checkbox
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addCheckBox(JPanel panel, JCheckBox checkbox, int x, int y, int width, int height) {
        checkbox.setBounds(x, y, width, height);
        checkbox.setFont(font);
        panel.add(checkbox);
    }

    /**
     * Adds a button to the desired panel
     * @param panel
     * @param button
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addButton(JPanel panel, JButton button, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        panel.add(button);
    }

    /**
     * Adds a label to the desired panel
     * @param panel
     * @param label
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addLabel(JPanel panel, JLabel label, int x, int y, int width, int height) {
        label.setBounds(x, y, width, height);
        label.setFont(font);
        panel.add(label);
    }

    /**
     * Adds a combobox to the desired panel
     * @param panel
     * @param combobox
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addCombobox(JPanel panel, JComboBox combobox, int x, int y, int width, int height) {
        combobox.setBounds(x, y, width, height);
        combobox.setFont(font);
        panel.add(combobox);
    }

    /**
     * Adds a radio button to the desired panel
     * @param panel
     * @param radiobutton
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void addRadioButton(JPanel panel, JRadioButton radiobutton, int x, int y, int width, int height) {
        radiobutton.setBounds(x, y, width, height);
        radiobutton.setFont(font);
        panel.add(radiobutton);
    }
}
