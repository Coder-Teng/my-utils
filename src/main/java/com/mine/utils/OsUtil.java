package com.mine.utils;

import java.io.*;

/**
 * @author Teng
 * @create 2019-12-13
 */
public class OsUtil {
    public static void main(String[] args) {
        String osType = System.getProperty ( "os.name" );
        System.out.println ( "osType = " + osType );
        String userDir = System.getProperty ( "user.dir" );
        System.out.println ( "userDir = " + userDir );

        File f = new File ( "" );
        System.out.println ( "f.getAbsolutePath ( ) = " + f.getAbsolutePath ( ) );

//        readParamFromTxt ( "text.txt" );
        ReadFileByBytes ( "text.txt" );
        ReadFileByChar ( "text.txt" );
        ReadFileByLine ( "text.txt" );
    }

    public static void readParamFromTxt(String paramsTxtPath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader ( new FileReader ( new File ( paramsTxtPath ) ) );
            String line = null;
            while ((line = br.readLine ( )) != null) {
                System.out.println ( line );
            }
        } catch (
                IOException e ) {
            e.printStackTrace ( );
        } finally {
            if (br != null) {
                try {
                    br.close ( );
                } catch ( IOException e ) {
                    e.printStackTrace ( );
                }
            }
        }
    }

    public static void ReadFileByLine(String filename) {
        File file = new File ( filename );
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream ( file );
            reader = new InputStreamReader ( is );
            bufferedReader = new BufferedReader ( reader );
            String line = null;
            while ((line = bufferedReader.readLine ( )) != null) {
                System.out.println ( line );
            }

        } catch ( FileNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close ( );
                if (null != reader)
                    reader.close ( );
                if (null != is)
                    is.close ( );
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
    }

    /**
     * 按字节读取文件
     *
     * @param filename
     */
    public static void ReadFileByBytes(String filename) {
        File file = new File ( filename );
        InputStream is = null;
        try {
            is = new FileInputStream ( file );
            int index = 0;
            while (-1 != (index = is.read ( ))) {
                System.out.write ( index );
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace ( );
        } finally {
            try {
                if (null != is)
                    is.close ( );

            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
        System.out.println ( "-----------------------------------" );
        try {
            is = new FileInputStream ( file );
            byte[] tempbyte = new byte[1000];
            int index = 0;
            while (-1 != (index = is.read ( tempbyte ))) {
                System.out.write ( tempbyte , 0 , index );
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace ( );
        } finally {
            try {
                if (null != is)
                    is.close ( );

            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
    }

    /**
     * 按字符读取文件
     *
     * @param filename
     */
    public static void ReadFileByChar(String filename) {
        File file = new File ( filename );
        InputStream is = null;
        Reader isr = null;
        try {
            is = new FileInputStream ( file );
            isr = new InputStreamReader ( is );
            int index = 0;
            while (-1 != (index = isr.read ( ))) {
                System.out.print ( (char) index );
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } finally {
            try {
                if (null != is)
                    is.close ( );
                if (null != isr)
                    isr.close ( );
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
    }


}
