package com.objetdirect.gwt.umlapi.client.yamazaki.elements;

public class TranseString {

	private static TranseString singleton = new TranseString();

	private TranseString()
	{

	}

	public static TranseString getInstance()
	{
		return singleton;
	}

	public String returnTranseType(String before)
	{
		String after = "";

		if(before.contains("I"))
		{
			after = "int";
		}
		else if(before.contains("V"))
		{
			after = "void";
		}
		else if(before.contains("D"))
		{
			after = "double";
		}
		else if(before.contains("QS"))
		{
			after = "String";
		}

		if(before.contains("["))
		{
			int hairetuNum = before.length() - before.replace("[", "").length();


			for(int count = 0; count < hairetuNum; count++)
				after += "[]";
		}

		return after;
	}

	public String returnAccessSymbol(int beforeAccessNum)
	{
		String afterAccess = "";

		if	   (beforeAccessNum == 0)
			afterAccess = "~";

		else if(beforeAccessNum == 1)
			afterAccess = "+";

		else if(beforeAccessNum == 2)
			afterAccess = "-";

		else if(beforeAccessNum == 4)
			afterAccess = "#";

		return afterAccess;
	}
}
