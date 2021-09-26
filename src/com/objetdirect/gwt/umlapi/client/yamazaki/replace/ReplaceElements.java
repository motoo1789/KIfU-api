package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNode;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

public class ReplaceElements implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLNode replaceTarget;

	private String surplusKey;
	private String surplusValue;
	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;


	public ReplaceElements(String surplusKey,String surplusValue,String nothasKey,String nothasValue,UMLNode component)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.replaceTarget = component;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	public void drawReplaceAddDelete()
	{
		if(surplusKey.contains("{"))
		{
			char[] tmp = nothasValue.toCharArray();
			replaceTarget.setVisibility(UMLVisibility.getVisibilityFromToken(tmp[0]));
		}
		else if(surplusKey.contains("}"))
		{
			replaceTarget.setType(nothasValue);;
		}
		else
		{
			replaceTarget.setName(nothasValue);
		}

	}

	private void setMessage()
	{
		//  何が入っているか　？？？の？？？をまで
		String classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);

		String  appendReplaceString = classnamefmname + nothasValue + "に変換";
		message = appendReplaceString;
	}

	public CheckBox getCheckBox()
	{
		return this.checkbox;
	}


//	public String getMessage()
//	{
//		return this.message;
//	}


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

}
