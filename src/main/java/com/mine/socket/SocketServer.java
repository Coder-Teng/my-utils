package com.mine.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Teng
 * @create 2020-04-03
 */
public class SocketServer {
    public static void main(String[] args) {
        startProxyServer ( );
    }

    private static void startProxyServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket ( 9999 );
            while (true) {
                Socket scoket = serverSocket.accept ( );
                new Handler ( scoket ).start ( );
            }
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close ( );
                } catch ( IOException e ) {
                    e.printStackTrace ( );
                }
            }
        }
    }

    static class Handler extends Thread {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            String response = "EEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAKKKKKKKKKKKKKKKKKKKKKKKK";
            StringBuffer enCom = new StringBuffer ( );
            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                br = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) );
                bw = new BufferedWriter ( new OutputStreamWriter ( socket.getOutputStream ( ) ) );
                String line = null;
                while ((line = br.readLine ( )) != null) {
                    enCom.append ( line );
                }
                bw.write ( response );
                bw.flush ( );
                socket.shutdownOutput ( );
                System.out.println ( "receive = " + enCom );
                System.out.println ( "send = " + response );
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
                    if (null != this.socket) {
                        this.socket.close ( );
                    }
                } catch ( IOException e ) {
                    e.printStackTrace ( );
                }
            }
        }
    }
}

