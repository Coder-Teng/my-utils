package com.mine.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author Teng
 * @create 2020-04-03
 */
public class SocketClient {
    private static final int TIMEOUT = 8000;

    public static void main(String[] args) {
        String ip = "";
        int port = 9999;
        String encrytCom = "AAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBDDDDDDDDDDDDDDDDWWWWWWWWWWWWWWWWWWWSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS";
        String respStr = sendCommand ( ip , port , encrytCom );
        System.out.println ( "respStr = " + respStr );
    }

    public static String sendCommand(String proxyServerIp , int proxyServerPort , String encryptedCommand) {
        StringBuffer encryptedRespose = new StringBuffer ( );
        Socket socket = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            socket = new Socket ( );
            SocketAddress addr = new InetSocketAddress ( proxyServerIp , proxyServerPort );
            socket.connect ( addr , TIMEOUT );
            br = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) );
            bw = new BufferedWriter ( new OutputStreamWriter ( socket.getOutputStream ( ) ) );
            bw.write ( encryptedCommand );
            bw.flush ( );
            socket.shutdownOutput ();
            String line = null;
            System.out.println ( " = " );
            while ((line = br.readLine ( )) != null) {
                encryptedRespose.append ( line );
            }
            System.out.println ( "send = " + encryptedCommand );
            System.out.println ( "receive = " + encryptedRespose );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } finally {
            try {
                if (null != br) {
                    br.close ( );
                }
                if (null != bw) {
                    bw.close ( );
                }
                if (null != socket) {
                    socket.close ( );
                }
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
        return encryptedRespose.toString ( );
    }
}
