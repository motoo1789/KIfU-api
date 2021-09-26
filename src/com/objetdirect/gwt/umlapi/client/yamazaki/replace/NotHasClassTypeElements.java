package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

public class NotHasClassTypeElements implements IDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private UMLClass addTarget;

	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;

	public NotHasClassTypeElements(String nothasKey,String nothasValue,UMLClass addTarget)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = addTarget;

		setMessage();

		this.checkbox = new CheckBox(this.message);
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] classElements = nothasKey.split(";");
		String classname = classElements[0];
		message = classname + "クラスに " + nothasValue + "を追加しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		addTarget.setType(nothasValue);
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
