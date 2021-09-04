package com.objetdirect.gwt.umlapi.client.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.client.yamazaki.dao.IGetElements;


public class DiffElements {

	ClassCheck checkClass = ClassCheck.getInstance();
	FieldCheck checkField = FieldCheck.getInstance();
	MethodCheck checkMethod = MethodCheck.getInstance();
	ParametarCheck checkParametar = ParametarCheck.getInstance();


	public void deffCheck(IGetElements kifu, IGetElements umlds)
	{
		System.out.println("---------------------");
		System.out.println("-----diff check----");

//		for(String kifuKey : kifu.getClassMap().keySet())
//		{
//			if(umlds.getClassMap().get(kifuKey) == null)
//			{
//				surplusClass.add(kifuKey);
//			}
//			else
//			{
//				// map.put(kifuKey,List<IElements>)
//
//				// map.put(kifuKey.List<IElements{method})
//
//				checkObject.surplusFieldMethod(kifu.getFieldMap(), umlds.getFieldMap());
//				checkObject.surplusFieldMethod(kifu.getMethodMap(), umlds.getMethodMap());
//			}
//		}
		// Class
		DiffClassElements diffClassElements = checkClass.checkClass(kifu.getClassMap(), umlds.getClassMap());
		diffClassElements.show();

		// Field
		DiffFieldElements diffFieldElements = checkField.checkField(kifu.getFieldMap(), umlds.getFieldMap());
		diffFieldElements.show();

/*
 * 		Method
		Mainメソッドの処理は後回しにしようとおもう
		余剰クラスの余剰メソッドがソースコードで存在する場合に余剰と判断しないバグがある
*/
		DiffMethodElements diffMethodElements = checkMethod.checkMethod(kifu.getMethodMap(), umlds.getMethodMap());
		diffMethodElements.show();


		// Parametar
		DiffParaElements diffParametarElements = checkParametar.hasBothMethod(kifu.getMethodMap(), umlds.getMethodMap());
		diffParametarElements.show();

	}

}
