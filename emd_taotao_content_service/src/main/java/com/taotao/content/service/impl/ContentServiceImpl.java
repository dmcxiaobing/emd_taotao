package com.taotao.content.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
/**
 * 内容管理
 * @author ：david  
 * @新浪微博 ：https://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	
	/**
	 * 添加内容
	 */
	@Override
	public TaotaoResult addContent(TbContent content) {
		// 补全pojo的属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入到数据库
		contentMapper.insert(content);
		return TaotaoResult.ok(content);
	}

}
