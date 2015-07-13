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
package com.xinxin.everyxday.bean.base;

import java.util.List;

public class CommonResponseBody<T> {

	private List<T> list;

	private T responseObject;

	private CommonResponseHeader responseHeader;

	private CommonResponseErrorBean responseErrorBean;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public T getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(T responseObject) {
		this.responseObject = responseObject;
	}

	public CommonResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(CommonResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public CommonResponseErrorBean getResponseErrorBean() {
		return responseErrorBean;
	}

	public void setResponseErrorBean(CommonResponseErrorBean responseErrorBean) {
		this.responseErrorBean = responseErrorBean;
	}

}
