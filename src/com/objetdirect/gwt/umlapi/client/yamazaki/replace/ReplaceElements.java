package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.objetdirect.gwt.umlapi.client.artifacts.ISetStrokeRED;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNode;

public class ReplaceElements {

	private StringSplitSubstring splitsubstringObject;

	private UMLNode replaceTarget;

	private String surplusKey;
	private String surplusValue;
	private String nothasKey;
	private String nothasValue;

	private String message;


	public ReplaceElements(String surplusKey,String surplusValue,String nothasKey,String nothasValue,UMLNode component)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.replaceTarget = component;
	}

	private void setMessage()
	{
		String classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);


		message = classnamefmname;
	}

	public String getSurplusKey()
	{
		return this.surplusKey;
	}

	public String getSurplusValue()
	{
		return this.surplusValue;
	}

	public String getNothasKey()
	{
		return this.nothasKey;
	}

	public String getNothasValue()
	{
		return this.nothasValue;
	}

	public void strokeRED()
	{
		setStrokeREDTarget.setStrokeRED(key);
	}
}
