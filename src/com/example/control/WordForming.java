package com.example.control;

import java.util.ArrayList;
import java.util.List;

import com.example.modle.Word;

public class WordForming {
	public List<Word> createData(){
		List<Word> wordList=new ArrayList<>();
		Word word;
		for(int i=0;i<10;i++){
			word=new Word();
			word.setWord("File");
			wordList.add(word);
		}
		return wordList;
	}
}
