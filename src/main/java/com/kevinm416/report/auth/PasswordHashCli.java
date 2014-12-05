package com.kevinm416.report.auth;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.crypto.hash.format.Base64Format;
import org.apache.shiro.util.ByteSource;

public class PasswordHashCli {

    public static void main(String[] args) {
        SimpleHashRequest request = new SimpleHashRequest(
                Sha256Hash.ALGORITHM_NAME,
                ByteSource.Util.bytes(args[0]),
                ByteSource.Util.bytes(""),
                1024);

        DefaultHashService service = new DefaultHashService();
        Hash hash = service.computeHash(request);
        System.out.println(new Base64Format().format(hash));
    }

}
