package Server.Handlers;

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
                dos.writeUTF("Does this work???");
                socket.close();
                dis.close();
                dos.close();
                break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}