package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 遵循easyUI规则的数据json返回Javabean
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class EasyUIDataGridResult implements Serializable {

	private long total;
	private List rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
