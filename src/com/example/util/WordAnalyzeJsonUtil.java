package com.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 解析从金山词霸获取的单词的每个Json的数据
 * 
 */
public class WordAnalyzeJsonUtil {
	public static Map<String,String> wordArray=new HashMap<String, String>();
	public static int ciXing=0;  //根据此属性 在面板中动态创建显示窗体
	public static Map<String,String> ciXingArray=new HashMap<String, String>();
	/*
	 * 获取所有的键值对 并保存到 wordArray中
	 */
	public static WordAnalyzeJsonUtil getElements(String jsonString){
		WordAnalyzeJsonUtil analyzeJsonUtil=new WordAnalyzeJsonUtil();
		JSONObject json=JSONObject.fromObject(jsonString);
		try{
			wordArray.put("word_name", json.getString("word_name"));
			wordArray.put("is_CRI", json.getString("is_CRI"));
			JSONObject jsons=JSONObject.fromObject(json.getString("exchange"));
			wordArray.put("word_pl", jsons.getString("word_pl"));
			wordArray.put("word_third", jsons.getString("word_third"));
			wordArray.put("word_past", jsons.getString("word_past"));
			wordArray.put("word_done", jsons.getString("word_done"));
			wordArray.put("word_ing", jsons.getString("word_ing"));
			wordArray.put("word_er", jsons.getString("word_er"));
			wordArray.put("word_est", jsons.getString("word_est"));
			
			JSONArray array=json.getJSONArray("symbols");
			for(int i=0;i<array.size();i++){
				JSONObject object=array.getJSONObject(i);
				wordArray.put("ph_en", object.getString("ph_en"));
				wordArray.put("ph_am", object.getString("ph_am"));
				wordArray.put("ph_other", object.getString("ph_other"));
				wordArray.put("ph_en_mp3", object.getString("ph_en_mp3"));
				wordArray.put("ph_am_mp3", object.getString("ph_am_mp3"));
				wordArray.put("ph_tts_mp3", object.getString("ph_tts_mp3"));
				JSONArray subArray=object.getJSONArray("parts");
				for(int j=0;j<subArray.size();j++){
					JSONObject subObject=subArray.getJSONObject(j);
					//保存词性的个数
					ciXingArray.put(subObject.getString("part"), subObject.getString("means"));
					ciXing++;
				}
			}
			analyzeJsonUtil.wordArray=wordArray;
			analyzeJsonUtil.ciXingArray=ciXingArray;
			analyzeJsonUtil.ciXing=ciXing;
		}catch (Exception e){
			return null;
		}
		//遍历
//		java.util.Iterator<Entry<String, String>> iter = wordArray.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			System.out.println("Key:"+entry.getKey().toString()+"  ");
//			System.out.println("Value:"+entry.getValue().toString()+"\n");
//		}
		return analyzeJsonUtil;
	}
}
