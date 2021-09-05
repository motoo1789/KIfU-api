package com.objetdirect.gwt.umlapi.server.yamazaki.main;

import com.objetdirect.gwt.umlapi.server.yamazaki.dao.SavaUMLDSElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.dao.SaveKIfUElements;
import com.objetdirect.gwt.umlapi.server.yamazaki.difference.DiffElements;

public class DiffExe {
	private static DiffExe singleton = new DiffExe();

	private DiffExe()
	{

	}

	public static DiffExe getInstance()
	{
		return singleton;
	}

	public void diffexe()
	{
		// TODO 自動生成されたメソッド・スタブ
		SaveKIfUElements kifu = new SaveKIfUElements();

		SavaUMLDSElements umlds = new SavaUMLDSElements();

		DiffElements deff = new DiffElements();
		deff.deffCheck(kifu, umlds);
	}
}
