package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNode;

public class ReplaceMethodConstractor implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLNode replaceTarget;
	private UMLArtifact roleRebuild;

	private String surplusKey;
	private String nothasValue;

	private String message;
	private CheckBox checkbox;

	public ReplaceMethodConstractor(String surplusKey,String nothasValue,UMLNode component,UMLArtifact roleRebuild)
	{
		this.surplusKey = surplusKey;
		this.nothasValue = nothasValue;
		this.replaceTarget = component;
		this.roleRebuild = roleRebuild;

		setMessage();

		this.checkbox = new CheckBox(this.message);
	}

	@Override
	public void drawReplaceAddDelete()
	{
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("}"))
		{
			replaceTarget.setType(nothasValue);
			replaceTarget.setStrokeBLACK(replaceTarget.getDiffTypeKey());
			roleRebuild.rebuildGfxObject();
		}
	}

	private void setMessage()
	{
		//  何が入っているか　？？？の？？？をまで
		String classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);

		if(nothasValue.equals(""))
			message =  classnamefmname + "型なしのコンストラクタに変換";
		else
			message = classnamefmname + nothasValue + "に変換";
	}

	@Override
	public CheckBox getCheckBox() {
		// TODO 自動生成されたメソッド・スタブ
		return this.checkbox;
	}

	@Override
	public String getSurplusKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getSurplusValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
