package com.servlet;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stubring 
		 String b = "$xmldata=<?xml version=\"1.0\"?><?xml version=\"1.0\" encoding=\"GB2312\"?>  123";
		 b=b.replace("$xmldata=<?xml version=\"1.0\"?>","<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			
			System.out.print(b);
	}

}
