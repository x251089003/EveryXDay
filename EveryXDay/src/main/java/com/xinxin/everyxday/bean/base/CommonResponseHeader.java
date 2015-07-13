package com.xinxin.everyxday.bean.base;

import java.io.Serializable;

/**
 * Server: nginx 
 * Date: Fri, 12 Oct 2012 23:33:14 GMT 
 * Content-Type: application/json; 
 * charset=utf-8 
 * Connection: keep-alive 
 * Result-Code: 200
 * X-Taoxiaoxian-Media-Type: taoxiaoxian.v1 
 * Content-Length: 888 
 * Link: <http://api.taoxiaoxian.com/guides?cid=1&page=1>; rel="next"
 */

public class CommonResponseHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String HEADER_DATE = "Date";
	public static final String HEADER_RESULT_CODE = "Result-Code";
	public static final String HEADER_LINK = "Link";

	private String Link;
	private String resultCode;
	private String Date;

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	@Override
	public String toString() {
		return "CommonResponseHeader [Link=" + Link + ", resultCode="
				+ resultCode + ", Date=" + Date + "]";
	}
	
}
