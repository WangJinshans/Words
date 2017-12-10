package com.example.modle;

public class Article {
	
	/*
	 * 阅读类
	 * content 文章内容
	 * pageNumber 文章的页码
	 * tiltle 文章的标题
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
