package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 简单测试redis.使用jedis
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class TestJedis {

	/**
	 * 简单测试.使用单机版
	 */
	@Test
	public void testJedis() {
		// 创建一个jedis对象。需要指定服务器的ip和端口号
		Jedis jedis = new Jedis("192.168.1.188", 6379);
		// 设置连接密码  
		jedis.auth("redis");  
		// 直接操作数据库
		jedis.set("jedis-key", "david");
		// 取出数据
		System.out.println(jedis.get("jedis-key"));
		// 关闭jedis
		jedis.close();
	}

	/**
	 * 使用数据库连接池操作对象,使用单机版
	 */
	// @Test
	public void testJedisPool() {
		// 创建jedispool对象，需要制定服务器ip和端口号
		JedisPool jedisPool = new JedisPool("192.168.197.128", 6379);
		// 获取jedis对象
		Jedis jedis = jedisPool.getResource();
		// 操作redis
		jedis.set("jedis-", "david");
		// 取出数据
		System.out.println(jedis.get("jedis-"));
		// 关闭jedis
		jedis.close();
		jedisPool.close();
	}

	/**
	 * 使用集群版。进行测试
	 */
	public void testJedisCluster() {
		// 创建一个jedisCluster对象，构造参数set类型，集合中每个元素是hostAndPort类型
		Set<HostAndPort> nodes = new HashSet<>();
		// 向集合中添加节点
		nodes.add(new HostAndPort("192.168.25.153", 7001));
		nodes.add(new HostAndPort("192.168.25.153", 7002));
		nodes.add(new HostAndPort("192.168.25.153", 7003));
		nodes.add(new HostAndPort("192.168.25.153", 7004));
		nodes.add(new HostAndPort("192.168.25.153", 7005));
		nodes.add(new HostAndPort("192.168.25.153", 7006));

		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 直接使用JedisCluster操作redis,自带连接池，jedisCluster对象也可以是单利的
		jedisCluster.set("cluster-test", "hello jedis cluster");
		String string = jedisCluster.get("cluster-test");
		System.out.println(string);
		// 系统关闭前关闭JedisCluster
		jedisCluster.close();

	}

}
