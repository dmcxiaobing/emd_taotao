package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

/**
 * 只是为了处理图片地址的，因为多张图片是以逗号隔开的字符串。所以这个javabean直接处理了。
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class Item extends TbItem{
	/**
	 * 初始化。商品数据。
	 */
	public Item(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}

	/**
	 * 因为item.jsp是直接调用getImages()。所以方法名一致
	 * @return
	 */
	public String[] getImages() {
		if (this.getImage()!=null&&!this.getImage().equals("")) {
			String imageString = this.getImage();
			// 然后以逗号分开。
			String[] imageStrings = imageString.split(",");
			return imageStrings;
 		}
		return null;
		
	}
	
	
}
