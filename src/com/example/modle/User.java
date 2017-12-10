package com.example.modle;

public class User {
	private String userName;
	private String userPassWord;
	
	public User() {

	}
	public User(String userName, String userPassWord) {
		this.userName = userName;
		this.userPassWord = userPassWord;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", userPassWord=" + userPassWord + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassWord() {
		return userPassWord;
	}
	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}
	
}
