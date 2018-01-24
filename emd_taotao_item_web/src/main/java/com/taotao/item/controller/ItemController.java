package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

/**
 * 商品详情页..利用缓存，并且声称静态html页面
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService; 
	
	
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId,Model model) {
		// 获取商品的信息
		TbItem tbItem = itemService.getItemById(itemId);
		// 新建一个item，只是为了处理图片地址的，因为多张图片是以逗号隔开的字符串。
		Item item = new Item(tbItem);
		// 获取商品描述信息
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		// 将数据传递到jsp中
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",tbItemDesc);
		// 返回逻辑视图
		return "item";
	}
}
