package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

/**
 * 商品分类的实现
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	/**
	 * 获取到所有商品分类的node
	 */
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		// 根据父节点id查询子节点列表
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		// 设置查询条件
		Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(tbItemCatExample);
		// 将查询结果转成EasyUITreeNode列表
		List<EasyUITreeNode> results = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbItemCat.getId());
			easyUITreeNode.setText(tbItemCat.getName());
			// 如果节点下有子节点“closed” 如果没有子节点“open”
			easyUITreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
			// 添加到节点列表
			results.add(easyUITreeNode);
		}
		return results;
	}

}
