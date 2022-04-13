package by.lukyanov.finaltask.util;

import by.lukyanov.finaltask.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCRYPTION_METHOD = "SHA-1";
    private static PasswordEncoder instance;

    private PasswordEncoder() {
    }

    public static PasswordEncoder getInstance(){
        if (instance == null){
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public String encode(String decodedPassword) throws ServiceException {
        byte[] encodedBytes = new byte[0];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_METHOD);
            messageDigest.update(decodedPassword.getBytes(StandardCharsets.UTF_8));
            encodedBytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        BigInteger bigInteger = new BigInteger(1, encodedBytes);
        String encodedPassword = bigInteger.toString(16);
        return encodedPassword;
    }
}
