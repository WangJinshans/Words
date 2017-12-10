package com.example.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TranslationAnalyzeUitl {
	private static Map<String,String> resultArray=new HashMap<String, String>();
	
	public static Map<String,String> init(String result){
		System.out.println(result);
		JSONObject json=JSONObject.fromObject(result);
		JSONArray array=json.getJSONArray("trans_result");
		
		for(int i=0;i<array.size();i++){
			JSONObject object=array.getJSONObject(i);
			resultArray.put("dst", object.getString("dst"));
		}
		return resultArray;
	}
}
