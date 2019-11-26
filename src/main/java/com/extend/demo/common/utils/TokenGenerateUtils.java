package com.extend.demo.common.utils;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;

public class TokenGenerateUtils {

    private static final String ENCRYPTION_TYPE = "AES";

    private static final String CIPHER = "AES/ECB/PKCS5Padding";


    public static String generateToken(String pass) {
        pass = pass + System.currentTimeMillis();
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_TYPE);
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            Key key = new SecretKeySpec(byteKey, ENCRYPTION_TYPE);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] tokenByte = cipher.doFinal(pass.getBytes());
            String tokenStr = Hex.encodeHexString(tokenByte);
            System.out.println("加密后：" + tokenStr);

            /*cipher.init(Cipher.DECRYPT_MODE,key);
            tokenByte=cipher.doFinal(tokenByte);
            System.out.println("解密后："+new String(tokenByte));*/

            return tokenStr;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String pass = "zhangsan1995";
        System.out.println(generateToken(pass));
    }
}
