package com.taotao.portal.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;

/**
 * 首页展示Controller
 */
@Controller
public class IndexController {
	// 首页banner的id为89，可参考tb_content表
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	/**
	 * 初始化图片的宽度和高度等尺寸
	 */
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;

	
	@Autowired
	private ContentService contentService;

	/**
	 * 转发到首页.并利用了redis缓存
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		// 根据cid查询轮廓图的内容列表
		List<TbContent> contentList = contentService.getContentByCid(AD1_CATEGORY_ID);
		// 把列表转换为Ad1Node列表，及index中广告
		List<AD1Node> add1Nodes = new ArrayList<AD1Node>();
		for (TbContent  content: contentList) {
			AD1Node node = new AD1Node();
			node.setAlt(content.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setSrc(content.getPic());
			node.setSrcB(content.getPic2());
			node.setHref(content.getUrl());
			//添加到节点列表
			add1Nodes.add(node);
		}
		// 把列表转换成json数据
		String jsonString = JsonUtils.objectToJson(add1Nodes);
		// 把json数据传递给页面
		model.addAttribute("ad1",jsonString);
		return "index";
	}
}
