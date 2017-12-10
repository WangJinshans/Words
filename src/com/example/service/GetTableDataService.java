package com.example.service;

import java.util.List;

import com.example.modle.Word;

public class GetTableDataService {
	/*
	 * 获取数据实现分页   totalList 一启动进入背单词界面 就开始加载   加载期间存在一个动画 进度条  从服务器获取所有单词
	 * 
	 * 每页20个单词，点击页码    保存数据操作并清空table 然后setInput （页码x20，+20）
	 * 
	 * 数据的操作应该可以保存到类似于sqlite的一种数据库（本地数据库）
	 * 
	 * 静态服务  
	 * 
	 * 传入参数       页码
	 * 
	 * 传出参数       大小为20的List<Word>的数组
	 */
	
	
	private static List<Word> totalList;//总的单词类列表
	private static List<Word> returnlist;//返回特定页的列表
	
	
	/*
	 * 返回特定的页的单词列表
	 */
	public List<Word> getWordList(int pageNumber){
		int startNumber=pageNumber;
		int endNumber=pageNumber+20;
		returnlist=totalList.subList(startNumber, endNumber);
		return returnlist;
	}
	
	
	/*
	 * 从服务器获取所有单词列表
	 * 
	 */
	
	public boolean getAllWordList(){
		
		return false;
	}
}
