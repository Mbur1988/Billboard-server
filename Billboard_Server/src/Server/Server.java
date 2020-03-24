package Server;

import Server.ViewerHandler.ViewerHandler;
import Server.ViewerTracker.ViewerTracker;

import java.io.*;
import java.net.*;
import java.util.UUID;

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
        ViewerTracker viewerTracker = new ViewerTracker();

        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server.Server created on port " + port + "\nServer.Server running...");

        // running infinite loop for getting
        // client request
        while (true) {
            Socket socket = null;

            try {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // generate a random uuid for new thread
                String uuid = UUID.randomUUID().toString();

                // create a new thread object
                ViewerHandler thread = new ViewerHandler(socket, dis, dos, uuid);

                // add this client to active clients list
                viewerTracker.Add(uuid, thread);

                // Invoking the start() method
                thread.start();
            } catch (Exception e) {
                assert socket != null;
                socket.close();
                e.printStackTrace();
            }
        }
    }
}