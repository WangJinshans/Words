package com.example.http;

import com.example.constant.Constant;
import com.example.util.UnicodeUtil;

public class Translation {

	/*
	 * Ĭ�ϵ�ת����ʽ ת��Ϊ����
	 * 
	 * */
	public static String sendToTranslate(String query){
		TransApi api = new TransApi(Constant.BaiDuTranslation.APP_ID, Constant.BaiDuTranslation.SECURITY_KEY);
		String result = UnicodeUtil.unicodeToString(api.getTransResult(query, "auto", "zh"));
		return result;
	}
	
	/*
	 * 
	 * ָ����ת����ʽ
	 * 
	 * */
	public static String sendToTranslate(String query,String from){
		String result="";
		TransApi api = new TransApi(Constant.BaiDuTranslation.APP_ID, Constant.BaiDuTranslation.SECURITY_KEY);
		if(from.equals("en")){
			result = UnicodeUtil.unicodeToString(api.getTransResult(query, "en", "zh"));
		}
		if(from.equals("zh")){
			result = UnicodeUtil.unicodeToString(api.getTransResult(query, "zh", "en"));
		}
		return result;
	}
}
