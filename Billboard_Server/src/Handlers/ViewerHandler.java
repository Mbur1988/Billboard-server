package Handlers;

import SerializableObjects.User;

import java.io.*;
import java.net.Socket;

public class ViewerHandler extends ConnectionHandler {

    public ViewerHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
    }

    @Override
    public void run() {
        ObjectStreamHandler stream = new ObjectStreamHandler(socket);
        while (true)
        {
            try {
                User user = new User("test", "test");
                stream.Send(user);

                Object received = stream.Receive();
                if (received instanceof User)
                {
                    User test = (User) received;
                    test.showDetails();
                }

                dos.writeUTF("DataInputStream Test");
                System.out.println(dis.readUTF());

                socket.close();
                dis.close();
                dos.close();
                break;
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}