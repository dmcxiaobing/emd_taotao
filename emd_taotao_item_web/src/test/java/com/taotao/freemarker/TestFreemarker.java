package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 简单介绍freemarker，使用模板创建文件
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class TestFreemarker {

	/**
	 * freemarker的使用方法
	 */
	@Test
	public void testFreemarker()throws Exception{
		// 创建一个Configuration对象..模板文件
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 设置模板所在的路径
		configuration.setDirectoryForTemplateLoading(new File("F:\\MyEclipseSpace\\emd_taotao\\emd_taotao\\emd_taotao_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
		// 设置模板的字符节，这里使用utf-8
		configuration.setDefaultEncoding("utf-8");
		// 使用Configuration对象加载一个模板文件。需要指定模板文件的文件名
//		Template template = configuration.getTemplate("hello.ftl");
		Template template = configuration.getTemplate("student.ftl");
		// 创建一个数据集，可以是pojo，也可以是map。推荐使用map
		Map data = new HashMap();
		Student student = new Student(1, "qq986945193", 22, "广东");
		Student student1 = new Student(2, "qq986945193", 222, "广东");
		Student student2 = new Student(3, "qq986945193", 2221, "广东");
		List<Student> stuList = new ArrayList<>();
		stuList.add(student1);
		stuList.add(student2);
		data.put("hello", "hello freemarker");
		data.put("student", student);
		data.put("stuList", stuList);
		// 日期类型的处理
		data.put("date", new Date());
		data.put("val", "http://weibo.com/mcxiaobing");
		// 创建一个Writer对象，指定输出文件的路径及文件名
		Writer writer = new FileWriter("D:/hello.html");
		// 使用模板对象的process方法输出文件
		template.process(data, writer);
		// 关闭资源
		writer.close();
		
		
	}
}
