package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.parser.ContentModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类
 * 
 * @author ：david
 * @新浪微博 ：https://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 查看所有内容分类
	 */
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 添加到结果列表
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 添加一个内容分类
	 * 
	 * @param parentId
	 *            属于哪个分类的
	 * @param name
	 *            分类的名称
	 * @return 返回结果
	 */
	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		// 创建一个pojo对象
		TbContentCategory tbContentCategory = new TbContentCategory();
		// 补全对象的属性
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		// 设置状态。可选值。1正常，2删除
		tbContentCategory.setIsParent(false);
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// 设置排序，默认值1
		tbContentCategory.setSortOrder(1);
		// 插入到数据库
		contentCategoryMapper.insert(tbContentCategory);
		// 判断父节点的状态。如果父节点为叶子节点则修改父节点。
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			// 以前没有子节点，这次有了子节点，所以更新，并添加到父节点
			parent.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return TaotaoResult.ok(tbContentCategory);

	}

}
