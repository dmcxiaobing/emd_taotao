package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
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
	
	// token存在cookie中的键
	@Value("${TOKEN_KEY_COOKIE}")
	private String TOKEN_KEY_COOKIE;
	
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
	
	/**
	 * 登陆
	 */
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response) {
		// 接受两个参数。用户名和密码。调用service进行登录
		TaotaoResult taotaoResult = userService.login(username, password);
		// 从返回结果中取出token
		String token = taotaoResult.getData().toString();
		if (taotaoResult.getStatus() == 200) {
			CookieUtils.setCookie(request, response,TOKEN_KEY_COOKIE , token);
		}
		// 响应结果数据，json数据，包含token
		return taotaoResult;
	}

	/**
	 * 根据token，查询用户信息
	 */
	@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult getUserByToken(@PathVariable String token,HttpServletRequest request,HttpServletResponse response) {
		// 根据token，查询用户信息
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		// 响应结果数据，json数据
		return taotaoResult;
	}
	
	/**
	 * 根据token，清除redis中键
	 */
	@RequestMapping(value="/user/deleteToken/{token}",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult deleteToken(@PathVariable String token,HttpServletRequest request,HttpServletResponse response) {
		// 根据token，清除redis的信息
		TaotaoResult taotaoResult = userService.deleteToken(token);
		// 响应结果数据，json数据
		return taotaoResult;
	}
	
}
