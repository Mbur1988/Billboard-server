package Handlers;

import Tools.Log;
import java.io.*;
import java.net.Socket;

public class CPHandler extends ConnectionHandler {

    /**
     * Class constructor
     * @param socket the socket reference to use
     * @param dis the existing data input stream
     * @param dos the existing data output stream
     */
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        Log.Message(socket.toString() + " control panel handler started");

        // Create a new ObjectStreamHandler to send billboards to the viewer
        ObjectStreamHandler stream = new ObjectStreamHandler(socket);

        //
        // Do required control panel things here
        //

        // Close connection nicely
        try {
            socket.close();
            this.dis.close();
            this.dos.close();
            Log.Confirmation(socket.toString() + " closed successfully");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}