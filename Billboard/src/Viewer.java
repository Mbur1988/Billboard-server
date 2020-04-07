import Handlers.ObjectStreamHandler;
import SerializableObjects.User;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Viewer class 
public class Viewer extends Client {

    public static void main(String[] args) {

        SetNetworkConfig();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(Viewer::RequestUpdate, 0, 15, TimeUnit.SECONDS);

        // This is loop can be used as the main loop for the GUI and will be unaffected by the executor service
        while (true) {
            // Add main loop contents here
        }
    }

    /**
     * Called by the executor service every 15 seconds.
     * This method creates a new connection to the server and will request the current billboard to display
     */
    public static void RequestUpdate() {
        try {
            if (AttemptConnect()) {

                // create object stream handler
                ObjectStreamHandler stream = new ObjectStreamHandler(socket);

                // send connection type
                dos.writeUTF("viewer");

                // test object stream
                User test = null;
                Object received = stream.Receive();
                if (received instanceof User) {
                    test = (User) received;
                    test.showDetails();
                }
                stream.Send(test);

                // test data stream with handshake
                System.out.println(dis.readUTF());
                dos.writeUTF("handshake from viewer");

                scn.close();
                dis.close();
                dos.close();
            }

            else {
                System.out.println("Connection failed... Retry in 15s");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}