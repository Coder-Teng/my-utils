package com.mine.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StopWatch;

import javax.crypto.Cipher;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 以RSA算法为例，它的密钥有256/512/1024/2048/4096等不同的长度。
 * 长度越长，密码强度越大，当然计算速度也越慢。
 * 如果修改待加密的byte[]数据的大小，可以发现，
 * 使用512bit的RSA加密时，明文长度不能超过53字节，
 * 使用1024bit的RSA加密时，明文长度不能超过117字节，
 * 这也是为什么使用RSA的时候，总是配合AES一起使用，即用AES加密任意长度的明文，用RSA加密AES口令。
 */
public class RSAUtil {
    public static final String RSA_ALGORITHM = "RSA";
    public static final Charset UTF8 = Charset.forName ( "UTF-8" );

    public static void main(String[] args) throws Exception {
        // generate public and private keys
        StopWatch clock = new StopWatch ( );
        clock.start ( "生成公私钥" );
        KeyPair keyPair = buildKeyPair ( );
        PublicKey publicKey = keyPair.getPublic ( );
        PrivateKey privateKey = keyPair.getPrivate ( );
        clock.stop ( );

        String publicKeyString = base64Encode ( publicKey.getEncoded ( ) );
        System.out.println ( "publicKeyString=" + publicKeyString );
        String privateKeyString = base64Encode ( privateKey.getEncoded ( ) );
        System.out.println ( "privateKeyString=" + privateKeyString );
        System.out.println ( );

        clock.start ( "执行加解密" );
        // encrypt the message
        byte[] encrypted = encrypt ( privateKey , "This is a secret message" );
        System.out.println ( base64Encode ( encrypted ) );  // <<encrypted message>>
        System.out.println ( );
        // decrypt the message
        byte[] secret = decrypt ( publicKey , encrypted );
        System.out.println ( new String ( secret , UTF8 ) );     // This is a secret message
        clock.stop ( );
        System.out.println ( clock.prettyPrint ( ) );
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ( RSA_ALGORITHM );
        keyPairGenerator.initialize ( keySize );
        return keyPairGenerator.genKeyPair ( );
    }

    public static byte[] encrypt(PrivateKey privateKey , String message) throws Exception {
        Cipher cipher = Cipher.getInstance ( RSA_ALGORITHM );
        cipher.init ( Cipher.ENCRYPT_MODE , privateKey );

        return cipher.doFinal ( message.getBytes ( UTF8 ) );
    }

    public static byte[] decrypt(PublicKey publicKey , byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance ( RSA_ALGORITHM );
        cipher.init ( Cipher.DECRYPT_MODE , publicKey );

        return cipher.doFinal ( encrypted );
    }

    /**
     * 从字符串中加载公钥
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = base64Decode ( publicKeyStr );
            KeyFactory keyFactory = KeyFactory.getInstance ( RSA_ALGORITHM );
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec ( buffer );
            return (RSAPublicKey) keyFactory.generatePublic ( keySpec );
        } catch ( NoSuchAlgorithmException e ) {
            throw new RuntimeException ( e );
        } catch ( InvalidKeySpecException e ) {
            throw new RuntimeException ( e );
        }
    }

    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = base64Decode ( privateKeyStr );
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec ( buffer );
            KeyFactory keyFactory = KeyFactory.getInstance ( RSA_ALGORITHM );
            return (RSAPrivateKey) keyFactory.generatePrivate ( keySpec );
        } catch ( NoSuchAlgorithmException e ) {
            throw new RuntimeException ( e );
        } catch ( InvalidKeySpecException e ) {
            throw new RuntimeException ( e );
        }
    }

    public void savePublicKey(PublicKey publicKey) throws IOException {
        // 得到公钥字符串
        String publicKeyString = base64Encode ( publicKey.getEncoded ( ) );
        System.out.println ( "publicKeyString=" + publicKeyString );
        FileWriter fw = new FileWriter ( "publicKey.keystore" );
        BufferedWriter bw = new BufferedWriter ( fw );
        bw.write ( publicKeyString );
        bw.close ( );
    }

    public void savePrivateKey(PrivateKey privateKey) throws IOException {
        // 得到私钥字符串
        String privateKeyString = base64Encode ( privateKey.getEncoded ( ) );
        System.out.println ( "privateKeyString=" + privateKeyString );

        BufferedWriter bw = new BufferedWriter ( new FileWriter ( "privateKey.keystore" ) );
        bw.write ( privateKeyString );
        bw.close ( );
    }

    public static String base64Encode(byte[] data) {
        return new String ( Base64.encodeBase64 ( data ) );
    }

    public static byte[] base64Decode(String data) throws IOException {
        return Base64.decodeBase64 ( data.getBytes ( ) );
    }
}