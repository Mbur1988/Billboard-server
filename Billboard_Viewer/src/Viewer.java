import SerializableObjects.User;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Viewer class 
public class Viewer {
    static int port = 5056;

    static InetAddress ip;

    public static void setPort(int port) {
        Viewer.port = port;
    }

    public static int getPort() {
        return port;
    }

    public static void setIp(InetAddress ip) {
        Viewer.ip = ip;
    }

    public static InetAddress getIp() {
        return ip;
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

            // setting localhost ip
            ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, port);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("viewer");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            User user = (User) ois.readObject();
            user.showDetails();

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            oos.flush();

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