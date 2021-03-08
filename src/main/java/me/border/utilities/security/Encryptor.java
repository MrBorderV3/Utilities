package me.border.utilities.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {

    private static final String KEY_TYPE = "AES";
    private static final String CIPHER_TYPE = "AES/ECB/PKCS5Padding";
    private static final Charset FORMAT = StandardCharsets.UTF_8;

    private SecretKey secretKey;

    public Encryptor() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(KEY_TYPE);
            keyGen.init(256);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public Encryptor(byte[] key){
        secretKey = new SecretKeySpec(key, KEY_TYPE);
    }

    public Encryptor(SecretKey key){
        this.secretKey = key;
    }

    public String encrypt(String str) {
        try {
            Cipher eCipher = Cipher.getInstance(CIPHER_TYPE);
            eCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedPassBytes = eCipher.doFinal(str.getBytes(FORMAT));
            byte[] encodedPass = Base64.getEncoder().encode(encryptedPassBytes);

            return new String(encodedPass, FORMAT);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String decrypt(String str) {
        try {
            Cipher dCipher = Cipher.getInstance(CIPHER_TYPE);
            dCipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decoded = Base64.getDecoder().decode(str.getBytes(FORMAT));
            byte[] decipheredTextBytes = dCipher.doFinal(decoded);

            return new String(decipheredTextBytes, FORMAT);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SecretKey getSecretKey(){
        return secretKey;
    }
}