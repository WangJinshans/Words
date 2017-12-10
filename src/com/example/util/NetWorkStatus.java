package com.example.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;


public class NetWorkStatus {

	private static String results;
	
	/*
	 * ÍøÂç²âÊÔ
	 */
	public static String netWorkCheckOut(){
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("ping baidu.com");
			InputStream iStream = process.getInputStream();
			InputStreamReader iSReader = new InputStreamReader(iStream, "UTF-8");
			BufferedReader bReader = new BufferedReader(iSReader);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}
			iStream.close();
			iSReader.close();
			bReader.close();
			String result = new String(sb.toString().getBytes("UTF-8"));
			if (!StringUtils.isBlank(result)) {
				if (result.indexOf("TTL") > 0 || result.indexOf("ttl") > 0) {
					results = "ÍøÂçÕı³£";
				} else {
					results = "ÍøÂç¶Ï¿ª";
				}
			}
		} catch (Exception e) {
			results = "ÍøÂçÒì³£";
			e.printStackTrace();
		}
		return results;
	}
}
