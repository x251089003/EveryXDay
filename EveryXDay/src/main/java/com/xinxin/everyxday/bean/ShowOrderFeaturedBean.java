package com.xinxin.everyxday.bean;

import java.util.Date;

public class ShowOrderFeaturedBean {

	private int id;
	private String avatar;
	private String title;
	private String cover;
	private Date createTime;
	private String detail;
	private String detailNew;
	private String buyurl;
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDetailNew() {
		return detailNew;
	}

	public void setDetailNew(String detailNew) {
		this.detailNew = detailNew;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getBuyurl() {
		return buyurl;
	}

	public void setBuyurl(String buyurl) {
		this.buyurl = buyurl;
	}

}
