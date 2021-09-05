package com.objetdirect.gwt.umlapi.server.yamazaki.difference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.server.yamazaki.elements.MethodParametar;


public class DiffParaElements {

	Map<String,List<MethodParametar>> surplusPaarametar = new HashMap<String,List<MethodParametar>>();
	Map<String,List<MethodParametar>> nothasParametar = new HashMap<String,List<MethodParametar>>();

	public DiffParaElements(Map<String,List<MethodParametar>> nothas,Map<String,List<MethodParametar>> surplus)
	{
		this.surplusPaarametar = surplus;
		this.nothasParametar = nothas;
	}

	public void show() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("---------------------");
		System.out.println("----Parametar----");

		for(String methodName : nothasParametar.keySet())
		{
			System.out.println("足りないメソッド名 " + methodName);
			for(MethodParametar para : nothasParametar.get(methodName))
			{
				System.out.println("欠損パラメータ名 " + para.getName());
				if(!para.getTypebool())
					System.out.println("型の不一致　" + para.getType());
				System.out.println();
			}

		}
		System.out.println(" ");
		for(String methodName : surplusPaarametar.keySet())
		{
			System.out.println("余剰なメソッド名 " + methodName);
			for(MethodParametar para : surplusPaarametar.get(methodName))
			{
				System.out.println("余剰パラメータ名 " + para.getName());
				System.out.println();
			}
		}
	}
}
