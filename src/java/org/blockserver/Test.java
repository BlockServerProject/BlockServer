package org.blockserver;

/**
 * The test class for Travis-CI to run.
 */
public class Test{
	private static boolean IS_TEST = false;
	public static void main(String[] args){
		IS_TEST = true;
		System.out.println("[TEST] Testing IO-Lib...");
		// TODO add something to test
		System.out.println("[TEST] Testing server...");
		BlockServer.main(args);
	}
	public static boolean isTest(){
		return IS_TEST;
	}
}
