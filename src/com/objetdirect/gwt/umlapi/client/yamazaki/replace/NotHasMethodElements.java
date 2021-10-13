package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;

public class NotHasMethodElements implements IDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact addTarget;

	private String surplusKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;

	public NotHasMethodElements(String surplusKey,String nothasValue,ClassArtifact component)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.nothasValue = nothasValue;
		this.addTarget = component;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffMethodKey = surplusKey.split("&");
		String classname = splitDiffMethodKey[0];
		message = classname + "に" + nothasValue + "を追加しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("&")) 							//　メソッド
		{
			addTarget.addMethod(UMLClassMethod.parseMethod(nothasValue));
			addTarget.rebuildGfxObject();

		}

	}

	@Override
	public CheckBox getCheckBox() {
		// TODO 自動生成されたメソッド・スタブ
		return checkbox;
	}

	@Override
	public String getSurplusKey()
	{
		return this.surplusKey;
	}

	public String getSurplusValue()
	{
		return "ReplaceElementsのgetSurplusValueはなし";
	}

	public String getNothasKey()
	{
		return "ReplaceElementsのgetNothasKeyはなし";
	}

	public String getNothasValue()
	{
		return this.nothasValue;
	}

}
