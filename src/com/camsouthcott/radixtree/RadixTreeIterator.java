package com.camsouthcott.radixtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

//Iterator that traverses the radix tree in alphabetical order
//Adding or removing words to the radix tree during the execution of this iterator may cause errors
//to add the above functionality, the nodes would have to be modified to store their value in their node
//currently nodes have no way of quickly reconstructing the path that lead to them, 
//so creating the word stored at a node must be done from the root of the tree
public class RadixTreeIterator implements Iterator<String>{

	
	private Stack<Iterator<Map.Entry<String, RadixTreeNode>>> iterators;
	private ArrayList<String> keys;
	private RadixTreeNode currentNode;
	
	public RadixTreeIterator(RadixTreeNode root){
		iterators = new Stack<Iterator<Map.Entry<String, RadixTreeNode>>>();
		keys = new ArrayList<String>();
		currentNode = root;
		move();
	}
	
	@Override
	public boolean hasNext() {
		return keys.size() != 0;
	}

	@Override
	public String next() {
		
		//return null if the tree has been fully traversed
		if(keys.size() == 0){
			return null;
		}
		
		StringBuilder word = new StringBuilder();
		
		for(String key :keys){
			word.append(key);
		}
		
		move();
		
		return word.toString();
	}
	
	//This method and the methods it calls assume that the bottom node of every branch is a word.
	private void move(){

		Map<String,RadixTreeNode> children = currentNode.getChildren();
		
		if(children.size() != 0){
			moveToChild();
		}else{
			moveToSibling();
		}
	}
	
	private void moveToChild(){
		
		do{
			Iterator<Map.Entry<String, RadixTreeNode>> iterator = currentNode.getChildren().entrySet().iterator();
			
			Map.Entry<String,RadixTreeNode> firstEntry = iterator.next();
			
			keys.add(firstEntry.getKey());
			currentNode = firstEntry.getValue();
			iterators.push(iterator);
		}while(!currentNode.isWord());
	}
	
	private void moveToSibling(){
		
		while(iterators.size() != 0){
			Iterator<Map.Entry<String, RadixTreeNode>> iterator = iterators.pop();
			
			keys.remove(keys.size() -1);
			
			if(iterator.hasNext()){
				//currentNode has siblings that have not been traversed
				Map.Entry<String, RadixTreeNode> entry = iterator.next();
				
				keys.add(entry.getKey());
				currentNode = entry.getValue();
				iterators.push(iterator);
				
				if(!currentNode.isWord()){
					moveToChild();
				}
				return;
			}else{
				//All direct sibling nodes have been traversed, move to parent and check for siblings
				currentNode = currentNode.getParent();
			}
		}
		
		//if the code exits here, the iterator has traversed the entire tree
	}

}
