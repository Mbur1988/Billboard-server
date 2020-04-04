import Handlers.ObjectStreamHandler;
import SerializableObjects.User;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Viewer class 
public class Viewer {

    private static InetAddress ip;
    private static int port;

    public static void setPort(int port) {
        Viewer.port = port;
    }

    public static int getPort() {
        return port;
    }

    public static void setIp(String ip) throws UnknownHostException {
        Viewer.ip = InetAddress.getByName(ip);
    }

    public static String getIp() {
        return ip.getHostAddress();
    }

    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(Viewer::RequestUpdate, 0, 15, TimeUnit.SECONDS);
//        while (true) {
//
//        }
    }

    public static void RequestUpdate() {
        try {
            Scanner scn = new Scanner(System.in);

            // setting ip
            setIp("localhost");
            // setting port
            setPort(5056);

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, port);

            ObjectStreamHandler stream = new ObjectStreamHandler(socket);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("viewer");

            User test = null;
            Object received = stream.Receive();
            if (received instanceof User)
            {
                test = (User) received;
                test.showDetails();
            }

            stream.Send(test);

            System.out.println(dis.readUTF());
            dos.writeUTF("DataOutputStream Test");

            scn.close();
            dis.close();
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}