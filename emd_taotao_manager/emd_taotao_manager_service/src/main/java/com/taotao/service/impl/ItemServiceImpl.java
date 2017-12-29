package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

/**
 * 商品管理的service
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

	/**
	 * 根据Id获取到商品
	 */
	@Override
	public TbItem getItemById(long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	/**
	 * 分页获取到所有商品
	 */
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 取出查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		// 设置返回的数据
		result.setRows(list);
		// 设置总记录数
		result.setTotal(pageInfo.getTotal());
		// 返回结果
		return result;
	}
	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		// 生成商品id
		long itemId = IDUtils.genItemId();
		// 补全item的属性
		item.setId(itemId);
		// 设置商品的状态，1-正常 2-下架 3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 向商品表插入数据
		itemMapper.insert(item);
		// 创建一个商品描述表对应的pojo
		TbItemDesc itemDesc = new TbItemDesc();
		// 补全pojo的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		// 返回添加成功的结果
		return TaotaoResult.ok();
	}

}
