package com.camsouthcott.radixtree;

import java.util.LinkedList;
import java.util.List;

public class RadixTree {

	private RadixTreeHead head;
	
	public RadixTree(){
		head = new RadixTreeHead();
	}
	
	public String find(String word){
		return head.find(word);
	}
	
	public List<String> toList(){
		return head.createList(new LinkedList<String>());
	}
	
	public void addWord(String word){
		
		if(word != null){
			word = word.trim();

			if(!word.isEmpty()){
				head.addWord(word);
			}
		}
	}
}
