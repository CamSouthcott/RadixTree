package com.camsouthcott.radixtree;

import java.util.LinkedList;
import java.util.List;

public class RadixTree {

	private RadixTreeNode head;
	
	public RadixTree(){
		head = new RadixTreeNode(null,false);
	}
	
	public boolean find(String word){
		
		if(word != null){
			word = formatWord(word.trim());

			if(word != null && !word.isEmpty()){
				return head.find(word);
			}
		}
		
		return false;
	}
	
	public List<String> toList(){
		List<String> list = new LinkedList<String>();
		head.createList(list,"");
		return list;
	}
	
	public void addWord(String word){
		
		if(word != null){
			word = formatWord(word.trim());

			if(word != null && !word.isEmpty()){
				head.addWord(word);
			}
		}
	}
	
	private static String formatWord(String word){
		
		try{
			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < word.length(); i++){
				sb.append(formatChar(word.charAt(i)));
			}

			return sb.toString();
		}catch(IllegalArgumentException e){
			return null;
		}
	}
	
    private static char formatChar(char c){

        //convert numbers to their equivalent letters
        switch(c){
            case '0':
                c = 'o';
                break;
            case '1':
                c = 'i';
                break;
            case '2':
                c = 'z';
                break;
            case '3':
                c = 'e';
                break;
            case '4':
                c = 'a';
                break;
            case '5':
                c = 's';
                break;
            case '6':
                c = 'g';
                break;
            case '7':
                c = 't';
                break;
            case '8':
                c = 'b';
                break;
            case '9':
                c = 'q';
                break;
            default:
                break;
        }

        if(c >= 'A' && c <= 'Z'){
            return (char) (c + 'a'  - 'A');
        }
        
        if(c >= 'a' && c <= 'z'){
        	return c;
        }

        throw new IllegalArgumentException();
    }
}
