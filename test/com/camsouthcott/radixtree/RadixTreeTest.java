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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RadixTreeTest {
	
	public static void main(String[] args){
		
		System.out.println("Trie Test Started");
		Result result = JUnitCore.runClasses(RadixTreeTest.class);

		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		
		System.out.println("Trie Test Result: " + result.wasSuccessful());
	}
	
	@Test
	public void insertion(){
		RadixTree radixTree = new RadixTree();
		
		String word = "Olives";
		
		radixTree.addWord(word);
		
		assertEquals(word,radixTree.find(word));
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
		assertEquals(word, radixTree.find(word));
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
		assertEquals(lowercase, radixTree.find(lowercase));
		assertEquals(lowercase, radixTree.find(uppercase));
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
		assertEquals(smallWord, radixTree.find(smallWord));
		assertEquals(bigWord, radixTree.find(bigWord));
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
		assertEquals(smallWord, radixTree.find(smallWord));
		assertEquals(bigWord, radixTree.find(bigWord));
		assertEquals(biggestWord, radixTree.find(biggestWord));
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
		assertEquals(vertex, radixTree.find(vertex));
		assertEquals(branch1, radixTree.find(branch1));
		assertEquals(branch2, radixTree.find(branch2));
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
		assertEquals(smallWord, radixTree.find(smallWord));
		assertEquals(bigWord, radixTree.find(bigWord));
		assertEquals(biggestWord, radixTree.find(biggestWord));
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
		assertEquals(candle, radixTree.find(candle));
		assertEquals(cane, radixTree.find(cane));
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
		assertEquals(base, radixTree.find(base));
		assertEquals(baseball, radixTree.find(baseball));
		assertEquals(bass, radixTree.find(bass));
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
		
		assertEquals(null,radixTree.find(null));
	}
	
	@Test
	public void findEmpty(){
		RadixTree radixTree = new RadixTree();
		
		String word = "apple";
		radixTree.addWord(word);
		
		assertEquals(null,radixTree.find(""));
	}
	
	@Test
	public void findNotIn(){
		RadixTree radixTree = new RadixTree();
		
		String word = "apple";
		radixTree.addWord(word);
		
		assertEquals(null,radixTree.find("apples"));
	}

	@Test
	public void largeTree(){
		
		List<String> sortedWordList = new LinkedList<String>();
		List<String> shuffledWordList = new LinkedList<String>();
		RadixTree radixTree = new RadixTree();
		int wordListSize = 109561;
		
		

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(RadixTreeTest.class.getResourceAsStream("/words.txt")));

			String line;


			while((line = br.readLine()) != null){
				String[] wordArray = line.split(" ");

				for(String word: wordArray){
					sortedWordList.add(word);
					shuffledWordList.add(word);
				}
			}
		} catch (IOException e) {
			fail("IO exception");
		}

		Collections.shuffle(shuffledWordList);
		
		assertEquals(wordListSize,sortedWordList.size());
		assertEquals(wordListSize,shuffledWordList.size());

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

		//assertEquals(wordListSize,wordListFromTree.size());

		for(int i = 0; i < wordListFromTree.size(); i++){
			assertEquals(sortedWordList.get(i), wordListFromTree.get(i));
		}
	}
}

