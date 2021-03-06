package com.camsouthcott.radixtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RadixTreeTest {
	
	public static final int WORD_LIST_SIZE = 109561;
	
	@Test
	public void insertion(){
		RadixTree radixTree = new RadixTree();
		
		String word = "Olives";
		
		radixTree.addWord(word);
		
		assertEquals(word,radixTree.find(word)? word:null);
	}
	
	@Test
	public void listCreation(){
		RadixTree radixTree = new RadixTree();
		
		//Must be in alphabetical order
		String[] wordArray = {"apples","bananas","grapes","pears"};
		
		for(String word: wordArray){
			radixTree.addWord(word);
		}
		
		List<String> wordList = radixTree.toList();
		
		for(int i = 0; i < wordArray.length; i++){
			assertEquals(wordArray[i],wordList.get(i));
		}
		
		assertEquals(wordArray.length, wordList.size());
	}
	
	@Test
	public void insertDuplicate(){
		RadixTree radixTree = new RadixTree();
		
		String word = "monkeys";
		
		radixTree.addWord(word);
		radixTree.addWord(word);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(1, wordList.size());
		assertEquals(word, wordList.get(0));
		assertEquals(word, radixTree.find(word)? word:null);
	}
	
	@Test
	public void insertCapitals(){
		
		RadixTree radixTree = new RadixTree();
		
		String lowercase = "apples";
		String uppercase = "APPLES";
		
		radixTree.addWord(lowercase);
		radixTree.addWord(uppercase);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(1, wordList.size());
		assertEquals(lowercase, wordList.get(0));
		assertEquals(lowercase, radixTree.find(lowercase)? lowercase:null);
		assertEquals(lowercase, radixTree.find(uppercase)? lowercase:null);
	}
	
	@Test 
	public void insertLarger(){
		RadixTree radixTree = new RadixTree();
		
		String smallWord = "base";
		String bigWord = "baseball";
		
		radixTree.addWord(smallWord);
		radixTree.addWord(bigWord);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(2, wordList.size());
		assertEquals(smallWord, wordList.get(0));
		assertEquals(bigWord, wordList.get(1));
		assertEquals(smallWord, radixTree.find(smallWord)? smallWord:null);
		assertEquals(bigWord, radixTree.find(bigWord)? bigWord:null);
	}
	
	@Test 
	public void insertSmaller(){
		RadixTree radixTree = new RadixTree();
		
		String smallWord = "base";
		String bigWord = "baseball";
		String biggestWord = "baseballs";
		
		radixTree.addWord(bigWord);
		radixTree.addWord(biggestWord);
		radixTree.addWord(smallWord);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(3, wordList.size());
		assertEquals(smallWord, wordList.get(0));
		assertEquals(bigWord, wordList.get(1));
		assertEquals(biggestWord, wordList.get(2));
		assertEquals(smallWord, radixTree.find(smallWord)? smallWord:null);
		assertEquals(bigWord, radixTree.find(bigWord)? bigWord:null);
		assertEquals(biggestWord, radixTree.find(biggestWord)? biggestWord:null);
	}
	
	@Test
	public void insertAtVertex(){
		RadixTree radixTree = new RadixTree();
		
		String vertex = "pan";
		String branch1 = "panel";
		String branch2 = "pants";
		
		radixTree.addWord(branch1);
		radixTree.addWord(branch2);
		radixTree.addWord(vertex);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(3, wordList.size());
		assertEquals(vertex, wordList.get(0));
		assertEquals(branch1, wordList.get(1));
		assertEquals(branch2, wordList.get(2));
		assertEquals(vertex, radixTree.find(vertex)? vertex:null);
		assertEquals(branch1, radixTree.find(branch1)? branch1:null);
		assertEquals(branch2, radixTree.find(branch2)? branch2:null);
	}
	
	@Test 
	public void insertMiddle(){
		RadixTree radixTree = new RadixTree();
		
		String smallWord = "base";
		String bigWord = "baseball";
		String biggestWord = "baseballs";
		
		radixTree.addWord(smallWord);
		radixTree.addWord(biggestWord);
		radixTree.addWord(bigWord);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(3, wordList.size());
		assertEquals(smallWord, wordList.get(0));
		assertEquals(bigWord, wordList.get(1));
		assertEquals(biggestWord, wordList.get(2));
		assertEquals(smallWord, radixTree.find(smallWord)? smallWord:null);
		assertEquals(bigWord, radixTree.find(bigWord)? bigWord:null);
		assertEquals(biggestWord, radixTree.find(biggestWord)? biggestWord:null);
	}
	
	@Test
	public void insertSplit(){
		RadixTree radixTree = new RadixTree();
		
		String cane = "cane";
		String candle = "candle";
		
		radixTree.addWord(cane);
		radixTree.addWord(candle);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(2, wordList.size());
		assertEquals(candle, wordList.get(0));
		assertEquals(cane, wordList.get(1));
		assertEquals(candle, radixTree.find(candle)? candle:null);
		assertEquals(cane, radixTree.find(cane)? cane:null);
	}
	
	@Test
	public void insertSplitWithChildren(){
		RadixTree radixTree = new RadixTree();
		
		String base = "base";
		String baseball = "baseball";
		String bass = "bass";
		
		radixTree.addWord(base);
		radixTree.addWord(baseball);
		radixTree.addWord(bass);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(3, wordList.size());
		assertEquals(base, wordList.get(0));
		assertEquals(baseball, wordList.get(1));
		assertEquals(bass, wordList.get(2));
		assertEquals(base, radixTree.find(base)? base:null);
		assertEquals(baseball, radixTree.find(baseball)? baseball:null);
		assertEquals(bass, radixTree.find(bass)? bass:null);
	}
	
	@Test 
	public void insertNull(){
		RadixTree radixTree = new RadixTree();
		
		radixTree.addWord(null);
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(0, wordList.size());
	}
	
	@Test 
	public void insertEmpty(){
		RadixTree radixTree = new RadixTree();
		
		radixTree.addWord("");
		
		List<String> wordList = radixTree.toList();
		
		assertEquals(0, wordList.size());
	}
	
	@Test
	public void findNull(){
		RadixTree radixTree = new RadixTree();
		
		String word = "apple";
		radixTree.addWord(word);
		
		assertEquals(null,radixTree.find(null)?"NotNull":null);
	}
	
	@Test
	public void findEmpty(){
		RadixTree radixTree = new RadixTree();
		
		String word = "apple";
		radixTree.addWord(word);
		
		assertEquals(null,radixTree.find("")?"NotNull":null);
	}
	
	@Test
	public void findNotIn(){
		RadixTree radixTree = new RadixTree();
		
		String word = "apple";
		radixTree.addWord(word);
		
		assertEquals(null,radixTree.find("apples")?"NotNull":null);
	}

	@Test
	public void largeTree(){
		
		List<String> sortedWordList = new ArrayList<String>(WORD_LIST_SIZE + 1);
		List<String> shuffledWordList = new ArrayList<String>(WORD_LIST_SIZE + 1);
		RadixTree radixTree = new RadixTree();
		
		List<List<String>> listsToFill = new ArrayList<List<String>>();
		listsToFill.add(sortedWordList);
		listsToFill.add(shuffledWordList);
		fillWordLists(listsToFill);

		Collections.shuffle(shuffledWordList);
		
		assertEquals(WORD_LIST_SIZE,sortedWordList.size());
		assertEquals(WORD_LIST_SIZE,shuffledWordList.size());

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

		assertEquals(WORD_LIST_SIZE,wordListFromTree.size());

		for(String word : sortedWordList){
			assertEquals(word, treeListIterator.next());
		}
	}
	
	public static void fillWordLists(List<List<String>> lists ){
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(RadixTreeTest.class.getResourceAsStream("/words.txt")));

			String line;


			while((line = br.readLine()) != null){
				String[] wordArray = line.split(" ");

				for(String word: wordArray){
					for(List<String> list: lists){
						list.add(word);
					}
				}
			}
		} catch (IOException e) {
			fail("IO exception");
		}
	}
}

