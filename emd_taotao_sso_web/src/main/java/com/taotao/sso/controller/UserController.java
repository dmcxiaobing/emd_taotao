package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

/**
 * 用户处理的Controller
 * 
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 检测用户名是否可用 
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkUserData(@PathVariable String data, @PathVariable Integer type) {
		TaotaoResult taotaoResult = userService.checkData(data, type);
		return taotaoResult;
	}
	
	/**
	 * 注册
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult register(TbUser user) {
		TaotaoResult result = userService.register(user);
		return result;
	}
	
}
