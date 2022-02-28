package cn.cast.jedis;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.cast.util.JedisPoolUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nana
 * @date 2021/4/14 12:54 上午
 */
//jedis的测试类
public class JedisTest {
    @Test
    public void test(){
        //获取连接
        Jedis jedis =new Jedis("localhost",6379);
        // 操作
        jedis.set("name","nana");
        //关闭连接
        jedis.close();
    }
    @Test
    public void stringUse(){
        //获取连接,本机默认值
        Jedis jedis =new Jedis();
        // 操作
        jedis.set("name","nana1");
        String name =jedis.get("name");
        System.out.println(name);
        //可以使用setex()方法可以指定过期时间
        jedis.setex("activecode",20,"激活码"); //将键值对存入redis，20秒之后自动删除键值对
        //关闭连接
        jedis.close();
    }
    @Test
    public void hashUse(){
        //获取连接,本机默认值
        Jedis jedis =new Jedis();
        // 存储hash
        jedis.hset("user","name","lisi");
        jedis.hset("user","age","18");
        jedis.hset("user","gender","male01");
        //获取hash
        System.out.println(jedis.hget("user","name"));
        //获取所有的hash所有的map中的数据
        Map<String,String> user=jedis.hgetAll("user");
        Set<String> keySet = user.keySet();
        //遍历所有的key获取value
        for (String key:keySet){
            //获取value
            String value=user.get(key);
            System.out.println(key+ ":"+value);
        }
        //关闭连接
        jedis.close();
    }
    @Test
    public void listUse(){
        Jedis jedis=new Jedis();
        jedis.lpush("mylist","a","b","c");//从左边存 cba
        jedis.rpush("mylist","a","b","c");//从右边存 abc
        jedis.lrange("mylst",0,-1);
        List<String> list= jedis.lrange("mylist",0,-1);
        System.out.println(list);
        //弹出
        String element=jedis.lpop("mylist");//从左边弹出
        System.out.println(element);
        String element1=jedis.rpop("mylist");
        System.out.println(element);
        System.out.println(jedis.lrange("mylist",0,-1));
        jedis.close();
    }
    @Test
    public void setUse(){
        Jedis jedis=new Jedis();
        jedis.sadd("myset","php","c++","java");
        Set<String> set=jedis.smembers("myset");
        System.out.println(set);
        jedis.close();
    }
    @Test
    public void sortedsetUse(){
        Jedis jedis=new Jedis();
        //存储
        jedis.zadd("mysort",3,"亚瑟");
        jedis.zadd("mysort",30,"后裔");
        jedis.zadd("mysort",55,"孙悟空");
        Set<String> mysortedset =jedis.zrange("mysort",0,-1);
        System.out.println(mysortedset);
        jedis.close();
    }
    @Test
    public void jedisPoolUse(){
        //创建一个配置对象 根据业务做 一些配置
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(30);//设置最大连接数
        jedisPoolConfig.setMaxIdle(10);//设置最大空闲连接数
        //创建jedis连接池对象
        JedisPool jedisPool=new JedisPool(jedisPoolConfig,"localhost",6379);
        //获取连接
        Jedis jedis=jedisPool.getResource();
        //使用
        jedis.set("hello","world");
        //关闭，归还到连接池
        jedis.close();
    }
    @Test
    public void testJedisPoolUtils(){
        //通过连接池工具类获取
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set("hello","word");
        jedis.close();
    }
}
