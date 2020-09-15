package com.mine.utils;

import org.apache.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LdapUtil {
    private static Logger logger = Logger.getLogger ( LdapUtil.class );

    private static final String HOST = "192.168.100.21";
    private static final String PORT = "389";
    private static final String LDAPURL = "ldap://" + HOST + ":" + PORT;

    public static Boolean connect(String username , String password) {
        DirContext ctx = null;
        Hashtable<String, String> HashEnv = new Hashtable<String, String> ( );
        // LDAP访问安全级别(none,simple,strong)
        HashEnv.put ( Context.SECURITY_AUTHENTICATION , "simple" );
        //AD的用户名
        HashEnv.put ( Context.SECURITY_PRINCIPAL , username );
        //AD的密码
        HashEnv.put ( Context.SECURITY_CREDENTIALS , password );
        // LDAP工厂类
        HashEnv.put ( Context.INITIAL_CONTEXT_FACTORY , "com.sun.jndi.ldap.LdapCtxFactory" );
        //连接超时设置为5秒
        HashEnv.put ( "com.sun.jndi.ldap.connect.timeout" , "5000" );
        // 默认端口389
        HashEnv.put ( Context.PROVIDER_URL , LDAPURL );
        try {
            // 初始化上下文
            ctx = new InitialDirContext ( HashEnv );
            return true;
        } catch ( AuthenticationException e ) {
            logger.error ( "AD身份验证失败: " + e.getMessage ( ) );
        } catch ( javax.naming.CommunicationException e ) {
            logger.error ( "AD域连接失败: " + e.getMessage ( ) );
        } catch ( Exception e ) {
            logger.error ( "身份验证未知异常: " + e.getMessage ( ) );
        } finally {
            if (null != ctx) {
                try {
                    ctx.close ( );
                    ctx = null;
                } catch ( Exception e ) {
                    e.printStackTrace ( );
                }
            }
        }
        return false;
    }
}