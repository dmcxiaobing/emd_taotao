package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 内容管理
 * @author ：david  
 * @新浪微博 ：https://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public interface ContentService {
	/**
	 * 添加内容
	 */
	TaotaoResult addContent(TbContent content);
	/**
	 * 根据cid查询轮廓图的内容列表
	 */
	List<TbContent> getContentByCid(Long aD1_CATEGORY_ID);

}
