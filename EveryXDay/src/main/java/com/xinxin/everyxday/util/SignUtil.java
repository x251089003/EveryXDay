package com.xinxin.everyxday.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 签名算法
 * 需要用到Base64类，推荐用apache的 commons-codec-1.6.jar，这个是base64标准实现.(没有的话可以问我要)
 * 计算结果已经测试过，没问题~ ：
 * @see 
 * 
 * 
		  	public static void main(String[] argv){
				SignUtil s = new SignUtil();
				try {
					System.out.println(s.genAuthorizationValue(SignUtil.stringToSign));
				} catch (Exception e) {
					// TODO:处理异常
					e.printStackTrace();
				}
			}
 * 
 * 			
 */
public class SignUtil {
	/**
	 * 算法枚举类
	 * 迁移到项目中时，可以单独作为一个类，而不是作为内部类。
	 * 作为内部类也无所谓，目前没有hmac的其他算法
	 * @author hanchao
	 *
	 */
	public enum SigningAlgorithm {
	    HmacSHA1,
	    HmacSHA256;
	}

	/*
	 * StringToSign = HTTP-Verb + "\n" +
               Content-Type + "\n" +
               Expire + "\n" +
               ResourceURI;             //资源URI,域名后面所有的部分.
	 */
//	public static String stringToSign = "GET\n"
//										+ "application/json\n"
//										+ "Sat, 20 Nov 2286 17:46:39 GMT\n"
//										+ "/kills/11923?status=1&page=12";
	
	/**
	 * 生成请求头中Authorization的ssig部分。（Authorization: accessKey:ssig）
	 * @param stringToSign	待签名的字符串，拼装结构见上面注释
	 * @return				截取好的ssig
	 * @throws Exception	根据情况改成自定义异常
	 */
	public String genAuthorizationValue(String stringToSign, String secretKey) throws Exception{
		try {
			String signature = signAndBase64Encode(stringToSign.getBytes("UTF-8"), secretKey, SigningAlgorithm.HmacSHA1);
			
	        if(signature.length()>=15)
	        	signature = signature.substring(5,15);
		        
		    return signature;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 对stringToSign进行加密，返回ssig字符串
	 * @param data				stringToSign.getBytes("UTF-8")
	 * @param key				secretKey
	 * @param algorithm			sha1算法名称
	 * @return					Base64( HMAC-SHA1( 'SecretKey', UTF-8-Encoding-Of( StringToSign ) ) )的结果
	 * @throws Exception		根据情况改成自定义异常
	 */
	protected String signAndBase64Encode(byte[] data, String key, SigningAlgorithm algorithm) throws Exception {
        try {
            byte[] signature = sign(data, key.getBytes("UTF-8"), algorithm);
            return new String(Base64.encodeBase64(signature));
        } catch (Exception e) {
            throw new Exception("Unable to calculate a request signature: " + e.getMessage(), e);
        }
    }
	
	/**
	 * hmac-sha1算法
	 * @param data
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception		根据情况改成自定义异常
	 */
	protected byte[] sign(byte[] data, byte[] key, SigningAlgorithm algorithm) throws Exception {
        try {
            Mac mac = Mac.getInstance(algorithm.toString());
            mac.init(new SecretKeySpec(key, algorithm.toString()));
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new Exception("Unable to calculate a request signature: " + e.getMessage(), e);
        }
    }
	
}
