package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索结果页的前端jsp需要的数据
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class SearchResult implements Serializable{
	// 总页数
	private long totalPages;
	// 总记录数
	private long recordCount;
	private List<SearchItem> itemList;
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	
}
