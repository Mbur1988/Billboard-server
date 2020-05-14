package Server;

import java.io.*;
import java.net.*;
import Server.Handlers.MariaDB;
import Server.Trackers.Authorised;
import Tools.Log;
import Tools.PropertyReader;
import CustomExceptions.InvalidPortException;
import Server.Handlers.ConnectionHandler;

public class Server {
    // Declare port variable to be used by server
    private static int port;
    public static MariaDB mariaDB = new MariaDB();

    /**
     * Sets the port number to be used by the server
     * @param port The port number
     * @throws InvalidPortException
     */
    public static void setPort(int port) throws InvalidPortException {
        if (port <= 0) {
            throw new InvalidPortException("port number invalid");
        } else if (port > 0 && port < 1024 || port == 3306) {
            throw new InvalidPortException("port number reserved");
        } else {
            Server.port = port;
        }
    }

    /**
     * Returns the port number that is currently been used by the server
     * @return the port number as an integer
     */
    public static int getPort() {
        return port;
    }

    public static void main(String[] args) {
        Log.Message("Server.Server started");
        SetNetworkConfig();
        mariaDB.Connect();
        //mariaDB.Disconnect();
        try {
            // server is listening on configured port
            ServerSocket serverSocket = new ServerSocket(port);

            // run an infinite loop for getting client requests
            while (true) {
                Socket socket = null;
                try {
                    // socket object to receive incoming client requests
                    socket = serverSocket.accept();
                    // obtaining input and out streams
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    Log.Confirmation(socket.toString() + " connected to server");

                    ConnectionHandler connectionHandler = new ConnectionHandler(socket, dis, dos);
                    connectionHandler.start();

                } catch (Exception e) {
                    assert socket != null;
                    socket.close();
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the network configuration using the server.props file
     */
    private static void SetNetworkConfig() {
        try {
            // setting port
            String Port = PropertyReader.GetProperty("server", "Port");
            setPort(Integer.parseInt(Port));
        } catch (IOException | InvalidPortException e) {
            e.printStackTrace();
        }
    }
}