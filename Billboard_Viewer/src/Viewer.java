import CustomExceptions.InvalidPortException;
import Handlers.ObjectStreamHandler;
import SerializableObjects.User;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Viewer class 
public class Viewer {

    // Declare InetAddress variable to be used by server
    private static InetAddress ip;
    // Declare port variable to be used by server
    private static int port;

    /**
     * Sets the port number to be used by the server
     * @param port The port number
     * @throws InvalidPortException
     */
    public static void setPort(int port) throws InvalidPortException {
        if (port <= 0) {
            throw new InvalidPortException("port number invalid");
        }
        else if (port > 0 && port < 1024 || port == 3306) {
            throw new InvalidPortException("port number reserved");
        }
        else {
            Viewer.port = port;
        }
    }

    /**
     * Returns the port number that is currently been used by the server
     * @return the port number as an integer
     */
    public static int getPort() {
        return port;
    }

    /**
     * Sets the port number to be used by the viewer
     * @param ip the ip address or hostname as a string
     * @throws UnknownHostException
     */
    public static void setIp(String ip)  {
        try {
            Viewer.ip = InetAddress.getByName(ip);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the ip address or hostname that is currently set as a string
     * @return ip address or hostname as string
     */
    public static String getIp() {
        return ip.getHostAddress();
    }

    public static void main(String[] args) throws InvalidPortException {

        // setting ip
        setIp("localhost");
        // setting port
        setPort(5056);

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
            Scanner scn = new Scanner(System.in);

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, port);

            ObjectStreamHandler stream = new ObjectStreamHandler(socket);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("viewer");

            User test = null;
            Object received = stream.Receive();
            if (received instanceof User)
            {
                test = (User) received;
                test.showDetails();
            }

            stream.Send(test);

            System.out.println(dis.readUTF());
            dos.writeUTF("DataOutputStream Test");

            scn.close();
            dis.close();
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}