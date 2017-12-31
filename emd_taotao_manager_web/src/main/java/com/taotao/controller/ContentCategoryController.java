package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

/**
 * 内容分类管理
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	/**
	 * 获取所有内容分类。
	 * @param parentId 父节点ID，根据父节点ID获取子节点所有ID。默认为0
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	/**
	 * 添加一个内容分类
	 * @param parentId 属于哪个分类的
	 * @param name 分类的名称
	 * @return 返回结果
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId,String name) {
		return contentCategoryService.addContentCategory(parentId, name);
	}
}
