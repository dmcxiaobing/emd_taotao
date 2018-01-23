package com.taotao.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 网页静态处理。将freemarker模板生成文件。和spring整合。
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
@Controller
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 根据模板生成html文件。。整合了spring
	 */
	@RequestMapping("/genhtml")
	@ResponseBody
	public String genHtml() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, Exception {
		// 生成静态页面
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("hello.ftl");
		// 设置数据
		Map data = new HashMap<>();
		data.put("hello", "hello spring freemarker test");
		Writer writer = new FileWriter("D:/HELLO.HTML");
		// 生成
		template.process(data, writer);
		writer.close();
		return "OK";
	}
	

}
