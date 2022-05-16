package by.lukyanov.finaltask.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordEncoder {
    private static final int DEFAULT_SALT_LENGTH = 16;
    private static final int DEFAULT_HASH_LENGTH = 32;
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 64 * 1024;
    private static final int PARALLELISM = 1;
    private static Argon2 argon2;
    private static PasswordEncoder instance;

    private PasswordEncoder() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH);
    }

    public static PasswordEncoder getInstance(){
        if (instance == null){
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public String encode(String plainPassword) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, plainPassword.toCharArray());
    }

    public boolean verify(String hash, String plainPassword){
        return argon2.verify(hash, plainPassword.toCharArray());
    }
}
