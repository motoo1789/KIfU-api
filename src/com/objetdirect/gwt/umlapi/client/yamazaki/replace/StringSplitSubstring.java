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
		String[] splitarray = new String[2];
		String classname = "";
		String fmname = "";

		if(splitString.contains("!")) 								// フィールド
		{
			splitarray = splitString.split("!");
			classname = splitarray[0] + "の";
			fmname = splitarray[1];

			if(fmname.contains("{"))
				fmname = fmname.replace("{", "の") + "を";
			else if(fmname.contains("}"))
				fmname = fmname.replace("}", "の") + "を";

			classname = classname + fmname;
		}
		else if(splitString.contains("%") && splitString.contains("&")) // パラメータ
		{
			splitarray = splitString.split("%");
			classname = splitarray[0].replace("&", "のメソッド");
			classname.concat("にある");

			fmname = splitarray[1];
			if(fmname.contains("}"))
			{
				fmname = "パラメータ" + fmname.replace("}", "の");
			}

			classname = classname+ fmname + "を";
		}
		else if(splitString.contains("&")) 							//　メソッド
		{
			splitarray = splitString.split("&");

			classname = splitarray[0] + "の";
			fmname = splitarray[1];

			if(fmname.contains("{"))
				fmname = fmname.replace("{", "の") + "を";
			else if(fmname.contains("}"))
				fmname = fmname.replace("}", "の") + "を";

			classname = classname + fmname;
		}
		else 													// クラス名
		{
			splitarray = splitString.split(";");

			classname = splitarray[0] + "の";
			fmname = splitarray[1] + "を";

			classname = classname + fmname;
		}

		return classname;
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

	public String getTypeorVisibility(String type)
	{
		if(type.contains("{"))
			return "visibility";
		else if(type.contains("}"))
			return "type";
		else
			return "name";
	}
}
