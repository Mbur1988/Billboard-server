package Clients.ControlPanel;

import Clients.Client;
import Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface;
import Clients.ControlPanel.LoginInterface.LoginInterface;
import SerializableObjects.Lists;
import SerializableObjects.User;
import Tools.Log;

import java.io.IOException;

public class ControlPanel extends Client {

    public static User user;
    public static Lists lists;

    public static void main(String[] args) {
        Log.Message("Control panel started");
        SetNetworkConfig();

        //** Uncomment the below line for testing of control panel. **//
        //** Remember to comment it again before any pull request is made **//
        // ControlPanelInterface.controlPanelScreen();

        LoginInterface.loginScreen();
    }

    /**
     * Attempts to connect to the server
     * logs confirmation to console if connection successful
     * @return True if connection successful otherwise false
     */
    public static boolean AttemptConnect() {
        try {
            if (Connect()) {
                Log.Confirmation("Connected to server on: " + socket);
                // Send connection type so that the server can pass the connection to the correct handler
                dos.writeUTF("controlpanel");
                // Return true if the connection was successful
                return true;
            } else {
                // Return false if the connection was unsuccessful
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Attempts to disconnect from the server
     * logs confirmation to console if disconnection successful
     * @return True if disconnection successful otherwise false
     */
    public static boolean AttemptDisconnect() {
        if (Disconnect()) {
            Log.Confirmation(socket + " closed successfully");
            return true;
        } else {
            return false;
        }

    }
}