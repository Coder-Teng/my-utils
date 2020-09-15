import redis.clients.jedis.Jedis;

/**
 * @author Teng
 * @create 2020-06-11
 */
public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis ("192.168.100.80", 6379);  //指定Redis服务Host和port
        jedis.auth("123456"); //如果Redis服务连接需要密码，制定密码
        String result = jedis.set ( "test" , "aaaa" );
        System.out.println ( "result = " + result );
        //访问Redis服务
        String value = jedis.get("test");
        System.out.println ( "value = " + value );
        jedis.close(); //使用完关闭连接
    }
}
