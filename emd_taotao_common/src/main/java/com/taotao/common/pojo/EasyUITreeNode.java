package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * 返回集合的tree数据
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class EasyUITreeNode implements Serializable {
	// parentId 父节点id
	private long id;
	// 标题名称
	private String text;
	// 如果节点下有子节点则是closed，如果没有子节点则是open
	private String state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
