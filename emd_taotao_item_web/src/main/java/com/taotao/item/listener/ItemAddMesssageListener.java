package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 添加新商品后，直接根据freemarker模板。生成静态html。
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class ItemAddMesssageListener implements MessageListener{

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	// 静态页面输出路径
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	
	@Override
	public void onMessage(Message message) {
		try {
			// 从消息中取出商品id
			TextMessage textMessage = (TextMessage) message;
			String itemIdString = textMessage.getText();
			Long itemId = Long.parseLong(itemIdString);
			// 等待事务提交
			Thread.sleep(1000);
			// 根据商品id查询商品信息以及商品描述
			TbItem tbItem = itemService.getItemById(itemId);
			// 将tbitem转换成支持图片功能的item
			Item item = new Item(tbItem);
			TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
			// 使用freemarker生成静态页面
			Configuration configuration = freeMarkerConfigurer.createConfiguration();
			// 创建模板，加载模板对象
			Template template = configuration.getTemplate("item.ftl");
			// 准备模板所需要的数据
			Map data = new HashMap();
			data.put("item", item);
			data.put("itemDesc", tbItemDesc);
			// 指定输出的目录及文件名
			Writer writer = new FileWriter(new File(HTML_OUT_PATH + itemIdString + ".html"));
			// 生成静态页面
			template.process(data, writer);
			// 关闭资源
			if (writer!=null) {
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
