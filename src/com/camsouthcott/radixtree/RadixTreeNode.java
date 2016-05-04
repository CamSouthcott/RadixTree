package com.camsouthcott.radixtree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class RadixTreeNode {
	
	private RadixTreeNode parent;
	private Map<String,RadixTreeNode> nodes;
	private boolean isWord = false;
	
	protected RadixTreeNode(RadixTreeNode parent, boolean isWord){
		this.parent = parent;
		this.isWord = isWord;
		nodes = newNodeMap();
	}
	
	private RadixTreeNode(RadixTreeNode parent, Map<String,RadixTreeNode> nodes, boolean isWord){
		this.parent = parent;
		this.nodes = nodes;
		this.isWord = isWord;
	}
	
	private void setParent(RadixTreeNode parent){
		this.parent = parent;
	}
	
	private Map<String, RadixTreeNode> newNodeMap(){
		return new TreeMap<String,RadixTreeNode>();
	}
	
	protected void addWord(String newEntry){
		
		if(newEntry.length() == 0){
			//newEntry shares a path with this node, set isWord to true
			isWord = true;
			return;
		}
		
		String previousNodeName = findNodeName(newEntry);
		
		if(previousNodeName == null){
			//No node that shares a prefix with newEntry, create new node
			nodes.put(newEntry, new RadixTreeNode(this,true));
			return;
		}
		
		String prefix = getPrefix(newEntry, previousNodeName);
		
		if(prefix.length() == newEntry.length() || prefix.length() == previousNodeName.length()){
			if(newEntry.length() < previousNodeName.length()){
				
				//newEntry should be a parent of the old child
				RadixTreeNode previousNode = nodes.get(previousNodeName);
				nodes.remove(previousNodeName);
				
				Map<String, RadixTreeNode> newEntryNodes = newNodeMap();
				newEntryNodes.put(previousNodeName.substring(newEntry.length()), previousNode);
				
				RadixTreeNode newEntryNode = new RadixTreeNode(this,newEntryNodes,true);
				previousNode.setParent(newEntryNode);
				
				nodes.put(newEntry,newEntryNode);
				
			}else{
				
				// newEntry either is oldNode or is a child of oldNode, send it to oldNode to handle
				RadixTreeNode previousNode = nodes.get(previousNodeName);
				previousNode.addWord(newEntry.substring(previousNodeName.length()));
			}
		}else{
			
			//newEntry and oldNodeName share a substring, make a new parent node for them
			RadixTreeNode previousNode = nodes.get(previousNodeName);
			nodes.remove(previousNodeName);
			RadixTreeNode newEntryNode = new RadixTreeNode(null,true);
					
			Map<String, RadixTreeNode> prefixNodes = newNodeMap();
			prefixNodes.put(previousNodeName.substring(prefix.length()), previousNode);
			prefixNodes.put(newEntry.substring(prefix.length()), newEntryNode);
			
			RadixTreeNode prefixNode = new RadixTreeNode(this,prefixNodes,false);
			previousNode.setParent(prefixNode);
			newEntryNode.setParent(prefixNode);
			nodes.put(prefix, prefixNode);
		}
	}
	
	protected boolean find(String letters){
		
		if(letters.length() == 0){
			return isWord;
		}else{
			
			String nodeName = findNodeName(letters);
			
			if(nodeName != null){
				String prefix = getPrefix(letters,nodeName);
				
				if(prefix != null && nodeName.length() == prefix.length() && letters.length() >= nodeName.length()){
					return nodes.get(nodeName).find(letters.substring(nodeName.length()));
				}
			}
				
		}
		
		return false;
	}
	
	protected void createList(List<String> list, String word){
		
		if(isWord){
			list.add(word);
		}
		
		for(String key: nodes.keySet()){
			nodes.get(key).createList(list, word + key);
		}
	}
	
	protected String findNodeName(String letters){
		
		for(String key: nodes.keySet()){
			if(letters.charAt(0) == key.charAt(0)){
				return key;
			}
		}
		
		return null;
	}
	
	protected String getPrefix(String input1, String input2){
		
		int highestMatchIndex = 0;
		
		while(highestMatchIndex < input1.length() && highestMatchIndex < input2.length()){

			if(input1.charAt(highestMatchIndex) != input2.charAt(highestMatchIndex)){
				break;
			}
			highestMatchIndex++;
		}
		
		if(highestMatchIndex > 0){
			return input1.substring(0,highestMatchIndex);
		}
		
		return null;
	}
}
