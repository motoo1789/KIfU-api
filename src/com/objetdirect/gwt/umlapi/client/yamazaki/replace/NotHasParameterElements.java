package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;

public class NotHasParameterElements implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLClassMethod addTarget;

	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;

	public NotHasParameterElements(String nothasKey,String nothasValue,UMLClassMethod method)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = method;

		setMessage();

		this.checkbox = new CheckBox(this.message);
	}

	private void setMessage()
	{
		message = nothasKey + "に" + nothasValue + "を追加しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(addTarget != null && nothasKey.contains("%") && nothasKey.contains("&"))
		{
			String[] splitPara = nothasValue.split(":");
			addTarget.addParametar(new UMLParameter(splitPara[1], splitPara[0]));
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
