package Tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static Tools.HashCredentials.*;
import static org.junit.jupiter.api.Assertions.*;

class HashCredentialsTest {

    String userPassword = "password";
    HashCredentials hashCredentials = new HashCredentials();

    @Test
    void testHash1() throws NoSuchAlgorithmException {
        assertNotEquals(Hash(userPassword, hashCredentials.CreateSalt()), "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8");
    }

    @Test
    void testHash2() throws NoSuchAlgorithmException {
        assertNotEquals(Hash(userPassword, hashCredentials.CreateSalt()), Hash(userPassword, hashCredentials.CreateSalt()));
    }

    @Test
    void testHash3() throws NoSuchAlgorithmException {
        byte[] salt = hashCredentials.CreateSalt();
        assertEquals(Hash(userPassword, salt), Hash(userPassword, salt));
    }

    @Test
    void testHash4() throws NoSuchAlgorithmException {
        assertEquals(Hash(userPassword), "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8");
    }

    @Test
    void testHash5() throws NoSuchAlgorithmException {
        assertEquals(Hash(userPassword), Hash(userPassword));
    }

    @Test
    void createSalt1() {
        byte[] salt = hashCredentials.CreateSalt();
        assertEquals(salt instanceof byte[], true);
    }

    @Test
    void createSalt2() {
        assertNotEquals(hashCredentials.CreateSalt(), hashCredentials.CreateSalt());
    }
}