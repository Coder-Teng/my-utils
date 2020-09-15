package com.mine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**获取linux的ip、子网掩码、网关、DNS的工具类
 * @author Teng
 * @create 2020-03-25
 */
public class NetWorkUtil {

    public static void main(String[] args) {
        List<NetWorkInfo> netWorkInfo = getNetWorkInfo ( );
        List<String> dnsInfo = getDnsInfo ( );
        System.out.println ( "netWorkInfo = " + netWorkInfo );
        System.out.println ( "dnsInfo = " + dnsInfo );
    }

    /**
     * 获取ip、子网掩码、网关
     *
     * @return
     */
    public static List<NetWorkInfo> getNetWorkInfo() {
        String cmdIp = "ip addr show | grep inet | grep brd | awk '{print$2,$4}'";
        String[] cmds = new String[]{"/bin/bash" , "-c" , cmdIp};
        List<NetWorkInfo> netWorkInfoList = null;
        BufferedReader br = null;
        String line = null;
        try {
            ProcessBuilder pb = new ProcessBuilder ( cmds );
            Process process = pb.start ( );
            br = new BufferedReader ( new InputStreamReader ( process.getInputStream ( ) ) );
            netWorkInfoList = new ArrayList<NetWorkInfo> ( );
            while ((line = br.readLine ( )) != null) {
                NetWorkInfo netWorkInfo = extractNetWorkInfo ( line );
                if (netWorkInfo != null) {
                    netWorkInfoList.add ( netWorkInfo );
                }
            }
        } catch ( Exception e ) {
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
        return netWorkInfoList;
    }

    /**
     * 提取ip、子网掩码、网关信息
     *
     * @param line 例子：192.168.106.175/24 192.168.106.255
     * @return
     */
    private static NetWorkInfo extractNetWorkInfo(String line) {
        if (line == null || "".equals ( line )) {
            return null;
        }
        String[] infos = line.split ( " " );
        if (infos == null || infos.length != 2) {
            return null;
        }
        String[] ipMask = infos[0].split ( "/" );
        if (ipMask == null || ipMask.length != 2) {
            return null;
        }
        String ip = ipMask[0].trim ( );
        String mask = ipMask[1].trim ( );
        mask = transMaskToStr ( mask );
        if (mask == null) {
            return null;
        }
        String broadcast = infos[1].trim ( );
        NetWorkInfo info = new NetWorkInfo ( ip , mask , broadcast );
        return info;
    }

    /**
     * 获取DNS
     *
     * @return
     */
    public static List<String> getDnsInfo() {
        String cmdDns = "cat /etc/resolv.conf | grep -v '#'";
        String[] cmds = new String[]{"/bin/bash" , "-c" , cmdDns};
        List<String> dnsList = null;
        BufferedReader br = null;
        String line = null;
        try {
            ProcessBuilder pb = new ProcessBuilder ( cmds );
            Process process = pb.start ( );
            br = new BufferedReader ( new InputStreamReader ( process.getInputStream ( ) ) );
            dnsList = new ArrayList<String> ( );
            while ((line = br.readLine ( )) != null) {
                String dns = extractDns ( line );
                if (dns != null) {
                    dnsList.add ( dns );
                }
            }
        } catch ( Exception e ) {
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
        return dnsList;
    }

    /**
     * 提取DNS
     *
     * @param line 例子： nameserver        218.85.152.99
     * @return
     */
    private static String extractDns(String line) {
        if (line == null || "".equals ( line )) {
            return null;
        }
        int startPos = -1;
        int endPos = -1;
        for (int i = 0; i < line.length ( ); i++) {
            char c = line.charAt ( i );
            if (c <= '9' && c >= '0') {
                startPos = i;
                break;
            }
        }
        for (int i = line.length ( ) - 1; i >= 0; i--) {
            char c = line.charAt ( i );
            if (c <= '9' && c >= '0') {
                endPos = i;
                break;
            }
        }
        if (startPos == -1 || endPos == -1) {
            return null;
        }
        return line.substring ( startPos , endPos + 1 );
    }

    /**
     * 将子网掩码转换为字符串
     *
     * @param maskNumStr
     * @return
     */
    private static String transMaskToStr(String maskNumStr) {
        int mask = 0;
        try {
            mask = Integer.parseInt ( maskNumStr );
        } catch ( Exception e ) {
            e.printStackTrace ( );
            return null;
        }
        StringBuilder maskStr = new StringBuilder ( );
        int[] maskIp = new int[4];
        for (int i = 0; i < maskIp.length; i++) {
            maskIp[i] = (mask >= 8) ? 255 : (mask > 0 ? (mask & 0xff) : 0);
            mask -= 8;
            maskStr.append ( maskIp[i] );
            if (i < maskIp.length - 1) {
                maskStr.append ( "." );
            }
        }
        return maskStr.toString ( );
    }

    static class NetWorkInfo {
        private String ip;
        private String mask;
        private String gateway;

        public NetWorkInfo() {
        }

        public NetWorkInfo(String ip , String mask , String gateway) {
            this.ip = ip;
            this.mask = mask;
            this.gateway = gateway;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getMask() {
            return mask;
        }

        public void setMask(String mask) {
            this.mask = mask;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        @Override
        public String toString() {
            return "NetWorkInfo{" +
                    "ip='" + ip + '\'' +
                    ", mask='" + mask + '\'' +
                    ", gateway='" + gateway + '\'' +
                    '}';
        }
    }
}

