package com.taotao.sso.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;

/**
 * 用户的业务实现
 * 
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;

	/**
	 * 检测用户名是否可用
	 */
	@Override
	public TaotaoResult checkData(String data, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 根据type判断类型。1、2、3分别代表username、phone、email
		// 设置查询条件
		if (type == 1) {
			criteria.andUsernameEqualTo(data);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(data);
		} else if (type == 3) {
			criteria.andEmailEqualTo(data);
		} else {
			return TaotaoResult.build(400, "参数中数据非法");
		}
		// 执行查询
		List<TbUser> userList = tbUserMapper.selectByExample(example);
		if (userList != null && userList.size() > 0) {
			// 查询到了，则说明不可用
			return TaotaoResult.ok(false);
		}
		// 查询不到，则说明可用
		return TaotaoResult.ok(true);
	}

	/**
	 * 注册用户。。
	 * 
	 * username //用户名 password //密码 phone //手机号 email //邮箱
	 */
	@Override
	public TaotaoResult register(TbUser user) {
		// 判断用户名是否为空
		if (StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(400, "用户名不能为空");
		}
		// 判断用户名是否重复，可用
		TaotaoResult result = checkData(user.getUsername(), 1);
		// 如果为true，则说明可以使用。否则不可用。
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "用户名已被占用");
		}

		// 判断手机号是否重复，可用
		if (StringUtils.isNotBlank(user.getPhone())) {
			result = checkData(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "手机号已被占用");
			}
		}
		// 判断email是否重复，可用
		if (StringUtils.isNotBlank(user.getEmail())) {
			result = checkData(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "Email已被占用");
			}
		}
		/**
		 * 判断密码是否为空
		 */
		if (StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "密码不能为空");
		}
		// 补全pojo的其他属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 密码进行MD5加密。这里使用spring的核心包中的工具类
		String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		// 补全到用户信息中
		user.setPassword(md5Password);
		// 插入到数据库。然后返回注册成功
		tbUserMapper.insert(user);
		return TaotaoResult.ok();
	}

	/**
	 * 登陆用户
	 */
	@Override
	public TaotaoResult login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
