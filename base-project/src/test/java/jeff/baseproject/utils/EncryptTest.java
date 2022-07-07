package jeff.baseproject.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {

    @Test
    void encryptHash() {
        String password = "1q2w3q4q5q";
        String encryptPassword = Encrypt.encryptHash(password);

        String encryptPassword2 = Encrypt.encryptHash(password);

        Assertions.assertEquals(encryptPassword, encryptPassword2);



    }
}