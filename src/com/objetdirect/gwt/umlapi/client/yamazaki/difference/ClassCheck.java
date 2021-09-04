package com.objetdirect.gwt.umlapi.client.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.yamazaki.elements.IElements;



public class ClassCheck{

	private static ClassCheck singleton = new ClassCheck();

	public static ClassCheck getInstance()
	{
		return singleton;
	}

	public DiffClassElements checkClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap)
	{
		return new DiffClassElements(nothasClass(kifu_classMap,umlds_classMap), surplusClass(kifu_classMap,umlds_classMap));
	}

	public List<IElements> nothasClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<IElements> nothasClass = new ArrayList<IElements>();

		for(String umldsKey : umlds_classMap.keySet())
		{
			if(kifu_classMap.get(umldsKey) == null)
			{
				nothasClass.add(umlds_classMap.get(umldsKey));
			}
			else
			{
				umlds_classMap.get(umldsKey).changeNamebool();
				if(umlds_classMap.get(umldsKey).getAccess() != kifu_classMap.get(umldsKey).getAccess())
				{
					nothasClass.add(umlds_classMap.get(umldsKey));
				}
			}
		}


		return nothasClass;
	}


	public List<IElements> surplusClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<IElements> surplusClass = new ArrayList<IElements>();

		for(String kifuKey : kifu_classMap.keySet())
		{
			if(umlds_classMap.get(kifuKey) == null)
			{
				surplusClass.add(kifu_classMap.get(kifuKey));
			}
//			else
//			{
//				kifu_classMap.get(kifuKey).changeNamebool();
//				if(umlds_classMap.get(kifuKey).getAccess() != kifu_classMap.get(kifuKey).getAccess())
//				{
//					surplusClass.add(kifu_classMap.get(kifuKey));
//				}
//			}
		}


		return surplusClass;
	}

}
