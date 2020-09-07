package me.border.utilities.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {

    private SecretKey secretKey;
    private Charset FORMAT = StandardCharsets.UTF_8;

    public Encryptor() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public Encryptor(byte[] key){
        secretKey = new SecretKeySpec(key, "AES");
    }

    public Encryptor(SecretKey key){
        this.secretKey = key;
    }

    public String encrypt(String str) {
        try {
            Cipher eCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
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
            Cipher dCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
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