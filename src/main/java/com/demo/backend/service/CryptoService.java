package com.demo.backend.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CryptoService {
    @Value("${crypto.KEY}")
    private String KEY;
    private static final String ALGO = "AES";

    public  String encrypt(String plainUrl) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plainUrl.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedUrl) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedUrl));
        return new String(decrypted);
    }
}