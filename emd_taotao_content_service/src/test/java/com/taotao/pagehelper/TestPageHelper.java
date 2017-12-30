package com.taotao.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

/**
 * 一个简单的测试类。测试pagehelper
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class TestPageHelper {

	@Test
	public void testPageHelper() throws Exception {
		// 1，在mybatis的配置文件中配置分页插件
		// 2，在执行查询之前配置分页条件。使用PageHelper的静态方法
		PageHelper.startPage(1, 10);
		// 3，执行查询
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-dao.xml");
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
		// 创建Example对象
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		// 4，取分页信息。使用PageInfo对象获取
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println("总记录数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
		System.out.println("返回的记录数:" + list.size());

	}
}
