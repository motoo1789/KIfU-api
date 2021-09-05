package com.objetdirect.gwt.umlapi.server.yamazaki.difference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.elements.TranseString;


public class DiffMethodElements {

	Map<String,List<IElements>> nothasMethods = new HashMap<String,List<IElements>>();
	Map<String,List<IElements>> surplusMethods = new HashMap<String,List<IElements>>();
	private TranseString transeSingleton = TranseString.getInstance();

	DiffMethodElements(Map<String,List<IElements>> nothasMethods,Map<String,List<IElements>> surplusMethods)
	{
		this.nothasMethods = nothasMethods;
		this.surplusMethods = surplusMethods;
	}

	public void show() {
	// TODO 自動生成されたメソッド・スタブ
		System.out.println("---------------------");
		System.out.println("----Method----");

		for(String classname : nothasMethods.keySet())
		{
			System.out.println("足りないクラス名 " + classname);
			for(IElements method : nothasMethods.get(classname))
			{
				System.out.println("欠損メソッド名　" + method.getElementName());
				if(!method.getAccessbool())
					System.out.println("修飾子の不一致　" + transeSingleton.returnAccessSymbol(method.getAccess()));
				if(!method.getTypebool())
					System.out.println("戻り値の不一致　" + method.getElementType());
				System.out.println();
			}
		}
		System.out.println(" ");
		for(String classname : surplusMethods.keySet())
		{
			System.out.println("余剰クラス名 " + classname);
			for(IElements method : surplusMethods.get(classname))
				System.out.println("余剰メソッド名 " + method.getElementName());
			System.out.println();
		}
	}
}
