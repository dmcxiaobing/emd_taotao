package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.pojo.TbContentExample;

/**
 * 内容管理
 * 
 * @author ：david
 * @新浪微博 ：https://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	/**
	 * 首页redis缓存的key
	 */
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;

	@Autowired
	private JedisClient jedisClient;

	/**
	 * 添加内容
	 */
	@Override
	public TaotaoResult addContent(TbContent content) {
		// 补全pojo的属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入到数据库
		contentMapper.insert(content);
		// 同步redis缓存。可以直接删除，因为前台访问会更新redis。
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		return TaotaoResult.ok(content);
	}

	/**
	 * 根据内容分类cid，获取到内容。
	 * 
	 * 先从redis中查询，redis中没有再去查询数据库，查询完之后将结果更新到redis中。。
	 * 
	 * 注意：添加内容时，要更新redis数据，不然前台将看不到。
	 */
	@Override
	public List<TbContent> getContentByCid(Long cid) {
		try {
			// 先查询redis缓存。
			String json = jedisClient.hget(INDEX_CONTENT, cid + "");
			// 查询到结果，把json转换成list返回
			if (StringUtils.isNotBlank(json)) {
				System.out.println("查询Redis缓存了");
				// 不等于空，则有数据
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有数据，则需要查询数据库
		TbContentExample tbContentExample = new TbContentExample();
		TbContentExample.Criteria criteria = tbContentExample.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(tbContentExample);
		System.out.println("查询数据库了");
		// 将结果添加到redis中。添加缓存数据时，不能影响正常逻辑。
		try {
			jedisClient.hset(INDEX_CONTENT, "" + cid, JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
