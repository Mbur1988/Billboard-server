package SerializableObjects;

import org.junit.jupiter.api.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void newUser() {
        user = new User();
    }

    @Test
    void isVerified() {
        assertEquals(user.isVerified(), false);
    }

    @Test
    void setVerified() {
        user.setVerified(true);
        assertEquals(user.isVerified(), true);
    }

    @Test
    void getId() {
        assertEquals(user.getId(), null);
    }

    @Test
    void setId() {
        UUID uuid = UUID.randomUUID();
        user.setId(uuid);
        assertEquals(user.getId(), uuid);
    }

    @Test
    void getUsername() {
        assertEquals(user.getUsername(), "");
    }

    @Test
    void setUsername() {
        user.setUsername("username");
        assertEquals(user.getUsername(), "username");
    }

    @Test
    void getPassword() {
        assertEquals(user.getPassword(), "");
    }

    @Test
    void setPassword() {
        user.setPassword("password");
        assertEquals(user.getPassword(), "password");
    }

    @Test
    void getAccess() {
        assertEquals(user.getAccess(), 0);
    }

    @Test
    void setAccess() {
        user.setAccess(3);
        assertEquals(user.getAccess(), 3);
    }
}