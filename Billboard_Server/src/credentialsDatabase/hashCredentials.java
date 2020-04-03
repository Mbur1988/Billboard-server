package credentialsDatabase;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;


import static java.security.MessageDigest.getInstance;

public class hashCredentials<Private> {
    public static void main(String[] args) throws Exception{

        String userPassword = "password";
        String hashcode = "SHA-256";
        byte[] salt = createSalt();
        System.out.println("256 hash salted of the password of 0000 is " + convertHash(userPassword, hashcode, salt));
    }



    private static String convertHash(String userPassword, String hashcode, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashcode);
        digest.reset();
        digest.update(salt);
        byte[] hash = digest.digest(userPassword.getBytes());
        return convertByte(hash);
    }

    private final static char[] hashArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String convertByte(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++){
            int j = bytes[i] & 0xFF;
            hexChars[i * 2] = hashArray[j >>> 4];
            hexChars[i * 2 + 1] = hashArray[j & 0x0F];


        }
        return new String(hexChars);

    }

    public static byte[] createSalt() {
        byte[] bytes = new byte[10];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;

    }



}
