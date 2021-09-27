package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNode;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

public class ReplaceClassStereoType implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact replaceTarget;

	private String surplusKey;
	private String surplusValue;
	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;


	public ReplaceClassStereoType(String surplusKey,String surplusValue,String nothasKey,String nothasValue,ClassArtifact component)
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

		if(surplusKey.contains(";")) // 本当はやりたくないがわからない
		{
			replaceTarget.getClassPartNameArtifact().setStereotype(nothasValue);
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
