package com.camsouthcott.radixtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;


public class RadixTreeDeletionTest {
	
	@Test
	public void delete(){
		RadixTree radixTree = new RadixTree();
		
		String word = "Olives";
		
		radixTree.addWord(word);
		
		assertEquals(word,radixTree.find(word)? word:null);
		
		radixTree.deleteWord(word);
		
		assertEquals(null,radixTree.find(word)? word:null);
	}
	
	@Test
	public void deleteNull(){
		RadixTree radixTree = new RadixTree();
		
		String word = "Olives";
		
		radixTree.addWord(word);
		
		assertEquals(word,radixTree.find(word)? word:null);
		
		radixTree.deleteWord(null);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(1, wordList.size());
		assertEquals(word,radixTree.find(word)? word:null);
	}
	
	@Test
	public void deleteNotInTree(){
		RadixTree radixTree = new RadixTree();
		
		String word = "Olives";
		String wordNotFound = "olive";
		
		radixTree.addWord(word);
		
		assertEquals(word,radixTree.find(word)? word:null);
		
		radixTree.deleteWord(wordNotFound);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(1, wordList.size());
		assertEquals(word,radixTree.find(word)? word:null);
	}
	
	@Test
	public void deleteChild(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "part";
		String child = "party";
		
		radixTree.addWord(parent);
		radixTree.addWord(child);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(child,radixTree.find(child)? child:null);
		
		radixTree.deleteWord(child);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(null,radixTree.find(child)? child:null);
	}
	
	@Test
	public void deleteParent(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "part";
		String child = "party";
		
		radixTree.addWord(parent);
		radixTree.addWord(child);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(child,radixTree.find(child)? child:null);
		
		radixTree.deleteWord(parent);
		
		assertEquals(null,radixTree.find(parent)? parent:null);
		assertEquals(child,radixTree.find(child)? child:null);
	}
	
	@Test
	public void deleteChildMultipleChildren(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "base";
		String child1 = "baseball";
		String child2 = "bases";
		
		radixTree.addWord(parent);
		radixTree.addWord(child1);
		radixTree.addWord(child2);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
		
		radixTree.deleteWord(child1);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(null,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
	}
	
	@Test
	public void deleteParentMultipleChildren(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "base";
		String child1 = "baseball";
		String child2 = "bases";
		
		radixTree.addWord(parent);
		radixTree.addWord(child1);
		radixTree.addWord(child2);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
		
		radixTree.deleteWord(parent);
		
		assertEquals(null,radixTree.find(parent)? parent:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
	}
	
	@Test
	public void deleteAllChildrenParentNotWord(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "base";
		String child1 = "baseball";
		String child2 = "bases";
		
		radixTree.addWord(child1);
		radixTree.addWord(child2);
		
		assertEquals(null,radixTree.find(parent)? parent:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
		
		radixTree.deleteWord(child1);
		radixTree.deleteWord(child2);
		
		List<String> wordList = radixTree.toList();
		assertEquals(0,wordList.size());
	}
	
	@Test
	public void deleteAllChildrenParentIsWord(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "base";
		String child1 = "baseball";
		String child2 = "bases";
		
		radixTree.addWord(parent);
		radixTree.addWord(child1);
		radixTree.addWord(child2);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
		
		radixTree.deleteWord(child1);
		radixTree.deleteWord(child2);
		
		List<String> words = radixTree.toList();
		assertEquals(1,words.size());
		assertEquals(parent,radixTree.find(parent)? parent:null);
	}
	
	@Test
	public void deleteMiddle(){
		RadixTree radixTree = new RadixTree();
		
		String parent = "par";
		String middle = "part";
		String child1 = "party";
		String child2 = "partly";
		
		radixTree.addWord(parent);
		radixTree.addWord(middle);
		radixTree.addWord(child1);
		radixTree.addWord(child2);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(middle,radixTree.find(middle)? middle:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
		
		radixTree.deleteWord(middle);
		
		assertEquals(parent,radixTree.find(parent)? parent:null);
		assertEquals(null,radixTree.find(middle)? middle:null);
		assertEquals(child1,radixTree.find(child1)? child1:null);
		assertEquals(child2,radixTree.find(child2)? child2:null);
	}

	@Test
	public void largeTree(){
		
		List<String> sortedWordList = new ArrayList<String>(RadixTreeTest.WORD_LIST_SIZE + 1);
		List<String> shuffledWordList = new ArrayList<String>(RadixTreeTest.WORD_LIST_SIZE + 1);
		RadixTree radixTree = new RadixTree();
		
		List<List<String>> listsToFill = new ArrayList<List<String>>();
		listsToFill.add(sortedWordList);
		listsToFill.add(shuffledWordList);
		RadixTreeTest.fillWordLists(listsToFill);

		Collections.shuffle(shuffledWordList);
		
		assertEquals(RadixTreeTest.WORD_LIST_SIZE,sortedWordList.size());
		assertEquals(RadixTreeTest.WORD_LIST_SIZE,shuffledWordList.size());

		for(String word : shuffledWordList){
			try{
			radixTree.addWord(word);
			}catch(Exception e){
				System.out.println(word);
				e.printStackTrace();
				fail("exception");
			}
		}

		List<String> wordListFromTree = radixTree.toList();
		Iterator<String> treeListIterator = wordListFromTree.iterator();

		assertEquals(RadixTreeTest.WORD_LIST_SIZE,wordListFromTree.size());

		for(String word : sortedWordList){
			assertEquals(word, treeListIterator.next());
		}
		
		//Choose 20 words at random and delete them from the tree
		List<String> deletionList = new ArrayList<String>();
		deletionList.add(shuffledWordList.get(81151));
		deletionList.add(shuffledWordList.get(102043));
		deletionList.add(shuffledWordList.get(5068));
		deletionList.add(shuffledWordList.get(99285));
		deletionList.add(shuffledWordList.get(82171));
		deletionList.add(shuffledWordList.get(65656));
		deletionList.add(shuffledWordList.get(18098));
		deletionList.add(shuffledWordList.get(67922));
		deletionList.add(shuffledWordList.get(65962));
		deletionList.add(shuffledWordList.get(55842));
		deletionList.add(shuffledWordList.get(3296));
		deletionList.add(shuffledWordList.get(91591));
		deletionList.add(shuffledWordList.get(83303));
		deletionList.add(shuffledWordList.get(25208));
		deletionList.add(shuffledWordList.get(77069));
		deletionList.add(shuffledWordList.get(69696));
		deletionList.add(shuffledWordList.get(47156));
		deletionList.add(shuffledWordList.get(80777));
		deletionList.add(shuffledWordList.get(31758));
		deletionList.add(shuffledWordList.get(8211));
		
		for(String word: deletionList){
			radixTree.deleteWord(word);
		}
		
		List<String> remainingWords = radixTree.toList();
		Collections.sort(deletionList);
		
		assertEquals(sortedWordList.size(), deletionList.size() + remainingWords.size());
		
		int deletionIndex = 0;
		Iterator<String> remainingWordsIterator = remainingWords.iterator();
		
		//assumes all lists are sorted
		//Checks that the deletion list and the remaining words in the tree form the original list
		for(String word: sortedWordList){
			if(deletionIndex < deletionList.size() && word.equals(deletionList.get(deletionIndex))){
				deletionIndex++;
			}else{
				assertEquals(word,remainingWordsIterator.next());
			}
		}
	}
}
