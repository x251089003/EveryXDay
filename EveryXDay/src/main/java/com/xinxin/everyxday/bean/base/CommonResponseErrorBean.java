package com.xinxin.everyxday.bean.base;

/**
 * 1.服务器返回code!=200,根据服务器返回信息自动填充CommonResponseErrorBean
 * 2.没有连上服务器也没有code返回,手动填充信息返回
 */
public class CommonResponseErrorBean {

	private String code;
	private String type;
	private String resource;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
