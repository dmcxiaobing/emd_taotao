package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

/**
 * 索引库维护的controller
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Controller
public class IndexManagerController {
	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 一建导入索引库
	 */
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult importIndex() {
		TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
		return taotaoResult;
	}
}
