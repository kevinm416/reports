package com.kevinm416.report.auth;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.util.ByteSource;

public class PasswordHasher {

    public static final int HASH_ITERATIONS = 1024;
    public static final String ALGORITHM_NAME = Sha256Hash.ALGORITHM_NAME;

    private static final int SALT_LENGTH_BYTES = 32; // 256 bits


    public static HashedPassword saltAndHashPassword(String plaintextPassword) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes(SALT_LENGTH_BYTES);

        SimpleHashRequest request = new SimpleHashRequest(
                ALGORITHM_NAME,
                ByteSource.Util.bytes(plaintextPassword),
                salt,
                HASH_ITERATIONS);

        DefaultHashService service = new DefaultHashService();
        Hash hash = service.computeHash(request);

        return new HashedPassword(hash.toBase64(), hash.getSalt().toBase64());
    }

}
