package com.taotao.jedis;
/**
 * 封装一些常用的方法
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public interface JedisClient {
	
	 /**
     * 设置redis中指定key的值，value类型为String的使用此方法
     */
	String set(String key, String value);
	/**
     * 获取redis中指定key的值，value类型为String的使用此方法
     */
	String get(String key);
    /**
     * 删除redis中指定key的值项
     */
    void del(String key);
    /**
     * 判断某个key是否存在
     */
	Boolean exists(String key);
	/**
	 * 设置key的过期时间。
	 * @param key 键名
	 * @param seconds 过期时间值
	 */
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
}
