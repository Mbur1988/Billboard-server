package Handlers;

import java.io.*;
import java.net.Socket;
import java.text.*;
import java.util.*;

public class CPHandler extends ConnectionHandler {

    // Declare class variables
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    // Class Constructor
    public CPHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
    }

    //Override of the run function of parent class
    @Override
    public void run() {
        // Declare variables
        String received;
        String toreturn;
        while (true)
        {
            // Attempt to read data input stream of new connection and handle any exceptions
            try {
                // Ask user what he wants
                dos.writeUTF("What do you want?[Date | Time]..\n"+
                        "Type Exit to terminate connection.");
                // Attempt to read client data input stream and handle any exceptions
                try {
                    // receive the answer from client
                    received = dis.readUTF();
                }
                catch (IOException e) {
                    System.out.println("Client " + socket + " disconnected...");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }
                // If an exit commant is received then neatly close the connection
                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // creating Date object
                Date date = new Date();

                // write on output stream based on the
                // answer from the client
                switch (received) {
                    case "Date" :
                        toreturn = fordate.format(date);
                        dos.writeUTF(toreturn);
                        break;
                    case "Time" :
                        toreturn = fortime.format(date);
                        dos.writeUTF(toreturn);
                        break;
                    case "Ping" :
                        dos.writeUTF("Pong..");
                        break;
                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}