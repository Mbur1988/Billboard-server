import Handlers.ConnectionHandler;

import java.io.*;
import java.net.*;

public class Server
{
    static int port = 5056;

    public static void setPort(int port) {
        Server.port = port;
    }

    public static int getPort() {
        return port;
    }

    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(port);

        // running infinite loop for getting
        // client request
        while (true) {
            Socket socket = null;
            try {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                ConnectionHandler connectionHandler = new ConnectionHandler(socket, dis, dos);
                connectionHandler.start();

            } catch (Exception e) {
                assert socket != null;
                socket.close();
                e.printStackTrace();
            }
        }
    }
}