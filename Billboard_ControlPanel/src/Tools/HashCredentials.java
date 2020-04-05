package Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashCredentials {

    /**
     * Adds salt and hashes the user password using MessageDigest
     * @param userPassword Password to hash as string
     * @param salt Salt to add as byte array
     * @return Hashed salt and password as string
     * @throws NoSuchAlgorithmException
     */
    public static String Hash(String userPassword, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        hash.reset();
        if (salt != null) {
            hash.update(salt);
        }
        byte[] hashedBytes = hash.digest(userPassword.getBytes());
        return HashCredentials.ConvertByte(hashedBytes);
    }

    /**
     * Hashes the user password using MessageDigest
     * @param userPassword Password to hash as string
     * @return Hashed salt and password as string
     * @throws NoSuchAlgorithmException
     */
    public static String Hash(String userPassword) throws NoSuchAlgorithmException {
        return HashCredentials.Hash(userPassword, null);
    }

    /**
     * Creates a random salt
     * @return Salt as byte array
     */
    public static byte[] CreateSalt() {
        byte[] bytes = new byte[10];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Converts a byte array to string
     * @param bytes Array to convert
     * @return Converted string
     */
    private static String ConvertByte(byte[] bytes) {
        char[] hashArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++){
            int j = bytes[i] & 0xFF;
            hexChars[i * 2] = hashArray[j >>> 4];
            hexChars[i * 2 + 1] = hashArray[j & 0x0F];
        }
        return new String(hexChars);
    }
}