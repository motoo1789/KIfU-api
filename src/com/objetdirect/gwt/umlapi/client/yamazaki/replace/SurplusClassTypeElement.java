package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

public class SurplusClassTypeElement implements IDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassPartNameArtifact targetClass;
	private UMLArtifact roleRebuild;

	private String surplusKey;
	private String surplusValue;

	private String message;

	private CheckBox checkbox;

	public SurplusClassTypeElement(String surplusKey,String surplusValue,ClassPartNameArtifact targetClass,UMLArtifact roleRebuild)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetClass = targetClass;
		this.roleRebuild = roleRebuild;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname= surplusKey.split(";");
		String classname = splitDiffAttributeClassname[0];
		//String type = splitDiffAttributeClassname[1];
		message = classname + "クラスの" + surplusValue + "を削除しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(targetClass != null)
		{
			targetClass.setStereotype("");
			roleRebuild.rebuildGfxObject();
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
