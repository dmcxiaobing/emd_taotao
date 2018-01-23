package com.taotao.item.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 添加新商品后，直接根据freemarker模板。生成静态html。
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class ItemAddMesssageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

}
