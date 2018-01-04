package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * 搜索的controller
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Controller
public class SearchController {
	/**
	 * 每页显示数量
	 */
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer	SEARCH_RESULT_ROWS;

	/**
	 * 搜索服务注入
	 */
	@Autowired
	private SearchService searchService;
	
	/**
	 * 从主页点击搜索的按钮，跳转到这里，然后此方法进行查询数据，并转发到需要显示的jsp中
	 * 
	 * queryString:查询条件。  page：当前第几页
	 */
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
			Model model,
			@RequestParam(defaultValue="1")Integer page) throws Exception {
		// 调用服务执行查询。因为是get请求，所以处理一下乱码
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
	
		SearchResult searchResult = searchService.search(queryString,page,SEARCH_RESULT_ROWS);
		// 将数据保存到域中
		model.addAttribute("query",queryString);
		model.addAttribute("totalPages",searchResult.getTotalPages());
		model.addAttribute("itemList",searchResult.getItemList());
		model.addAttribute("page",page);
		
		// 转发到逻辑视图
		return "search";
		
	}
	
	
	
}
