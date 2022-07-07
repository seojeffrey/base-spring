package jeff.baseproject.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {

    static String salt = "QkdkfkwWhgnc";
    public static String encryptHash(String password)
    {
        try {
            String hex = "";
            for (int i=0; i <= password.length()*25 % 13; i++) {
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                // MD2, MD4, MD5, SHA-1, SHA-256, SHA-512
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(salt.getBytes());
                md.update(password.getBytes());
                hex = String.format("%064x", new BigInteger(1, md.digest()));
                password = hex;

            }
            return hex;
        } catch (NoSuchAlgorithmException e)
        {
            return "";
        }



    }
}
