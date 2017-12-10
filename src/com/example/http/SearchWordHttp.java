package com.example.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import com.example.constant.Constant;

import net.sf.json.JSONObject;


public class SearchWordHttp {
	public static String searchWordFromJinshan(String word)
	{
		JSONObject jsonObject = null;
		OutputStreamWriter out = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(Constant.jinShanAPI.URL+word+"&type=" + Constant.jinShanAPI.JSON
					+ "&key=" + Constant.jinShanAPI.KEY);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			out = new OutputStreamWriter(httpUrlConn.getOutputStream(), "UTF-8");
			// 把数据写入请求的Body
			// out.write("w=" + word + "&type=" + type+"&key="+key);
			// //参数形式跟在地址栏的一样
			out.flush();
			out.close();
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			System.out.println(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
}
