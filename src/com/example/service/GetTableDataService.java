package com.example.service;

import java.util.List;

import com.example.modle.Word;

public class GetTableDataService {
	/*
	 * ��ȡ����ʵ�ַ�ҳ   totalList һ�������뱳���ʽ��� �Ϳ�ʼ����   �����ڼ����һ������ ������  �ӷ�������ȡ���е���
	 * 
	 * ÿҳ20�����ʣ����ҳ��    �������ݲ��������table Ȼ��setInput ��ҳ��x20��+20��
	 * 
	 * ���ݵĲ���Ӧ�ÿ��Ա��浽������sqlite��һ�����ݿ⣨�������ݿ⣩
	 * 
	 * ��̬����  
	 * 
	 * �������       ҳ��
	 * 
	 * ��������       ��СΪ20��List<Word>������
	 */
	
	
	private static List<Word> totalList;//�ܵĵ������б�
	private static List<Word> returnlist;//�����ض�ҳ���б�
	
	
	/*
	 * �����ض���ҳ�ĵ����б�
	 */
	public List<Word> getWordList(int pageNumber){
		int startNumber=pageNumber;
		int endNumber=pageNumber+20;
		returnlist=totalList.subList(startNumber, endNumber);
		return returnlist;
	}
	
	
	/*
	 * �ӷ�������ȡ���е����б�
	 * 
	 */
	
	public boolean getAllWordList(){
		
		return false;
	}
}
