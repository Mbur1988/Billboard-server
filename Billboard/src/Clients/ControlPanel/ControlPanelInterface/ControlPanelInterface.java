package Clients.ControlPanel.ControlPanelInterface;

import Clients.ControlPanel.ControlPanelTools.Tools;
import Clients.ControlPanel.ControlPanelTools.UserAccess;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static Clients.ControlPanel.ControlPanel.user;

public class ControlPanelInterface {

    private static final Font font = new Font("Courier", Font.PLAIN, 20);

    public static JFrame controlPanelScreen = new JFrame();
    public static JPanel createPanel = new JPanel();
    public static JPanel listPanel = new JPanel();
    public static JPanel schedulePanel = new JPanel();
    public static JPanel passwordPanel = new JPanel();
    public static JPanel editUserPanel = new JPanel();

    // Get the size of the screen.
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenWidth = screenSize.width;
    public static int screenHeight = screenSize.height;

    public static void controlPanelScreen() {

        //Create the Control Panel Screen window and set it the size of the screen.

        controlPanelScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        controlPanelScreen.setResizable(false);

        // Create the individual panels.

        // Create the tabbed pane.
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(0,0,screenWidth,screenHeight);

        // Elements for each pane:

        // Add the tabs to the tab pane.
        if (UserAccess.dec2bool(user.getAccess())[0]) {
            tabs.add("Create Billboard",createPanel);
        }
        if (UserAccess.dec2bool(user.getAccess())[1]) {
            tabs.add("Edit Billboards", listPanel);
        }
        if (UserAccess.dec2bool(user.getAccess())[2]) {
            tabs.add("Schedule Billboards", schedulePanel);
        }
        tabs.add("Change Password",passwordPanel);
        if (UserAccess.dec2bool(user.getAccess())[3]) {
            tabs.add("Edit Users", editUserPanel);
        }

        try {
            CreatePanel.createPanelScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListPanel.listPanelScreen();
        SchedulePanel.schedulePanelScreen();
        ChangePWPanel.changePWScreen();
        EditUsersPanel.editUserScreen();

        Tools.addExitButton(screenWidth - 105, screenHeight - 60, 100, 30);

        controlPanelScreen.getContentPane().add(tabs);
        controlPanelScreen.setLayout(null);
        controlPanelScreen.setUndecorated(true);
        controlPanelScreen.setVisible(true);
        controlPanelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // May need HIDE_ON_CLOSE instead of exit.

    }

    protected static void addTextfield (JPanel panel, JTextField textField, int x, int y, int width, int height) {
        textField.setBounds(x, y, width, height);
        textField.setFont(font);
        panel.add(textField);
    }

    protected static void addPasswordField (JPanel panel, JPasswordField passwordField, int x, int y, int width, int height) {
        passwordField.setBounds(x, y, width, height);
        passwordField.setFont(font);
        panel.add(passwordField);
    }

    protected static void addCheckBox (JPanel panel, JCheckBox checkbox, int x, int y, int width, int height) {
        checkbox.setBounds(x, y, width, height);
        checkbox.setFont(font);
        panel.add(checkbox);
    }

    protected static void addButton (JPanel panel, JButton button, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        panel.add(button);
    }

    protected static void addLabel (JPanel panel, JLabel label, int x, int y, int width, int height) {
        label.setBounds(x, y, width, height);
        label.setFont(font);
        panel.add(label);
    }

    protected static void addCombobox (JPanel panel, JComboBox combobox, int x, int y, int width, int height) {
        combobox.setBounds(x, y, width, height);
        combobox.setFont(font);
        panel.add(combobox);
    }

    protected static void addRadioButton (JPanel panel, JRadioButton radiobutton, int x, int y, int width, int height) {
        radiobutton.setBounds(x, y, width, height);
        radiobutton.setFont(font);
        panel.add(radiobutton);
    }

}
