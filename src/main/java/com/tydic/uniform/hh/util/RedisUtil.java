package com.tydic.uniform.hh.util;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**   
 * Redis操作接口
 *
 * @author gb.zhou
 * @version 1.0 2017-03-05 pm 21:18   
 */
public class RedisUtil {
	private static Logger log = Logger.getLogger(RedisUtil.class);
	
    private static JedisPool redisPool = null;
	//private static String ip = "120.77.44.145";
    private String ip;
	
	//private static int port = 6389;
	private  int port;
	
     
    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
/*    static  {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            
            redisPool = new JedisPool(config, ip, port);
    }*/
    
    public void init(){
    	 JedisPoolConfig config = new JedisPoolConfig();
         //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
         //如果赋值为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽)。
         config.setMaxTotal(500);
         //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
         config.setMaxIdle(5);
         //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
         config.setMaxWaitMillis(1000 * 100);
         //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
         config.setTestOnBorrow(true);
         
         redisPool = new JedisPool(config, ip, port);
    }
     
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
	private static void returnResource(Jedis redis) {
        if (redis != null) {	
        	redis.close();
        }
    }
	
	private static void staticPool(JedisPool redisPool){
		int active = redisPool.getNumActive();
		int idle = redisPool.getNumIdle();
		int waiters = redisPool.getNumWaiters();
		
		log.info("JedisPool statics active: " + active + ", idle: " + idle + ", waiters: " + waiters);
	}
	
    /**
     * 存数据
     * 
     * @param key
     * @param value
     * @param exp
     * @return
     */
	public static String set(String key, String value, int exp){
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			if(exp > 0){
				return redis.setex(key, exp, value);
			}else{
				return redis.set(key, value);
			}
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally{
			returnResource(redis);
		}
		return null;
	}
	
    /**
     * 存数据
     * 
     * @param key
     * @param value
     * @param exp
     * @return
     */
	public static String setByByte(byte[] key, byte[] value, int exp){
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			if(exp > 0){
				return redis.setex(key, exp, value);
			}else{
				return redis.set(key, value);
			}
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally{
			returnResource(redis);
		}
		return null;
	}
     
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
	public static String get(String key) {
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.get(key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
	public static byte[] getByByte(byte[] key) {
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.get(key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
	public static boolean exsits(String key) {
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.exists(key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return false;
	}
	
    /**
     * 累加key对应的value值
     * 
     * @param key
     * @param value
     * @param exp
     * @return
     */
	public static Long incr(String key){
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.incr(key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
    /**
     * 删除某个key
     * 
     * @param key
     * @return
     */
	public static Long del(String key) {
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.del(key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * set集合添加元素
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long sadd(String key, String value){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.sadd(key, value);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * 删除set集合指定元素
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long srem(String key, String value){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.srem(key, value);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * map集合添加元素
	 * @param hashName
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long hset(String hashName, String key, String value){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			//redis.hs
			return redis.hset(hashName, key, value);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * 删除map集合指定元素
	 * @param hashName
	 * @param key
	 * @return
	 */
	public static Long hdel(String hashName, String... key){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.hdel(hashName, key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * 获取map集合指定元素
	 * @param hashName
	 * @param key
	 * @return
	 */
	public static String hget(String hashName, String key){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.hget(hashName, key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * 判断map集合是否存在某个key
	 * @param hashName
	 * @return
	 */
	public static boolean hexists(String hashName, String key){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.hexists(hashName, key);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return false;
	}
	
	/**
	 * 获取map集合所有的key
	 * @param hashName
	 * @return
	 */
	public static Set<String> hkeys(String hashName){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.hkeys(hashName);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	/**
	 * 获取map集合所有的value
	 * @param hashName
	 * @return
	 */
	public static List<String> hvals(String hashName){
		
		Jedis redis = null;
		try {
			staticPool(redisPool);
			redis = redisPool.getResource();
			return redis.hvals(hashName);
		} catch (Exception e) {
			log.error("获取redis连接异常：", e);
		} finally {
			returnResource(redis);
		}
		return null;
	}
	
	
	
	public  String getIp() {
		return ip;
	}

	public  void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args){
		String key = "token";
		String value = "piccasi";
		int exp = 20;
		String ret = null;
		//String ret = set(key, value, 0);
		//System.out.println(ret);
		ret = get(key);
		System.out.println(ret);
		
		Long hs = hset("active_session_map", "token1", "{haha}");
		hs = hset("active_session_map", "token1", "{hehe}");
		hs = hset("active_session_map", "token2", "{gaga}");
		System.out.println("hs: " + hs);
		String hg = hget("active_session_map", "token1");
		
		System.out.println("存在: " + hexists("active_session_map", "token5"));
		
		
		System.out.println("hg: " + hg);
		
		hg = hget("active_session_map", "token2");
		System.out.println("hg: " + hg);
		
	}
}