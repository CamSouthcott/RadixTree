package com.camsouthcott.radixtree;

import java.util.List;

class RadixTreeNode {
	
	private static final int nodeArrayLength = 26;
	
	private RadixTreeNode parent;
	private RadixTreeNode[] nodes;
	private String value;
	private boolean isWord = false;
	
	protected RadixTreeNode(RadixTreeNode parent, String letters){
		this.parent = parent;
		value = letters;
		nodes = new RadixTreeNode[nodeArrayLength];
		isWord = true;
	}
	
	private RadixTreeNode(RadixTreeNode parent, RadixTreeNode[] nodes, String letters, boolean isWord){
		this.parent = parent;
		value = letters;
		this.nodes = nodes;
		this.isWord = isWord;
	}
	
	protected void setParent(RadixTreeNode parent){
		this.parent = parent;
	}
	
	protected void addWord(String letters){
		
		int highestMatchIndex = highestMatchingIndex(value, letters);
		int lettersLength = letters.length();
		int valueLength = value.length();
		
		if(highestMatchIndex == lettersLength || highestMatchIndex == valueLength){
			if(lettersLength < valueLength){
				
				//letters should be a parent of this node
				RadixTreeNode valueNode = new RadixTreeNode(this, nodes, value.substring(lettersLength),isWord);
				
				for(RadixTreeNode node: nodes){
					if(node != null){
						node.setParent(valueNode);
					}
				}
				
				nodes = new RadixTreeNode[nodeArrayLength];
				nodes[getIndex(value.charAt(lettersLength))] = valueNode;
				value = value.substring(0, lettersLength);
				isWord = true;
				
			}else if(lettersLength > valueLength){
				
				// letters should be a child of this node
				String extraLetters = letters.substring(valueLength);
				int index = getIndex(extraLetters.charAt(0));
				RadixTreeNode node = nodes[index];
				
				if(node != null){
					node.addWord(extraLetters);
				}else{
					nodes[index] = new RadixTreeNode(this,extraLetters); 
				}
			}else{
				isWord = true;
			}
		}else{
			
			//a new parent node is needed since letters and value only share a substring
			//convert this node to parent, add child nodes for letters and value
			RadixTreeNode valueNode = new RadixTreeNode(this, nodes, value.substring(highestMatchIndex),isWord);
			
			for(RadixTreeNode node: nodes){
				if(node != null){
					node.setParent(valueNode);
				}
			}
			
			nodes = new RadixTreeNode[nodeArrayLength];
			nodes[getIndex(value.charAt(highestMatchIndex))] = valueNode;
			value = value.substring(0, highestMatchIndex);
			isWord = false;
			
			RadixTreeNode lettersNode = new RadixTreeNode(this, letters.substring(highestMatchIndex));
			nodes[getIndex(letters.charAt(highestMatchIndex))] = lettersNode;
			
		}
	}
	
	protected String getWord(){
		StringBuilder sb = new StringBuilder();
		sb.insert(0, value);
		
		if(parent != null){
			parent.getWord(sb);
		}
		
		return sb.toString();
	}
	
	protected void getWord(StringBuilder sb){
		
		sb.insert(0, value);
		
		if(parent != null){
			parent.getWord(sb);
		}
	}
	
	protected String find(String letters){
		
		if(letters.length() >= value.length()){
			
			int highestMatchIndex = highestMatchingIndex(value,letters);
			
			if(highestMatchIndex == value.length()){
				if(highestMatchIndex == letters.length()){
					return getWord();
				}else{
					
					String extraLetters = letters.substring(value.length());
					int index = getIndex(extraLetters.charAt(0));
					RadixTreeNode node = nodes[index];

					if(node != null){
						return node.find(extraLetters);
					}
				}
			}
		}
		
		return null;
	}
	
	protected int highestMatchingIndex(String input1, String input2){
		
		int highestMatchIndex = 0;
		
		while(highestMatchIndex < input1.length() && highestMatchIndex < input2.length()){
			if(getIndex(input1.charAt(highestMatchIndex)) != getIndex(input2.charAt(highestMatchIndex))){
				break;
			}
			highestMatchIndex++;
		}
		
		return highestMatchIndex;
	}
	
	protected void createList(List<String> list){
		
		if(isWord){
			list.add(getWord());
		}
		
		for(RadixTreeNode node: nodes){
			if(node != null){
				node.createList(list);
			}
		}
	}

    protected static int getIndex(char c){

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

        //All letters
        // Asci table: http://www.w3schools.com/charsets/ref_html_ascii.asp
        if(c >= 'a' && c <='z'){
            return c - 'a';
        }else if(c >= 'A' && c <= 'Z'){
            return c - 'A';
        }

        throw new IllegalArgumentException();
    }
}
