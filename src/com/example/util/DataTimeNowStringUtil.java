package com.example.util;

import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

public class DataTimeNowStringUtil {
	public static String getTimeString(){
		Date data=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		return df.format(data).toString();
	}
}
