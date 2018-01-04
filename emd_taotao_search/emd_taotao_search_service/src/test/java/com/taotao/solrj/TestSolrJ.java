package com.taotao.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solrJ的简单测试
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class TestSolrJ {

	public  final static String SOLR_URL= "http://192.168.197.128:8080/solr/collection1";
	/**
	 * 添加索引内容
	 * @throws Exception 
	 */
//	@Test
	public void testAddDocument() throws SolrServerException, Exception {
		// 创建一个SolrServer对象，使用子类HttpSolrServer
		SolrServer solrServer = new HttpSolrServer(SOLR_URL);
		// 创建一个文档对象，SolrInputDocument
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		// 向文档中添加域，必须有id域，域的名称必须在schema.xml中定义
		solrInputDocument.addField("id", "addTest");
		solrInputDocument.addField("item_title", "测试添加商品");
		solrInputDocument.addField("item_price", 2000);
		// 把文档对象写入索引库
		solrServer.add(solrInputDocument);
		//提交
		solrServer.commit();
	}
	
	
	/**
	 * 根据id删除索引库
	 */
//	@Test
	public void testDeleteDocumentById() throws SolrServerException, Exception {
		SolrServer solrServer = new HttpSolrServer(SOLR_URL);
		solrServer.deleteById("addTest");
		solrServer.commit();
	}
	/**
	 * 根据条件进行删除索引库
	 */
//	@Test
	public void deleteDocumentByQuery() throws SolrServerException, Exception  {
		SolrServer solrServer = new HttpSolrServer(SOLR_URL);
		solrServer.deleteByQuery("item_title:测试添加商品");
		solrServer.commit();
	}
	
	
	
	
}
