package com.mine.utils;

import java.sql.*;

/**
 * @author Teng
 * @create 2019-12-11
 */
public class JDBC2Util {
    public static void main(String[] args) {
        testMysql60 ( );
//        testSqlServer ();
//        testMysql ();
//        testOracle ();
    }


    public static void testSqlServer() {
        final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        final String url = "jdbc:sqlserver://192.168.100.120:1433;databasename=avic";
        final String username = "sa";
        final String password = "Abc123456";
        final String sql = "select * from tt1";
        testDB ( driver , url , username , password , sql );
    }

    public static void testOracle() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url2 = "jdbc:oracle:thin:@//192.168.100.60:1521/orcl";
        String username2 = "MY_TEST";
        String pwd2 = "Abc123456";
        String testSql2 = "select * from TB_TEST";
        testDB ( driver , url2 , username2 , pwd2 , testSql2 );
    }

    public static void testMysql() {
        String driver = "com.mysql.jdbc.Driver";
        String url1 = "jdbc:mysql://127.0.0.1:3306/test";
        String username1 = "root";
        String pwd1 = "123456";
        String testSql1 = "select * from tb_test";
        testDB ( driver , url1 , username1 , pwd1 , testSql1 );
    }

    public static void testMysql60() {
        String driver = "com.mysql.jdbc.Driver";
        String url1 = "jdbc:mysql://192.168.100.60:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username1 = "root";
        String pwd1 = "Abc123!@#";
        String testSql1 = "select * from tb_test";
        testDB ( driver , url1 , username1 , pwd1 , testSql1 );
    }


    public static void testDB(String driver , String url , String username , String pwd , String testSelectSql) {
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
