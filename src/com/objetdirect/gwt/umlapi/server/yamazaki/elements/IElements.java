package com.objetdirect.gwt.umlapi.server.yamazaki.elements;

public interface IElements {
	int getAccess();
	String getElementName();
	String getElementType();
	void showParameter();

	void changeTypebool();
	void changeNamebool();
	void changeAccessbool();

	boolean getTypebool();
	boolean getNamebool();
	boolean getAccessbool();
}
