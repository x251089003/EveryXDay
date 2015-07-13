package com.xinxin.everyxday.base.loopj.jsoncache;

import com.xinxin.everyxday.bean.base.CommonResponseHeader;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 缓存策略类基类 负责生成文件地址，存储和获取缓存数据
 */

public class ResponseCachePolicyBase {
	
	protected String getDataCacheFilePath(String requestUrl, int page){
		return null;
	}
	
	protected boolean isNeedDataCache(String requestUrl, int page){
		return false;
	}
	
	protected String getResponseHeaderCacheFilePath(String requestUrl, int page){
		return null;
	}
	
	protected boolean isNeedResponseHeaderCache(String requestUrl, int page){
		return false;
	}
	
	public void cacheResponseData(String requestUrl, int page,
			byte[] responseBytes, CommonResponseHeader responseHeader) {

		if (isNeedDataCache(requestUrl, page)) {

			String filePath = getDataCacheFilePath(requestUrl, page);

			cacheDataToSdcard(filePath, responseBytes);
		}
		
		if(isNeedResponseHeaderCache(requestUrl, page)){
			
			String filePath = getResponseHeaderCacheFilePath(requestUrl, page);
			
			cacheResponseHeaderToSdcard(filePath, responseHeader);
		}
	}

	public String getResponseCacheData(String requestUrl, int page) {

		if (isNeedDataCache(requestUrl, page)) {

			String filePath = getDataCacheFilePath(requestUrl, page);

			if(isFileExist(filePath)){
				return getCacheDataFromSdcard(filePath);
			}
		}

		return null;
	}
	
	public CommonResponseHeader getResponseHeaderCache(String requestUrl, int page) {

		if (isNeedResponseHeaderCache(requestUrl, page)) {

			String filePath = getResponseHeaderCacheFilePath(requestUrl, page);
			
			if(isFileExist(filePath)){
				return getCacheResponseHeaderFromSdcard(filePath);
			}
		}
		return null;
	}
	
	private boolean isFileExist(String filePath){
		
		File f = new File(filePath);
		if(f != null && f.exists()){
			return true;
		}
		return false;
	}
	
	//缓存data数据至本地
	protected void cacheDataToSdcard(String filePath, byte[] cacheData) {
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			
			fos = new FileOutputStream(new File(filePath));
			bos = new BufferedOutputStream(fos);
			
			bos.write(cacheData);
//			System.out.println("\r\n write data cache : " + filePath);
//			System.out.println("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(fos != null){
					fos.close();
				}
				if(bos != null){
					bos.flush();
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//获取本地data缓存数据
	protected String getCacheDataFromSdcard(String filePath) {

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			
			fis = new FileInputStream(new File(filePath));
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

//			System.out.println("\r\nread data from cache ....");
//			System.out.println("\r\n");
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
				if(isr != null){
					isr.close();
				}
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	//缓存responseHeader数据至本地
	protected void cacheResponseHeaderToSdcard(String filePath, CommonResponseHeader responseHeader) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			
			fos = new FileOutputStream(new File(filePath));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(responseHeader);
//			System.out.println("\r\n write responseHeader cache : " + filePath);
//			System.out.println("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//获取本地responseHeader缓存数据
	protected CommonResponseHeader getCacheResponseHeaderFromSdcard(String filePath) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			
			fis = new FileInputStream(new File(filePath));
			ois = new ObjectInputStream(fis);
//			System.out.println("\r\nread responseHeader from cache ....");
//			System.out.println("\r\n");
			return (CommonResponseHeader)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null){
					ois.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
