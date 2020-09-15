package com.mine.utils;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author Teng
 * @create 2020-06-11
 */
public class JedisUtil {

    public static void main(String[] args) {
//        Jedis jedis = new Jedis ("127.0.0.1",6379 );
        Jedis jedis = new Jedis ("192.168.100.60",6379 );
        jedis.auth ( "cape" );
        Set<String> keys = jedis.keys ( "*" );
        System.out.println ( "keys = " + keys );
        
    }
}
