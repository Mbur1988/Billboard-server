package Handlers;

import java.io.*;
import java.net.*;

public class ConnectionHandler extends Thread {

    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;

    public ConnectionHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        try {
            // Attempt to read client data input stream and handle any exceptions
            String received = dis.readUTF();
            switch (received) {

                case "viewer":
                    ViewerHandler viewerHandler = new ViewerHandler(socket, dis, dos);
                    viewerHandler.start();
                    break;

                case "controlpanel":
                    CPHandler cpHandler = new CPHandler(socket, dis, dos);
                    cpHandler.start();
                    break;

                default:
                    this.socket.close();
                    this.dis.close();
                    this.dos.close();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
