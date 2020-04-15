package Clients.ControlPanel;

import Clients.Client;
import Clients.ControlPanel.LoginInterface.LoginInterface;
import SerializableObjects.User;
import Tools.Log;
import java.io.IOException;

public class ControlPanel extends Client {

    public static User user = new User();

    public static void main(String[] args) {
        Log.Message("Control panel started");
        SetNetworkConfig();
        LoginInterface.loginScreen();
    }

    /**
     * Attempts to connect to the server
     * logs confirmation to console if connection successful
     * logs error to console if connection failed
     * @return True if connection successful otherwise false
     */
    public static boolean AttemptConnect() {
        try {
            if (Connect()) {
                Log.Confirmation("Connected to server on: " + socket);
                // send connection type
                dos.writeUTF("controlpanel");
                return true;
            }
            else {
                return false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AttemptDisconnect() {
        try {
            if (Disconnect()) {
                Log.Confirmation(socket + " closed successfully");
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}