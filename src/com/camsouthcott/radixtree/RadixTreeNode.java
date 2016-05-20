package com.camsouthcott.radixtree;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RadixTreeNode {
	
	protected RadixTreeNode parent;
	protected Map<String,RadixTreeNode> nodes;
	protected boolean isWord = false;
	
	protected RadixTreeNode(RadixTreeNode parent, boolean isWord){
		this.parent = parent;
		this.isWord = isWord;
		nodes = newNodeMap();
	}
	
	protected RadixTreeNode(RadixTreeNode parent, Map<String,RadixTreeNode> nodes, boolean isWord){
		this.parent = parent;
		this.nodes = nodes;
		this.isWord = isWord;
	}
	
	//node creation functions so the class can be extended on
	protected RadixTreeNode newNode(RadixTreeNode parent, boolean isWord){
		return new RadixTreeNode(parent, isWord);
	}
	
	protected RadixTreeNode newNode(RadixTreeNode parent, Map<String,RadixTreeNode> nodes, boolean isWord){
		return new RadixTreeNode(parent, nodes, isWord);
	}
	
	private void setParent(RadixTreeNode parent){
		this.parent = parent;
	}
	
	private Map<String, RadixTreeNode> newNodeMap(){
		//Tree Map is required if alphabetical order is important
		return new TreeMap<String,RadixTreeNode>();
	}
	
	//recursive method for adding words to the tree
	protected void addWord(String newEntry){
		
		if(newEntry.length() == 0){
			//newEntry shares a path with this node, set isWord to true
			isWord = true;
			return;
		}
		
		String previousNodeName = findNodeNameByFirstLetter(newEntry);
		
		if(previousNodeName == null){
			//No node that shares a prefix with newEntry, create new node
			nodes.put(newEntry, newNode(this,true));
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
				
				RadixTreeNode newEntryNode = newNode(this,newEntryNodes,true);
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
			RadixTreeNode newEntryNode = newNode(null,true);
					
			Map<String, RadixTreeNode> prefixNodes = newNodeMap();
			prefixNodes.put(previousNodeName.substring(prefix.length()), previousNode);
			prefixNodes.put(newEntry.substring(prefix.length()), newEntryNode);
			
			RadixTreeNode prefixNode = newNode(this,prefixNodes,false);
			previousNode.setParent(prefixNode);
			newEntryNode.setParent(prefixNode);
			nodes.put(prefix, prefixNode);
		}
	}
	
	//recursive method for deleting words from the tree
	//returns whether the parent node should delete this node
	protected boolean delete(String letters){
		
		int nChild = nodes.size();
		
		if(letters.length() == 0){
			//delete is targeting this node
			if(nChild == 0){
				//node is childless, tell parent to remove this node
				return true;

			}else{
				//node has children, mark as not a word, but dont remove node from parent
				isWord = false;
				return false;
			}
		}else{
			// delete is possibly targeting a child node
			
			String key = findNodeNameByNodeName(letters);
			
			if(key != null){
				//delete call can be mapped to a child node
				RadixTreeNode node = nodes.get(key);
				
				boolean removeNode = node.delete(letters.substring(key.length()));
				
				if(removeNode){
					nodes.remove(key);
				}
				
				return nChild == 1 && removeNode && !isWord;
				
			}else{
				//delete call does not map to any child nodes, no action required
				return false;
			}
		}
	}
	
	//Recurse method for finding if a word exists in the tree
	protected boolean find(String letters){
		
		if(letters.length() == 0){
			return isWord;
		}else{
			
			String nodeName = findNodeNameByFirstLetter(letters);
			
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
		//word contains the letters on the path to this node
		if(isWord){
			list.add(word);
		}
		
		for(String key: nodes.keySet()){
			nodes.get(key).createList(list, word + key);
		}
	}
	
	//Finds the name of the node which matches the first char of letters
	protected String findNodeNameByFirstLetter(String letters){
			
		for(String key: nodes.keySet()){
			if(letters.charAt(0) == key.charAt(0)){
				return key;
			}
		}
		
		return null;
	}
	
	//finds the name of the node who's name is a prefix of letters
	protected String findNodeNameByNodeName(String letters){
		
		for(String key: nodes.keySet()){
			if(letters.startsWith(key)){
				return key;
			}
		}
		
		return null;
	}
	
	//Takes in 2 strings and finds the maximum length string that is a prefix of both inputs
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
