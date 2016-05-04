package com.macys.stella.test.fasttrack;

import org.jfree.util.Log;

public class test1 {

	public static void main(String[] args) {
	
		String issueNumber="Halted (00895892)";
		String[]isn1=issueNumber.split("\\(");
		System.out.println("1st split---"+isn1[0]+ "and..." +isn1[1] );
		String[] isn2=isn1[1].split("\\)");
		System.out.println("1st split---"+isn2[0] );

	}

}
