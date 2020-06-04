package Clients.Viewer;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.*;
import Clients.Client;
import SerializableObjects.Billboard;
import Tools.Log;
import Tools.ProjectPath;

// Clients.Viewer.Viewer class
public class Viewer extends Client {

    private static Billboard billboard;

    public static void main(String[] args) {
        Log.Message("Clients.Viewer.Viewer started");
        SetNetworkConfig();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(Viewer::RequestUpdate, 0, 15, TimeUnit.SECONDS);

        // This is loop can be used as the main loop for the GUI and will be unaffected by the executor service
        while (true) {
            //
            // Add any main loop contents here
            //
        }
    }

    /**
     * Called by the executor service every 15 seconds.
     * This method creates a new connection to the server and will request the current billboard to display
     */
    public static void RequestUpdate() {
        try {
            if (AttemptConnect()) {
                // receive current billboard
                billboard = (Billboard) objectStreamer.Receive();
                // closing resources
                Disconnect();
            } else {
                billboard = new Billboard("Unable to Connect to Server",
                        "Error Billboard",
                        "Please Check Connection",
                        null,
                        billboard.ConvertImageToData(
                                ProjectPath.RootString() + "\\Resources\\Images\\Oops.jpg"),
                        Color.red,
                        Color.white,
                        Color.red,
                        null,
                        null);
            }
            // Display billboard
            billboard.showBillboard();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * Attempts to connect to the server
     * logs confirmation to console if connection successful
     * logs error to console if connection failed
     *
     * @return True if connection successful otherwise false
     */
    private static boolean AttemptConnect() {
        try {
            if (Connect()) {
                Log.Confirmation("Connected to server on: " + socket);
                // send connection type
                dos.writeUTF("viewer");
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}