package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示登陆和注册页面的controller
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Controller
public class PageController {
	/**
	 * 转发到注册页面
	 */
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
	/**
	 * 转发到登陆页面
	 */
	@RequestMapping("/page/login")
	public String showLogin() {
		return "login";
	}
}
