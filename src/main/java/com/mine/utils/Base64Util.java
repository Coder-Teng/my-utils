package com.mine.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;

/**
 * @author Teng
 * @create 2020-01-07
 */
public class Base64Util {

    public static String enCode(String str) {
        byte[] bytes = Base64.encodeBase64 ( str.getBytes ( ) );
        if (bytes != null || bytes.length != 0) {
            return new String ( bytes );
        }
        return null;
    }

    public static String deCode(String str) {
        byte[] bytes = Base64.decodeBase64 ( str.getBytes ( ) );
        if (bytes != null || bytes.length != 0) {
            return new String ( bytes );
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "AB";
        System.out.println ( Arrays.toString ( str.getBytes ( ) ) );
        System.out.println ( enCode ( str ) );
        str = "ABM";
        System.out.println ( Arrays.toString ( str.getBytes ( ) ) );
        System.out.println ( enCode ( str ) );

        String strAVIC = "cape";
        System.out.println ( enCode ( strAVIC ) );

    }

}
