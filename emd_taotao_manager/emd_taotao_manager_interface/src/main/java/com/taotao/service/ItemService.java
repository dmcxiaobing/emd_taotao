package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	public TbItem getItemById(long itemId);

	public EasyUIDataGridResult getItemList(Integer page, Integer rows);

	public TaotaoResult addItem(TbItem item, String desc);

	public TbItemDesc getItemDescById(Long itemId);
}
