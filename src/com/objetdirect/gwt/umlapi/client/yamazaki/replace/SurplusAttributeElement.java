package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;

public class SurplusAttributeElement implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact targetClassArtifact;
	private UMLClassAttribute targetAttribute;

	private String surplusKey;
	private String surplusValue;

	private String message;

	private CheckBox checkbox;

	public SurplusAttributeElement(String surplusKey,String surplusValue,ClassArtifact component,UMLClassAttribute targetAttribute)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetClassArtifact = component;
		this.targetAttribute = targetAttribute;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname= surplusKey.split("!");
		String classname = splitDiffAttributeClassname[0];
		message = classname + "の" + surplusValue + "を削除しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("!"))
		{
			targetClassArtifact.removeAttribute(targetAttribute);
		}
	}

	@Override
	public CheckBox getCheckBox() {
		// TODO 自動生成されたメソッド・スタブ
		return checkbox;
	}

	@Override
	public String getSurplusKey() {
		// TODO 自動生成されたメソッド・スタブ
		return surplusKey;
	}

	@Override
	public String getSurplusValue() {
		// TODO 自動生成されたメソッド・スタブ
		return surplusValue;
	}

	@Override
	public String getNothasKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getNothasValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
