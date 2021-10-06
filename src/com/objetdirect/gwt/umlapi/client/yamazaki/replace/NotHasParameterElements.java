package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;

public class NotHasParameterElements implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLClassMethod addTarget;
	private UMLArtifact roleRebuild;

	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;

	public NotHasParameterElements(String nothasKey,String nothasValue,UMLClassMethod method,UMLArtifact roleRebuild)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = method;
		this.roleRebuild = roleRebuild;
		setMessage();

		this.checkbox = new CheckBox(this.message);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname = nothasKey.split("&");
		String classname = splitDiffAttributeClassname[0];

		String[] splitMethodName = nothasKey.split("%");
		int indexAndMark = splitMethodName[0].indexOf("&") + 1;
		String substringMethodName = splitMethodName[0].substring(indexAndMark,splitMethodName[0].length());
		message = classname + "クラスの" + substringMethodName + "メソッドにパラメータ" + nothasValue + "を追加しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(addTarget != null && nothasKey.contains("%") && nothasKey.contains("&"))
		{
			String[] splitPara = nothasValue.split(":");
			addTarget.addParametar(new UMLParameter(splitPara[1], splitPara[0]));
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
