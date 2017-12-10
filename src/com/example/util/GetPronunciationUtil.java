package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.constant.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class GetPronunciationUtil {

	public static void  downLoadFromUrl(String urlStr,String fileName) throws IOException{  
	    
		try{
			URL url = new URL(urlStr);    
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
		    //���ó�ʱ��Ϊ3��  
		    conn.setConnectTimeout(3*1000);  
		    //��ֹ���γ���ץȡ������403����  
		    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  

		    //�õ�������  
		    InputStream inputStream = conn.getInputStream();    
		    //��ȡ�Լ�����  
		    byte[] getData = readInputStream(inputStream);      

		    //�ļ�����λ��  
		    File saveDir = new File(Constant.Voice.VOICE_PATH);  
		    if(!saveDir.exists()){  
		        saveDir.mkdir();  
		    }  
		    File file = new File(saveDir+File.separator+fileName);      
		    FileOutputStream fos = new FileOutputStream(file);       
		    fos.write(getData);   
		    if(fos!=null){  
		        fos.close();    
		    }  
		    if(inputStream!=null){  
		        inputStream.close();  
		    }  


		    System.out.println("info:"+url+" download success");   
		}catch(Exception e){
			return;
		}

	}  



	/** 
	 * ���������л�ȡ�ֽ����� 
	 * @param inputStream 
	 * @return 
	 * @throws IOException 
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
	    byte[] buffer = new byte[1024];    
	    int len = 0;    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	    while((len = inputStream.read(buffer)) != -1) {    
	        bos.write(buffer, 0, len);    
	    }    
	    bos.close();    
	    return bos.toByteArray();    
	}    
}
