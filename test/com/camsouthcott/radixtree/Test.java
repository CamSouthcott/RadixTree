package com.camsouthcott.radixtree;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test {

	public static void main(String[] args){
		
		System.out.println("Trie Test Started");
		Result result = JUnitCore.runClasses(RadixTreeTest.class);

		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		
		System.out.println("Trie Test Result: " + result.wasSuccessful());
	}
}
