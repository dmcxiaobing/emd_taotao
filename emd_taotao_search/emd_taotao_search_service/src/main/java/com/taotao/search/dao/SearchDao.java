package com.taotao.search.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

/**
 * 查询索引商品的通用dao
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;

	/**
	 * 根据条件查询数据
	 * 
	 * @param solrQuery
	 *            查询条件
	 * @return 查询结果
	 */
	public SearchResult search(SolrQuery solrQuery) throws Exception {
		SearchResult searchResult = new SearchResult();
		// 根据query对象进行查询。
		QueryResponse response = solrServer.query(solrQuery);
		// 取出查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		// 取出总记录数
		searchResult.setRecordCount(solrDocumentList.getNumFound());
		// 取出商品数据
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		// 把查询结果封装到searchResult对象中
		for (SolrDocument document : solrDocumentList) {
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name((String)document.get("item_category_name"));
			searchItem.setId((String)document.get("id"));
			// 取出一张图片
			String image = (String) document.get("item_image");
			if (StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			searchItem.setImage(image);
			searchItem.setPrice((long)document.get("item_price"));
			searchItem.setSell_point((String)document.get("item_sell_point"));
			// 取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(document.get("id")).get("item_title");
			String title = "";
			if (list!=null && list.size()>0) {
				// 显示高亮，但是没有高亮的也要有标题内容，所以添加了else
				title = list.get(0);
			}else {
				title = (String) document.get("item_title");
			}
			searchItem.setTitle(title);
			// 将单个商品挨个添加到集合中
			itemList.add(searchItem);
		}
		// 将商品结果添加到searchResult中
		searchResult.setItemList(itemList);
		// 返回带有总记录数和商品的数据
		return searchResult;
	}
}
