package com.objetdirect.gwt.umlapi.client.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.client.yamazaki.elements.IElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.elements.TranseString;



public class DiffFieldElements {
	Map<String,List<IElements>> nothasFields = new HashMap<String,List<IElements>>();
	Map<String,List<IElements>> surplusFields = new HashMap<String,List<IElements>>();
	private TranseString transeSingleton = TranseString.getInstance();

	DiffFieldElements(Map<String,List<IElements>> nothasFields,Map<String,List<IElements>> surplusFields)
	{
		this.nothasFields = nothasFields;
		this.surplusFields = surplusFields;
	}

	public void show() {
	// TODO 自動生成されたメソッド・スタブ
		System.out.println("---------------------");
		System.out.println("-----Ffield----");

		for(String classname : nothasFields.keySet())
		{
			System.out.println("足りないクラス名 " + classname);
			for(IElements field : nothasFields.get(classname))
			{
				System.out.println("欠損フィールド名　" + field.getElementName());
				if(!field.getAccessbool())
					System.out.println("修飾子の不一致　" + transeSingleton.returnAccessSymbol(field.getAccess()));
				if(!field.getTypebool())
					System.out.println("型の不一致　" + field.getElementType());
				System.out.println();
			}
		}
		System.out.println(" ");
		for(String classname : surplusFields.keySet())
		{
			System.out.println("余剰クラス名 " + classname);
			for(IElements field : surplusFields.get(classname))
				System.out.println("余剰フィールド　" + field.getElementName());
			System.out.println();
		}

	}
}
