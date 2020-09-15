package com.mine.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * @author Teng
 * @create 2020-04-10
 */
public class AESUtil {
    /**
     * 加密:
     *
     * @param key
     * @param input
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] encrypt(byte[] key , byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance ( "AES/ECB/PKCS5Padding" );
        SecretKey keySpec = new SecretKeySpec ( key , "AES" );
        cipher.init ( Cipher.ENCRYPT_MODE , keySpec );
        return cipher.doFinal ( input );
    }

    /**
     * 解密
     *
     * @param key
     * @param input
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] decrypt(byte[] key , byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance ( "AES/ECB/PKCS5Padding" );
        SecretKey keySpec = new SecretKeySpec ( key , "AES" );
        cipher.init ( Cipher.DECRYPT_MODE , keySpec );
        return cipher.doFinal ( input );
    }

    public static String encrypt(String content) {
        if (content.length ( ) < 1) {
            return null;
        }
        String encryptedStr = null;
        try {
            byte[] key = PASSWORD.getBytes ( "UTF-8" );
            byte[] data = content.getBytes ( "UTF-8" );
            byte[] encrypted = encrypt ( key , data );
            encryptedStr = Base64.encodeBase64URLSafeString ( encrypted );
        } catch ( Exception e ) {
            e.printStackTrace ( );
            return null;
        }
        return encryptedStr;
    }

    public static String decrypt(String content) {
        if (content.length ( ) < 1) {
            return null;
        }
        String decrypyStr = null;
        try {
            byte[] key = PASSWORD.getBytes ( "UTF-8" );
            byte[] base64 = Base64.decodeBase64 ( content );
            byte[] decrypt = decrypt ( key , base64 );
            decrypyStr = new String ( decrypt , "UTF-8" );
        } catch ( Exception e ) {
            e.printStackTrace ( );
            return null;
        }
        return decrypyStr;
    }

    // 128位密钥 =16 bytes Key:
    private static final String PASSWORD = "KLI9DA7787E466B4";

    public static void main(String[] args) {
        String message = "ABvcdddedads123!@#112-=212.12[]()";
        String encrypt = encrypt ( message );
        System.out.println ( "encrypt = " + encrypt );
        String decrypt = decrypt ( encrypt );
        System.out.println ( "decrypt = " + decrypt );
    }

}
