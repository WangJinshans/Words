package com.example.thread;

import com.example.modle.Word;
import com.example.service.SpeakService;

public class SpeakThread extends Thread{

	private Word word;
	private SpeakService service;
	@Override
	public void run() {
		service.speak(word.getWord());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public SpeakThread(Word word) {
		service=new SpeakService();
		this.word=word;
	}
}
