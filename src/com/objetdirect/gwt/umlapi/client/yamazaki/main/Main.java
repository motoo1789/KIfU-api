package com.objetdirect.gwt.umlapi.client.yamazaki.main;

import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.yamazaki.dao.SavaUMLDSElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.dao.SaveKIfUElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.difference.DiffElements;



public class Main {

	public void diffexe()
	{
		// TODO 自動生成されたメソッド・スタブ
		SaveKIfUElements kifu = new SaveKIfUElements();

		SavaUMLDSElements umlds = new SavaUMLDSElements();

		DiffElements deff = new DiffElements();
		deff.deffCheck(kifu, umlds);
	}


}