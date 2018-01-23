package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/***
 * 全局异常处理器
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	/**
	 * 异常处理
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		logger.info("进入全局异常处理器");
		logger.debug("测试handler的类型:"+handler.getClass());
		// 控制台打印异常
		e.printStackTrace();
		// 向日志文件中写入异常
		logger.error("系统发生异常："+e);
		// 这里可以发邮件，或者发短信进行告知管理员
		// 最后展示错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message","您的网络有问题，请稍后重试。");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
