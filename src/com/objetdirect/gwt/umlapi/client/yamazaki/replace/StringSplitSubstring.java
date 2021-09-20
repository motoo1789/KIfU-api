package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

public class StringSplitSubstring {

	private static StringSplitSubstring singleton = new StringSplitSubstring();

	private StringSplitSubstring()
	{

	}

	public static StringSplitSubstring getInstance()
	{
		return singleton;
	}


	public String splitClassname(String splitString)
	{
		String[] splitarray = null;

		if(splitString.contains("!")) 								// フィールド
		{
			splitarray = splitString.split("!");
		}
		else if(splitString.contains("%") && splitString.contains("&")) // パラメータ
		{
			splitarray = splitString.split("%");
		}
		else if(splitString.contains("&")) 							//　メソッド
		{
			splitarray = splitString.split("&");
		}
		else 													// クラス名
		{
			splitarray = splitString.split(";");
		}


		String classname = splitarray[0];
		String fmname = splitarray[1];

		if(fmname.contains("{"))
			fmname = fmname.replace("{", "の");
		else if(fmname.contains("}"))
			fmname = fmname.replace("}", "の");

		return classname + "の" + fmname + "を";
	}

	public String splitFMname(String splitString)
	{
		String[] splitarray = null;

		if(splitString.contains("!")) 								// フィールド
		{
			splitarray = splitString.split("!");
		}
		else if(splitString.contains("%") && splitString.contains("&")) // パラメータ
		{
			splitarray = splitString.split("%");
		}
		else if(splitString.contains("&")) 							//　メソッド
		{
			splitarray = splitString.split("&");
		}
		else 													// クラス名
		{
			splitarray = splitString.split(";");
		}

		String fmname = splitarray[1];

		if(fmname.contains("{"))
			fmname = fmname.replace("{", "の");
		else if(fmname.contains("}"))
			fmname = fmname.replace("}", "の");


		return fmname;
	}
}
