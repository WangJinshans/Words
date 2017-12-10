package com.example.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.example.constant.Constant;

public class ImageRegistryUtil {
	private static ImageRegistry imageRegistry = null;
	private static Image image = null;
	
	private static URL newURL(String url){
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageRegistry getRegistry(){
		if(imageRegistry == null){
			imageRegistry = new ImageRegistry();
			
			imageRegistry.put("user_24px", ImageDescriptor.createFromURL(newURL("file:icons/user_24px.png")));
			imageRegistry.put("configure_24px", ImageDescriptor.createFromURL(newURL("file:icons/configure_24px.png")));
			imageRegistry.put("dictionary", ImageDescriptor.createFromURL(newURL("file:icons/dictionary_24.png")));
			imageRegistry.put("translation", ImageDescriptor.createFromURL(newURL("file:icons/translation.png")));
			imageRegistry.put("note", ImageDescriptor.createFromURL(newURL("file:icons/note_24.png")));
			imageRegistry.put("word", ImageDescriptor.createFromURL(newURL("file:icons/word.png")));
			imageRegistry.put("reading", ImageDescriptor.createFromURL(newURL("file:icons/read.png")));
			imageRegistry.put("listening", ImageDescriptor.createFromURL(newURL("file:icons/listen.png")));
			imageRegistry.put("bookstory", ImageDescriptor.createFromURL(newURL("file:icons/bookstory.png")));
		}
		
		return imageRegistry;
	}
	
	
	public static Image getRegistry(String picTureName){
		image=new Image(null, Constant.Resourses.ICON_PATH+picTureName+Constant.Resourses.PNG);
		return image;
	}
	
}
