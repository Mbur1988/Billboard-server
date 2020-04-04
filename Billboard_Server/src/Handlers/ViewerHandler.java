package Handlers;

import SerializableObjects.TestObject;

import java.io.*;
import java.net.Socket;

public class ViewerHandler extends ConnectionHandler {

    public ViewerHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        super(socket, dis, dos);
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                // Stream object for sending object
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                TestObject object1 = new TestObject(12,"username","password");
                oos.writeObject(object1);
                oos.flush();

                dos.writeUTF("Does this work???");

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                TestObject testObject = (TestObject) ois.readObject();
                testObject.showDetails();

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