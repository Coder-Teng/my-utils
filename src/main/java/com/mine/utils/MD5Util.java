package com.mine.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String calculateMD5OfFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance ( "MD5" );
            fileInputStream = new FileInputStream ( file );
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read ( buffer )) != -1) {
                MD5.update ( buffer , 0 , length );
            }
            return new String ( Hex.encodeHex ( MD5.digest ( ) ) );
        } catch ( Exception e ) {
            e.printStackTrace ( );
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close ( );
                }
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
    }

    public static String stringToMD5(String text) {
        byte[] secretBytes = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance ( "MD5" );
            secretBytes = MD5.digest ( text.getBytes ( ) );
            return new String ( Hex.encodeHex ( secretBytes ) );
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace ( );
        }
        return null;
    }
}
