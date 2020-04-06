import CustomExceptions.InvalidPortException;
import Tools.PropertyReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    // Declare InetAddress variable to be used by server
    protected static InetAddress ip;
    // Declare port variable to be used by server
    protected static int port;

    protected static Scanner scn;

    protected static Socket socket;

    protected static DataInputStream dis;

    protected static DataOutputStream dos;

    /**
     * Sets the port number to be used by the server
     * @param port The port number
     * @throws InvalidPortException
     */
    protected static void setPort(int port) throws InvalidPortException {
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
    protected static int getPort() {
        return port;
    }

    /**
     * Sets the port number to be used by the viewer
     * @param ip the ip address or hostname as a string
     * @throws UnknownHostException
     */
    protected static void setIp(String ip)  {
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
    protected static String getIp() {
        return ip.getHostAddress();
    }

    /**
     * Updates network variables with configurations defined in .props file
     */
    protected static void SetNetworkConfig() {
        try {
            // setting ip
            setIp(PropertyReader.GetProperty("client", "IpAddress"));
            // setting port
            String Port = PropertyReader.GetProperty("client", "Port");
            setPort(Integer.parseInt(Port));
        } catch (IOException | InvalidPortException e) {
            e.printStackTrace();
        }
    }

    protected static boolean AttemptConnect() {

        // create new scanner
        scn = new Scanner(System.in);

        // establish the connection with server port
        try {
            // create new socket
            socket = new Socket(ip, port);

            // obtaining input and out streams
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            return true;
        }
        catch (IOException e) {
        return false;
        }
    }

}
