import ControlPanelInterface.ControlPanelInterface;
import LoginInterface.LoginInterface;

// Client class 
public class ControlPanel extends Client {

    public static void main(String[] args) {

        SetNetworkConfig();

        ControlPanelInterface.controlPanelScreen();
        LoginInterface.loginScreen();

        try {
            if (AttemptConnect()) {

                // send connection type
                dos.writeUTF("controlpanel");

                // the following loop performs the exchange of
                // information between client and client handler
                while (true) {
                    System.out.println(dis.readUTF());
                    String toSend = scn.nextLine();
                    dos.writeUTF(toSend);

                    // If client sends exit,close this connection
                    // and then break from the while loop
                    if (toSend.equals("Exit")) {
                        System.out.println("Closing this connection : " + socket);
                        socket.close();
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
            }

            else {
                System.out.println("Connection failed... Retry in 15s");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}