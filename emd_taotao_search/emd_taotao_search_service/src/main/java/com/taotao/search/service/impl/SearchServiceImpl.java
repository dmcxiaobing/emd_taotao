package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

/**
 * 搜索的功能
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Service
public class SearchServiceImpl implements SearchService {

	
	@Autowired
	private SearchDao searchDao;
	/**
	 * 搜索商品。。由于搜索只需要将条件传递给dao就行了，所以，这里写一个dao
	 * 
	 * @param queryString
	 *            搜索条件
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示记录数
	 * @return 返回符合前端需要的结果数据
	 */
	@Override
	public SearchResult search(String queryString, Integer page, Integer rows) {
		try {
			// 首先创建一个SolrQuery对象
			SolrQuery solrQuery = new SolrQuery();
			// 根据查询条件拼装查询对象
			solrQuery.setQuery(queryString);
			// 设置分页条件
			if (page < 1) {
				page = 1;
			}
			if (rows < 1) {
				rows = 10;
			}
			// 设置从第几条记录开始
			solrQuery.setStart((page - 1) * rows);
			// 设置每页显示的记录数
			solrQuery.setRows(rows);

			// 设置默认搜索域，这里直接设置标题域
			solrQuery.set("df", "item_title");
			// 设置高亮显示
			solrQuery.setHighlight(true);
			solrQuery.addHighlightField("item_title");
			solrQuery.setHighlightSimplePre("<font color='red'>");
			solrQuery.setHighlightSimplePost("</font>");
			// 调用dao进行查询
			SearchResult searchResult = searchDao.search(solrQuery);
			// 计算查询结果的总页数..因为查询出的结果数据和总记录数
			long recordCount = searchResult.getRecordCount();
			long pages = recordCount/rows;
			if (recordCount%rows>0) {
				// 总记录数不能整除每页记录数，则加1
				pages+=1;
			}
			searchResult.setTotalPages(pages);
			// 返回结果
			return searchResult;
		} catch (Exception e) {
			return null;
		}
		
	}
}
