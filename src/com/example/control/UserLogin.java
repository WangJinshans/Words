package com.example.control;

import com.example.db.DBManager;
import com.example.modle.User;

public class UserLogin {
	private DBManager dbManager;
	
	public UserLogin(DBManager dbManager){
		if(dbManager==null){
			dbManager=new DBManager();
		}
	}
	public boolean login(User user){
		boolean loginOK=false;
		//loginOK=dbManager.
		return loginOK;
	}
	
}
