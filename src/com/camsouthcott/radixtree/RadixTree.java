package com.camsouthcott.radixtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RadixTree implements Iterable<String>{

	protected RadixTreeNode head;
	
	public RadixTree(){
		head = new RadixTreeNode(null,false);
	}
	
	//For use with child classes
	protected RadixTree(RadixTreeNode head){
		this.head = head;
	}
	
	public boolean find(String word){
		
		if(word != null){
			word = formatWord(word.trim());

			if(word != null && !word.isEmpty()){
				return find(head, word);
			}
		}
		
		return false;
	}
	
	private boolean find(RadixTreeNode node, String letters){
		
		if(letters.length() == 0 ){
			return node.isWord();
		}
		
		String childName = findNodeNameByNodeName(node, letters);
		
		return childName != null && find(node.getChild(childName), letters.substring(childName.length()));
	}
	
	public List<String> toList(){
		List<String> list = new ArrayList<String>();

		toList(head,"", list);
		
		return list;
	}
	
	private void toList(RadixTreeNode node, String prefix, List<String> list){
		
		if(node.isWord()){
			list.add(prefix);
		}
		
		for(String childName: node.getChildren().keySet()){
			toList(node.getChild(childName), prefix + childName, list);
		}
	}
	
	public void addWord(String word){
		
		if(word != null){
			word = formatWord(word.trim());

			if(word != null && !word.isEmpty()){
				addWord(head, word);
			}
		}
	}
	
	private void addWord(RadixTreeNode node, String newEntry){
		
		//new Entry refers to this node
		if(newEntry.length() == 0){
			node.setIsWord(true);
			return;
		}
		
		String childName = findNodeNameByFirstLetter(node, newEntry);
		
		if(childName == null){
			//No node that shares a prefix with newEntry, add new child
			node.addChild(newEntry, true);
			return;
		}
		
		String prefix = getPrefix(newEntry, childName);
		
		if(prefix.length() == newEntry.length() || prefix.length() == childName.length()){
			if(newEntry.length() < childName.length()){
				
				//newEntry should be a parent of the current child
				//remove current child
				RadixTreeNode previousNode = node.getChild(childName);
				node.removeChild(childName);
				
				node.addChild(newEntry, true);
				RadixTreeNode newEntryNode = node.getChild(newEntry);
				
				newEntryNode.addChild(childName.substring(newEntry.length()), previousNode);				
			}else{
				
				// newEntry either is currentChild or is a child of the current child, recursively call addWord
				addWord(node.getChild(childName),newEntry.substring(childName.length()));
			}
		}else{
			
			//newEntry and the current child share a substring, make a new parent node for them
			//Remove the current child
			RadixTreeNode previousNode = node.getChild(childName);
			node.removeChild(childName);
			
			//Create a parent node for the 2 nodes
			node.addChild(prefix, false);
			RadixTreeNode prefixNode = node.getChild(prefix);
			
			//add the nodes to the parent
			prefixNode.addChild(childName.substring(prefix.length()), previousNode);
			prefixNode.addChild(newEntry.substring(prefix.length()), true);
		}
	}
	
	public void deleteWord(String word){
		
		if(word != null){
			word = formatWord(word.trim());

			if(word != null && !word.isEmpty()){
				deleteWord(head, word);
			}
		}
	}
	
	private boolean deleteWord(RadixTreeNode node, String letters){
		
		int nChild = node.getChildren().size();
		
		if(letters.length() == 0){
			//delete is targeting this node
			if(nChild == 0){
				//node is childless, tell parent to remove this node
				return true;

			}else{
				//node has children, mark as not a word, but dont remove node from parent
				node.setIsWord(false);
				return false;
			}
		}else{
			// delete is possibly targeting a child node
			
			String key = findNodeNameByNodeName(node,letters);
			
			if(key != null){
				//delete call can be mapped to a child node
				RadixTreeNode child = node.getChild(key);
				
				boolean removeNode = deleteWord(child, letters.substring(key.length()));
				
				if(removeNode){
					node.removeChild(key);
				}
				
				return nChild == 1 && removeNode && !node.isWord();
				
			}else{
				//delete call does not map to any child nodes, no action required
				return false;
			}
		}
	}
	
	public Iterator<String> iterator(){
		return new RadixTreeIterator(head);
	}
	
	//Finds the name of the child which matches the first char of letters
	protected String findNodeNameByFirstLetter(RadixTreeNode node, String letters){
		
		Map<String, RadixTreeNode> children = node.getChildren();
		
		for(String key: children.keySet()){
			if(letters.charAt(0) == key.charAt(0)){
				return key;
			}
		}
		
		return null;
	}
	
	//finds the name of the child who's name is a prefix of letters
	protected String findNodeNameByNodeName(RadixTreeNode node, String letters){
		
		Map<String, RadixTreeNode> children = node.getChildren();
		
		for(String key: children.keySet()){
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
	
	protected static String formatWord(String word){
		
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
	
    protected static char formatChar(char c){
    	
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
