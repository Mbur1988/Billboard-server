import java.io.IOException;
import java.util.concurrent.*;
import Tools.Log;

// Viewer class 
public class Viewer extends Client {

    public static void main(String[] args) {
        Log.Message("Viewer started");
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

            if (AttemptConnect()) {

                //
                // Receive current billboard here
                //

                // closing resources
                Disconnect();
            }

            else {
                Log.Warning("Connection failed... Retry in 15s");
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
                Log.Confirmation("Connected to server on: " + socket.toString());
                // send connection type
                dos.writeUTF("viewer");
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