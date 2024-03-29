package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 内容管理
 * @author ：david  
 * @新浪微博 ：https://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Controller
public class ContentController {
	
	
	@Autowired
	private ContentService contentService;

	/**
	 * 添加内容
	 */
	@ResponseBody
	@RequestMapping("/content/save")
	public TaotaoResult addContent(TbContent content) {
		return contentService.addContent(content);
	}
}
