package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;

/**
 * 搜商品导入索引库
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	
	/**
	 * 一建导入索引库
	 */
	@Override
	public TaotaoResult importItemsToIndex() {
		try {
			// 先查询所有商品数据，符合索引要求的
			List<SearchItem> itemList = searchItemMapper.getItemList();
			// 遍历商品数据，添加到索引库
			for (SearchItem searchItem : itemList) {
				// 创建文档对象
				SolrInputDocument solrInputDocument = new SolrInputDocument();
				// 向文档中添加域
				solrInputDocument.addField("id", searchItem.getId());
				solrInputDocument.addField("item_title", searchItem.getTitle());
				solrInputDocument.addField("item_sell_point", searchItem.getSell_point());
				solrInputDocument.addField("item_price", searchItem.getPrice());
				solrInputDocument.addField("item_image", searchItem.getImage());
				solrInputDocument.addField("item_category_name", searchItem.getCategory_name());
				solrInputDocument.addField("item_desc", searchItem.getItem_desc());
				// 把文档写入到索引库
				solrServer.add(solrInputDocument);
				
			}
			// 提交
			solrServer.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "数据导入失败");
		}
		
		// 返回添加成功
		return TaotaoResult.ok();
	}

}
