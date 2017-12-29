package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;
/**
 * 图片上传controller，使用fastDfs
 * @Author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@ResponseBody
	@RequestMapping("/pic/upload")
	public String picUpload(MultipartFile uploadFile) {
		try {
			//接受上传的文件
			String originalFilename = uploadFile.getOriginalFilename();
			//取出扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url = IMAGE_SERVER_URL + url;
			//响应上传图片的url
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}
}
