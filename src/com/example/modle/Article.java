package com.example.modle;

public class Article {
	
	/*
	 * �Ķ���
	 * content ��������
	 * pageNumber ���µ�ҳ��
	 * tiltle ���µı���
	 * */
	private String pageNumber;
	private String content;
	private String tiltle;
	public String getTiltle() {
		return tiltle;
	}
	public void setTiltle(String tiltle) {
		this.tiltle = tiltle;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
