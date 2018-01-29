package com.taotao.sso.service;
/**
 * 用户service接口
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

	TaotaoResult checkData(String data,Integer type);
	
	TaotaoResult register(TbUser user);
	
	TaotaoResult login(String username,String password);

	TaotaoResult getUserByToken(String token);

	TaotaoResult deleteToken(String token);
}
