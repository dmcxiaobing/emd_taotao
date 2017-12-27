package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品管理的controller
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 使用resultful根据iD获取到商品
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	/**
	 * 获取商品列表。返回json，遵循easyUI的格式。查询出所有的商品数据。
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	/**
	 * item 商品详情。
	 * desc 商品描述
	 * 
	 * 添加保存商品。
	 */
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc) {
		TaotaoResult result = itemService.addItem(item, desc);
		return result;
	}

}
