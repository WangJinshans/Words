package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlLiteDB {
	private static Connection connection=null;
	private static Statement stmt=null;
	
	static{
		
	}
	
	public static void getConnection(){
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:word.db");
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
		}catch (Exception e){
			
		}
	}
	
	/*
	 * 获取查询内容
	 * 指定特定的返回值
	 * */
	public static void getSearchResult(String sql){
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//返回类型
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
