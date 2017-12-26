package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面展示 Controller
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Controller
public class PageController {
	/**
	 * 进入转发到首页
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	/**
	 * 传入某个jsp页面，直接转发到某个jsp
	 * @PathVariable 将{page}的值映射到 方法参数page中
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
