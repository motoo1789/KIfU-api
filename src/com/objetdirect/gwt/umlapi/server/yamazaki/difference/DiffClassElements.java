package com.objetdirect.gwt.umlapi.server.yamazaki.difference;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.elements.TranseString;



public class DiffClassElements {

	List<IElements> nothasClass = new ArrayList<IElements>();
	List<IElements> surplusClass = new ArrayList<IElements>();
	private TranseString transeSingleton = TranseString.getInstance();

	DiffClassElements(List<IElements> list,List<IElements> list2)
	{
		this.nothasClass = list;
		this.surplusClass = list2;
	}

	void show()
	{
		System.out.println("---------------------");
		System.out.println("-----Class----");
		System.out.println("不足クラス");
		if(nothasClass.size() != 0)
		{
			for(IElements element : nothasClass)
			{
				if(element.getNamebool())
				{
					System.out.println(element.getElementName() + "クラスの修飾子不一致　" + transeSingleton.returnAccessSymbol(element.getAccess()));
				}
				else
				{
					System.out.println("不足のクラス名　" + element.getElementName());
				}
			}
		}

		System.out.println("");
		System.out.println("余剰クラス");
		if(surplusClass.size() != 0)
		{
			for(IElements element : surplusClass)
			{
				System.out.println("余剰のクラス名　" + element.getElementName());
			}
		}
	}
}
