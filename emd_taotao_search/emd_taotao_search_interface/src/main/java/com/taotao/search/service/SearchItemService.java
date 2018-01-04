package com.taotao.search.service;
/**
 * 搜商品导入索引库
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */

import com.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {
	/**
	 * 一建导入索引库
	 */
	TaotaoResult importItemsToIndex();
}
