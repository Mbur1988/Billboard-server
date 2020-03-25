import java.io.*;
import java.net.*;
import java.util.Scanner;

// Viewer class 
public class Viewer
{
    static int port;

    public static int getPort() { return port; }

    public static void setPort(int port) { Viewer.port = port; }

    public static void main(String[] args) {
        try
        {
            Scanner scn = new Scanner(System.in);

            // setting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");
            
            // setting port
            port = 5056;
            
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, port);

            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of 
            // information between Viewer and Viewer handler 
            while (true)
            {
                System.out.println(dis.readUTF());
                String tosend = scn.nextLine();
                dos.writeUTF(tosend);

                // If Viewer sends exit,close this connection  
                // and then break from the while loop 
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by Viewer 
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