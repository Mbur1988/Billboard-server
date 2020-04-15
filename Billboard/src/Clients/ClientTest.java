package Clients;

import CustomExceptions.InvalidPortException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Client client;

    @BeforeEach
    public void newClient() {
        client = new Client();
    }

    @Test
    void setPort() throws InvalidPortException {
        client.setPort(99999);
        assertEquals(client.getPort(), 99999);
    }

    @Test
    void getPort() {
        assertEquals(client.getPort(), 0);
    }

    @Test
    void setIp() {
        client.setIp("localhost");
        assertEquals(client.getIp(), "127.0.0.1");
    }

    @Test
    void getIp() {
        client.setIp("192.168.001.254");
        assertEquals(client.getIp(), "192.168.1.254");
    }
}