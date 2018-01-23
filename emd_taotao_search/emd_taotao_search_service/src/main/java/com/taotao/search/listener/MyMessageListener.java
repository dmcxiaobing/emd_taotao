package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 这是一个测试queue的接收消息的listener。。整合了spring。发布者请参考emd_taotao_manager_service
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			// 接受到消息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
