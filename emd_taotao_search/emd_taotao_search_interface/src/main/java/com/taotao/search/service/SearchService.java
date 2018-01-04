package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * 搜索功能
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public interface SearchService {
	/**
	 * 搜索商品
	 * @param queryString 搜索条件
	 * @param page 当前页
	 * @param sEARCH_RESULT_ROWS 每页显示记录数
	 * @return 返回符合前端需要的结果数据
	 */
	SearchResult search(String queryString, Integer page, Integer sEARCH_RESULT_ROWS);

}
