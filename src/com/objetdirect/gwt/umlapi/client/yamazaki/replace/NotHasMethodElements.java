package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;

public class NotHasMethodElements implements IDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact addTarget;

	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;

	public NotHasMethodElements(String nothasKey,String nothasValue,ClassArtifact component)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = component;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffMethodKey = nothasKey.split("&");
		String classname = splitDiffMethodKey[0];
		message = classname + "に" + nothasValue + "を追加しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(nothasKey.contains("&")) 							//　メソッド
		{
			addTarget.addMethod(UMLClassMethod.parseMethod(nothasValue));
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
		return nothasKey;
	}

	@Override
	public String getNothasValue() {
		// TODO 自動生成されたメソッド・スタブ
		return nothasValue;
	}


}
