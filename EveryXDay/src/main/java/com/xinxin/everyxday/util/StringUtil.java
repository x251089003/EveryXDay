package com.xinxin.everyxday.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidEmailFormat(String emailStr) {
		
		if (StringUtil.isEmpty(emailStr)){
			return false;
		}
        return emailStr.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    }
	
	public static boolean isValidPhoneFormat(String phone) {
		
		if (StringUtil.isEmpty(phone)){
			return false;
		}
        return phone.matches("^1\\d{10}$");
    }
	
	public static String interceptPrice(String price){
		String resultPrice = "";
		
		try {//服务器返回价格数据格式错误兼容
			if(price.contains("-")){
				String priceArray[] = price.split("-");
				if(priceArray != null){
					resultPrice = interceptSinglePrice(priceArray[0]) + "-" + interceptSinglePrice(priceArray[1]);
				}else{
					resultPrice = price;
				}
			}else{
				resultPrice = interceptSinglePrice(price);
			}
		} catch (Exception e) {
		}
		return resultPrice;
	}
	
	public static String interceptSinglePrice(String price){
		String resultPrice = "";
		if(price.split("\\.", 2) != null){
			if(price.split("\\.", 2).length>1 && price.split("\\.", 2)[1].equals("0")){
				resultPrice = price.replace(".0", "");
			}
			else if(price.split("\\.", 2).length>1 && price.split("\\.", 2)[1].equals("00")){
				resultPrice = price.replace(".00", "");
			}else{
				if((price.substring(price.length()-1, price.length())).equals("0")){
					resultPrice = price.substring(0, price.length()-1);
				}else{
					resultPrice = price;
				}
			}
		}else{
			resultPrice = price;
		}
		return resultPrice;
	}
	
}
