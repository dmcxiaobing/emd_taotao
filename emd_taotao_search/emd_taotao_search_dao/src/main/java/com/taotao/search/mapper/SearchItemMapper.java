package com.taotao.search.mapper;
/**
 * 搜索的自定义mapper接口。因为涉及多表查询，所以逆向工程不能使用
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	/**
	 * 获取所有商品数据
	 */
	List<SearchItem> getItemList();
}
