package com.mine.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Teng
 * @create 2020-01-07
 */
public class EncodeUtil {

    /**
     * AES加密
     * @param content
     * @param slatKey
     * @param vectorKey
     * @return
     * @throws Exception
     */
    public String AesEncrypt(String content , String slatKey , String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance ( "AES/CBC/PKCS5Padding" );
        SecretKey secretKey = new SecretKeySpec ( slatKey.getBytes ( ) , "AES" );
        IvParameterSpec iv = new IvParameterSpec ( vectorKey.getBytes ( ) );
        cipher.init ( Cipher.ENCRYPT_MODE , secretKey , iv );
        byte[] encrypted = cipher.doFinal ( content.getBytes ( ) );
        return Base64.encodeBase64String ( encrypted );
    }

    /**
     * AES解密
     * @param base64Content
     * @param slatKey
     * @param vectorKey
     * @return
     * @throws Exception
     */
    public String AesDecrypt(String base64Content , String slatKey , String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance ( "AES/CBC/PKCS5Padding" );
        SecretKey secretKey = new SecretKeySpec ( slatKey.getBytes ( ) , "AES" );
        IvParameterSpec iv = new IvParameterSpec ( vectorKey.getBytes ( ) );
        cipher.init ( Cipher.DECRYPT_MODE , secretKey , iv );
        byte[] content = Base64.decodeBase64 ( base64Content );
        byte[] encrypted = cipher.doFinal ( content );
        return new String ( encrypted );
    }

    /**
     * 慢比较，防止基于时间差的攻击
     *
     * @param a
     * @param b
     * @return
     */
    private static boolean slowEquals(byte[] a , byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     * 生成盐串
     *
     * @return
     */
    public static String getSalt() {
        StringBuffer salt = new StringBuffer ( );
        SecureRandom ranGen = new SecureRandom ( );
        byte[] aesKey = new byte[32];
        ranGen.nextBytes ( aesKey );
        for (int i = 0; i < aesKey.length; i++) {
            String hex = Integer.toHexString ( 0xff & aesKey[i] );
            if (hex.length ( ) == 1)
                salt.append ( '0' );
            salt.append ( hex );
        }
        return salt.toString ( );
    }

    /**
     * SHA256加密
     *
     * @param str
     * @return
     */
    public static String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encdeStr = null;
        try {
            messageDigest = MessageDigest.getInstance ( "SHA-256" );
            byte[] hash = messageDigest.digest ( str.getBytes ( "UTF-8" ) );
            encdeStr = Hex.encodeHexString ( hash );
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace ( );
        } catch ( UnsupportedEncodingException e ) {
            e.printStackTrace ( );
        }
        return encdeStr;
    }
}
