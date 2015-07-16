package com.xinxin.everyxday.base.jsonparser;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.xinxin.everyxday.base.netcode.ResultCodeUtil;
import com.xinxin.everyxday.bean.base.CommonResponseErrorBean;
import com.xinxin.everyxday.bean.base.CommonResponseHeader;
import com.xinxin.everyxday.global.Globe;
import com.xinxin.everyxday.util.TimeUtil;

public class InterfaceResultParser extends InterfaceResultParserBase {
	
	public static CommonResponseHeader processResult(CommonResponseHeader responseHeader){
		if(responseHeader != null){
			responseHeader.setResultCode(ResultCodeUtil.getInstance().getCommonResult(responseHeader));
		}
		return responseHeader;
	}
	
	public static Gson generateDateFormatGson(){
		
		return new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
				
				String date = element.getAsString();
				TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
				
				try {
					return formatter.parse(date);
				} catch (ParseException e) {
					System.out.println("Failed to parse Date due to:"+e);
					return null;
				}
			}
		}).create();
	}
	
	public static CommonResponseHeader parserResponseHeader(Header[] headers){
		
		CommonResponseHeader responseHeader = new CommonResponseHeader();;
		
		if(headers != null && headers.length > 0){
			
			for(Header header : headers){
				
				String headerName = header.getName();
				String headerValue = header.getValue();
				
				if(CommonResponseHeader.HEADER_RESULT_CODE.equals(headerName)){ 
					responseHeader.setResultCode(headerValue);
				}else if(CommonResponseHeader.HEADER_DATE.equals(headerName)){ 
					responseHeader.setDate(headerValue);
					
					long curLocalTime = System.currentTimeMillis();
					long serverResponseTime = TimeUtil.convertGMTToLong(headerValue);
					
					if(serverResponseTime != 0){//更新本地时间戳
						Globe.REQUEST_TIMESTAMP = curLocalTime - serverResponseTime;
					}
					
				}else if(CommonResponseHeader.HEADER_LINK.equals(headerName)){ 
					int urlStartIndex = headerValue.indexOf("<");
					int urlEndIndex = headerValue.indexOf(">");
					headerValue = headerValue.substring(urlStartIndex+1, urlEndIndex);
					System.out.println("headerValue ===== " + headerValue);
					responseHeader.setLink(headerValue);
				}
			}
		}
		
		return responseHeader;
	}
	
	public static CommonResponseErrorBean generateErrorBean(){
		
		CommonResponseErrorBean errorBean = new CommonResponseErrorBean();
		errorBean.setCode(ResultCodeUtil.BAD_REQUEST_UNKNOWN);
		errorBean.setType(ResultCodeUtil.BAD_REQUEST_TYPE_UNKNOWN);
		errorBean.setResource(ResultCodeUtil.BAD_REQUEST_RESOURCE_UNKNOWN);
		return errorBean;
	}
	
}
