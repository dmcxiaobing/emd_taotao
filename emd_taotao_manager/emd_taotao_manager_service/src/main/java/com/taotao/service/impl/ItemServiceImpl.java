package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

/**
 * 商品管理的service
 * 
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	// 获取发送消息的topic
	@Resource(name="itemAddtopic")
	private Destination destination;
	// 商品数据在redis中缓存的前缀。以防重复id
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	// 设置redis数据缓存的时间。默认为一天。这样不是热点即会自动删除
	@Value("${TIEM_EXPIRE}")
	private Integer TIEM_EXPIRE;
	
	
	@Autowired
	private JedisClient jedisClient;
	
	
	/**
	 * 根据Id获取到商品
	 */
	@Override
	public TbItem getItemById(long itemId) {
		// 查询数据库之前先查询缓存
		try {
			String json = jedisClient.get(ITEM_INFO+":"+itemId+":BASE");
			if (StringUtils.isNotBlank(json)) {
				// 把json转换成就pojo返回
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果查询不到。则查询数据库
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		try {
			// 将查询到的数据。保存到redis
			jedisClient.set(ITEM_INFO+":"+itemId+":BASE", JsonUtils.objectToJson(tbItem));
			// 设置过期时间。提高缓存的利用率
			jedisClient.expire(ITEM_INFO+":"+itemId+":BASE", TIEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}

	/**
	 * 分页获取到所有商品
	 */
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 取出查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		// 设置返回的数据
		result.setRows(list);
		// 设置总记录数
		result.setTotal(pageInfo.getTotal());
		// 返回结果
		return result;
	}
	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		// 生成商品id
		long itemId = IDUtils.genItemId();
		// 补全item的属性
		item.setId(itemId);
		// 设置商品的状态，1-正常 2-下架 3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 向商品表插入数据
		itemMapper.insert(item);
		// 创建一个商品描述表对应的pojo
		TbItemDesc itemDesc = new TbItemDesc();
		// 补全pojo的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		// 向商品描述表中插入数据
		tbItemDescMapper.insert(itemDesc);
		// 向activemq发送添加商品的消息，这样搜索等其他模块，可以更新所需要的功能
		jmsTemplate.send(desc, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 发送商品的id
				TextMessage textMessage = session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
		// 返回添加成功的结果
		return TaotaoResult.ok();
	}
	/**
	 * 获取商品的描述信息
	 */
	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		// 查询之前先查询缓存
		try {
			String json = jedisClient.get(ITEM_INFO + ":" + itemId  + ":DESC");
			if (StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有查询数据库。
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		try {
			// 将查询结果保存到redis中
			jedisClient.set(ITEM_INFO + ":" + itemId  + ":DESC", JsonUtils.objectToJson(itemDesc));
			// 设置过期时间。提高缓存利用率
			jedisClient.expire(ITEM_INFO + ":" + itemId  + ":DESC",TIEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
