package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;

public class SurplusParameterElement implements IDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private UMLParameter targetpara;
	private UMLClassMethod targetMethod;

	private String surplusKey;
	private String surplusValue;

	private String message;

	private CheckBox checkbox;

	public SurplusParameterElement(String surplusKey,String surplusValue,UMLClassMethod targetMethod,UMLParameter targetpara)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetpara = targetpara;
		this.targetMethod = targetMethod;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname = surplusKey.split("&");
		String classname = splitDiffAttributeClassname[0];

		String[] splitMethodName = surplusKey.split("%");
		int indexAndMark = splitMethodName[0].indexOf("&") + 1;
		String substringMethodName = splitMethodName[0].substring(indexAndMark,splitMethodName[0].length());
		message = classname + "クラスの" + substringMethodName + "メソッドのパラメータ" + surplusValue + "を削除しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("&"))
		{
			targetMethod.removeParameter(targetpara);
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
