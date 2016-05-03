package com.camsouthcott.radixtree;

import java.util.List;

class RadixTreeHead {

	private RadixTreeNode[] nodes;
	
	protected RadixTreeHead(){
		nodes = new RadixTreeNode[26];
	}
	
	protected void addWord(String word){
		
		int index = RadixTreeNode.getIndex(word.charAt(0));
		
		RadixTreeNode node = nodes[index];
		
		if(node != null){
			node.addWord(word);
		}else{
			nodes[index] = new RadixTreeNode(null,word);
		}
	}
	
	protected String find(String word){
		
		if(word != null && !word.isEmpty()){
			word = word.trim();
			int index = RadixTreeNode.getIndex(word.charAt(0));
			RadixTreeNode node = nodes[index];
			
			if(node != null){
				return node.find(word);
			}
		}
		
		return null;
	}
	
	protected List<String> createList(List<String> list){
		
		for(RadixTreeNode node: nodes){
			if(node != null){
				node.createList(list);
			}
		}
		
		return list;
	}
}
