package test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.db.DBManager;

public class DB {
	public static void main(String[] args) {
//		DBManager manager=new DBManager();
		
		
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Words;";
		String userName = "sa";
		String userPassWord = "wangjinshan123..";
		System.out.println(driverName+"     "+url+"    "+userName+"     "+userPassWord);
		try {
			Driver driver=(Driver) Class.forName(driverName).newInstance();
			Connection connection=DriverManager.getConnection(url,userName,userPassWord);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
