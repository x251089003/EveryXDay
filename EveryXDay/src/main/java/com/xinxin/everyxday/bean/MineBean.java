/******************************************************************************
 * PROPRIETARY/CONFIDENTIAL 
 * Copyright (c) 2015 XianTao Technology Co.,Ltd
 * 
 * All rights reserved. This medium contains confidential and proprietary 
 * source code and other information which is the exclusive property of 
 * XianTao Technology Co.,Ltd. None of these materials may be used, 
 * disclosed, transcribed, stored in a retrieval system, translated into any 
 * other language or other computer language, or transmitted in any 
 * form or by any means (electronic, mechanical, photocopied, recorded 
 * or otherwise) without the prior written permission of XianTao Technology 
 * Co.,Ltd. 
 *******************************************************************************/
package com.xinxin.everyxday.bean;

public class MineBean {

	// {
	// "nickname" : "欣欣", //用户昵称
	// "avatar" : "http://www.sdfsdf.com/png.png", //用户头像
	// "level" : "黑铁会员", //用户等级，预留字段，暂时不用
	// "balance" : 13.40, //账户余额
	// "totalCashBack" : 324.30 //晒单返现，等待返现的总金额
	// "phone": "186012812323",
	// }

	private String nickname;
	private String avatar;
	private String level;
	private float balance;
	private float totalCashBack;
	private String phone;
	private String inviteUrl;// 好友邀请地址

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getTotalCashBack() {
		return totalCashBack;
	}

	public void setTotalCashBack(float totalCashBack) {
		this.totalCashBack = totalCashBack;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInviteUrl() {
		return inviteUrl;
	}

	public void setInviteUrl(String inviteUrl) {
		this.inviteUrl = inviteUrl;
	}

}
