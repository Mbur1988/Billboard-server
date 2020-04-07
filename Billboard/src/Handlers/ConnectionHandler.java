package Handlers;

import Tools.Log;

import java.io.*;
import java.net.*;

public class ConnectionHandler extends Thread {

    // Declare class variables
    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;

    /**
     * Class constructor
     * @param socket the socket reference to use
     * @param dis the existing data input stream
     * @param dos the existing data output stream
     */
    public ConnectionHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        try {
            // Attempt to read data input stream of new connection and handle any exceptions
            String received = dis.readUTF();
            // Switch method used to identify the new connection type
            switch (received) {
                // If the new connection is a viewer then create and start new viewer handler
                case "viewer":
                    Log.Message(socket.toString() + " identified as viewer");
                    ViewerHandler viewerHandler = new ViewerHandler(socket, dis, dos);
                    viewerHandler.start();
                    break;
                // If the new connection is a control panel then create and start new control panel handler
                case "controlpanel":
                    Log.Message(socket.toString() + " identified as control panel");
                    CPHandler cpHandler = new CPHandler(socket, dis, dos);
                    cpHandler.start();
                    break;
                // If the new connection type is unrecognised then neatly close the connection
                default:
                    Log.Message("Connection could not be identified");
                    this.socket.close();
                    this.dis.close();
                    this.dos.close();
                    Log.Confirmation(socket.toString() + " closed successfully");
                    break;
            }
        // Catch and exceptions
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
