import SerializableObjects.TestObject;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import static java.lang.Thread.sleep;

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

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            TestObject testObject = (TestObject) ois.readObject();
            testObject.showDetails();
            //ois.close();

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("viewer");

            System.out.println(dis.readUTF());

            scn.close();
            dis.close();
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}