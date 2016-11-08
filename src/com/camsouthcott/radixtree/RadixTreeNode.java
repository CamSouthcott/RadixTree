package com.camsouthcott.radixtree;

import java.util.Map;
import java.util.TreeMap;


//Node class of tree for compressing the storage of strings
//Unless the tree is empty, this code assumes that the terminating node of every branch is a word
public class RadixTreeNode {
	
	private RadixTreeNode parent;
	private Map<String,RadixTreeNode> nodes;
	private boolean isWord = false;
	
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
	
	protected void setIsWord(boolean isWord){
		this.isWord = isWord;
	}
	
	protected boolean isWord(){
		return isWord;
	}
	
	protected Map<String, RadixTreeNode> getChildren(){
		return nodes;
	}
	
	protected RadixTreeNode getParent(){
		return parent;
	}
	
	protected void setParent(RadixTreeNode parent){
		this.parent = parent;
	}
	
	protected Map<String, RadixTreeNode> newNodeMap(){
		//Tree Map is required if alphabetical order is important
		return new TreeMap<String,RadixTreeNode>();
	}
	
	protected void addChild(String key, boolean isWord){
		nodes.put(key, newNode(this, isWord));
	}
	
	protected void addChild(String key, RadixTreeNode child){
		nodes.put(key, child);
		child.setParent(this);
	}
	
	protected void removeChild(String key){
		nodes.remove(key);
	}
	
	protected RadixTreeNode getChild(String key){
		return nodes.get(key);
	}

}
