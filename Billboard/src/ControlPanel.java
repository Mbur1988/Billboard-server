import Tools.Log;
import ControlPanelInterface.ControlPanelInterface;
import ControlPanelInterface.CreatePanel;

import LoginInterface.*;
import  CustomExceptions.InvalidPortException;
import Tools.PropertyReader;

import java.io.IOException;

import static java.lang.Thread.sleep;

// Client class 
public class ControlPanel extends Client {

    public static void main(String[] args) {
        Log.Message("Control panel started");
        SetNetworkConfig();

        ControlPanelInterface.controlPanelScreen();
        LoginInterface.loginScreen();

        // Example connection to server.
        // If server is available the control panel will connect and immediately disconnect
        // If server unavailable
        while(true) {
            if (AttemptConnect()) {
                Disconnect();
                break;
            } else {
                Log.Warning("Connection failed... Retry in 1s");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Attempts to connect to the server
     * logs confirmation to console if connection successful
     * logs error to console if connection failed
     * @return True if connection successful otherwise false
     */
    private static boolean AttemptConnect() {
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
}