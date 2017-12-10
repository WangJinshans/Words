package com.example.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.example.modle.User;

public class DBManager {
	
	private String driverName;
	private String url;
	private String userName;
	private String userPassWord;
	
	public DBManager() {
		InputStream in=getClass().getClassLoader().getResourceAsStream("com/example/db/sql.properties");
		Properties properties=new Properties();
		try {
			properties.load(in);
			this.driverName = properties.getProperty("driver");
			this.url = properties.getProperty("url");
			this.userName = properties.getProperty("userName");
			this.userPassWord = properties.getProperty("userPassWord");
			System.out.println(driverName+"     "+url+"    "+userName+"     "+userPassWord);
			Driver driver=(Driver) Class.forName(driverName).newInstance();
			Connection connection=DriverManager.getConnection(url,userName,userPassWord);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public User login(String userName,String userPassWord)
	{
		User user=new User();
		
		return user;
	}
}
