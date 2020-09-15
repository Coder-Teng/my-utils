package com.mine.utils;

import java.sql.*;

/**
 * @author Teng
 * @create 2019-12-11
 */
public class JDBCUtil {
    public static void main(String[] args) {
//        String url1 = "jdbc:mysql://192.168.100.40:3306/cdms_terminal";
//        String username1 = "root";
//        String pwd1 = "123456";
//        String testSql1 = "select * from computer";
//        testMysql ( url1 , username1 , pwd1 , testSql1 );

//        String url2 = "jdbc:oracle:thin:@//192.168.2.177:1521/orcl";
//        String username2 = "C##CDMS_DBA";
//        String pwd2 = "centerm";
//        String testSql2 = "select * from computer";
//        testOracle ( url2 , username2 , pwd2 , testSql2 );

        final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        final String URL = "jdbc:sqlserver://192.168.100.120:1433;databasename=avic";
        final String USER_NAME = "sa";
        final String PASSWORD = "123456";
    }

    public static void testLocalMineOracle() {
        String url2 = "jdbc:oracle:thin:@//127.0.0.1:1521/orcl";
        String username2 = "C##MY_DBA";
        String pwd2 = "Abc123456";
        String testSql2 = "select * from computer";
        testOracle ( url2 , username2 , pwd2 , testSql2 );
    }

    public static void testLocalCdmsOracle() {
        String url2 = "jdbc:oracle:thin:@//127.0.0.1:1521/orcl";
        String username2 = "C##CDMS_DBA";
        String pwd2 = "centerm";
        String testSql2 = "select * from computer";
        testOracle ( url2 , username2 , pwd2 , testSql2 );
    }

    public static void testLocalCdmsMysql() {
        String url1 = "jdbc:mysql://127.0.0.1:3306/cdms_terminal";
        String username1 = "root";
        String pwd1 = "123456";
        String testSql1 = "select * from computer";
        testMysql ( url1 , username1 , pwd1 , testSql1 );
    }

    public static void testOracle(String url , String username , String pwd , String testSelectSql) {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            Class.forName ( "oracle.jdbc.driver.OracleDriver" );
            conn = DriverManager.getConnection ( url , username , pwd );
            System.out.println ( "conn = " + conn );
            if (testSelectSql == null || "".equals ( testSelectSql )) {
                conn.close ( );
                return;
            }
            state = conn.prepareStatement ( testSelectSql );
            rs = state.executeQuery ( );
            ResultSetMetaData rsmd = rs.getMetaData ( );
            int colCount = rsmd.getColumnCount ( );
            while (rs.next ( )) {
                for (int i = 1; i <= colCount; i++) {
                    if (i > 1) {
                        System.out.print ( "," );
                    }
                    System.out.print ( rs.getString ( i ) );
                }
                System.out.println ( );
            }
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( SQLException e ) {
            e.printStackTrace ( );
        } finally {
            try {
                if (rs != null) {
                    rs.close ( );
                }
                if (state != null) {
                    state.close ( );
                }
                if (conn != null) {
                    conn.close ( );
                }
            } catch ( SQLException e ) {
                e.printStackTrace ( );
            }
        }

    }

    public static void testMysql(String url , String username , String pwd , String testSelectSql) {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            Class.forName ( "com.mysql.jdbc.Driver" );
            conn = DriverManager.getConnection ( url , username , pwd );
            System.out.println ( "conn = " + conn );
            if (testSelectSql == null || "".equals ( testSelectSql )) {
                conn.close ( );
                return;
            }
            state = conn.prepareStatement ( testSelectSql );
            rs = state.executeQuery ( );
            ResultSetMetaData rsmd = rs.getMetaData ( );
            int colCount = rsmd.getColumnCount ( );
            while (rs.next ( )) {
                for (int i = 1; i <= colCount; i++) {
                    if (i > 1) {
                        System.out.print ( "," );
                    }
                    System.out.print ( rs.getString ( i ) );
                }
                System.out.println ( );
            }
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( SQLException e ) {
            e.printStackTrace ( );
        } finally {
            try {
                if (rs != null) {
                    rs.close ( );
                }
                if (state != null) {
                    state.close ( );
                }
                if (conn != null) {
                    conn.close ( );
                }
            } catch ( SQLException e ) {
                e.printStackTrace ( );
            }
        }

    }

    public static void testSqlServer(String driver,String url , String username , String pwd , String testSelectSql) {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            Class.forName ( driver );
            conn = DriverManager.getConnection ( url , username , pwd );
            System.out.println ( "conn = " + conn );
            if (testSelectSql == null || "".equals ( testSelectSql )) {
                conn.close ( );
                return;
            }
            state = conn.prepareStatement ( testSelectSql );
            rs = state.executeQuery ( );
            ResultSetMetaData rsmd = rs.getMetaData ( );
            int colCount = rsmd.getColumnCount ( );
            while (rs.next ( )) {
                for (int i = 1; i <= colCount; i++) {
                    if (i > 1) {
                        System.out.print ( "," );
                    }
                    System.out.print ( rs.getString ( i ) );
                }
                System.out.println ( );
            }
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( SQLException e ) {
            e.printStackTrace ( );
        } finally {
            try {
                if (rs != null) {
                    rs.close ( );
                }
                if (state != null) {
                    state.close ( );
                }
                if (conn != null) {
                    conn.close ( );
                }
            } catch ( SQLException e ) {
                e.printStackTrace ( );
            }
        }

    }
}
