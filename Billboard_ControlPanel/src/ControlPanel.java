import java.io.*;
import java.net.*;
import java.util.Scanner;

import ControlPanelInterface.ControlPanelInterface;
import LoginInterface.*;
import  CustomExceptions.InvalidPortException;

// Client class 
public class ControlPanel {

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
            ControlPanel.port = port;
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
            ControlPanel.ip = InetAddress.getByName(ip);
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

    public static void main(String[] args) {
        ControlPanelInterface.controlPanelScreen();
        LoginInterface.loginScreen();
        try
        {
            Scanner scn = new Scanner(System.in);

            // setting ip
            setIp("localhost");
            // setting port
            setPort(5056);

            // establish the connection with server port 5056 
            Socket s = new Socket(ip, port);

            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            dos.writeUTF("controlpanel");

            // the following loop performs the exchange of 
            // information between client and client handler 
            while (true)
            {
                System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);

                // If client sends exit,close this connection  
                // and then break from the while loop 
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client 
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources 
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}