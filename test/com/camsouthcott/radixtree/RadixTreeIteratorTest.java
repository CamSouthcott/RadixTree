package com.camsouthcott.radixtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class RadixTreeIteratorTest {
	
	@Test
	public void empty(){
		RadixTree radixTree = new RadixTree();
		
		Iterator<String> iterator = radixTree.iterator();
		
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}
	
	@Test
	public void child(){
		RadixTree radixTree = new RadixTree();
		
		String olive = "olive";
		String olives = "olives";
		
		radixTree.addWord(olive);
		radixTree.addWord(olives);
		
		Iterator<String> iterator = radixTree.iterator();
		
		assertTrue(iterator.hasNext());
		assertEquals(olive, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(olives, iterator.next());
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}
	
	@Test
	public void siblings(){
		RadixTree radixTree = new RadixTree();
		
		String base = "base";
		String bass = "bass";
		
		radixTree.addWord(base);
		radixTree.addWord(bass);
		
		Iterator<String> iterator = radixTree.iterator();
		
		assertTrue(iterator.hasNext());
		assertEquals(base, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(bass, iterator.next());
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}
	
	//Tests for proper function when a chain of non-word nodes is created
	@Test
	public void delete(){
		RadixTree radixTree = new RadixTree();
		
		String base = "base";
		String bass = "bass";
		String baseball = "baseball";
		
		radixTree.addWord(base);
		radixTree.addWord(bass);
		radixTree.addWord(baseball);
		
		String[] addThenRemove = {"a","bb","bab","basb","basef","basebp","basebap","basebale","baseballe"};
		
		for(String word: addThenRemove){
			radixTree.addWord(word);
			radixTree.deleteWord(word);
		}
		
		Iterator<String> iterator = radixTree.iterator();
		
		assertTrue(iterator.hasNext());
		assertEquals(base, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(baseball, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(bass, iterator.next());
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
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
		
		Iterator<String> iterator = radixTree.iterator();
		
		for(String word: sortedWordList){
			assertEquals(word, iterator.next());
		}
		
		assertEquals(iterator.hasNext(), false);
	}

}
