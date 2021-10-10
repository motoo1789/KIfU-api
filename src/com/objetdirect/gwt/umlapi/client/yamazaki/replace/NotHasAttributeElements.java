package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNode;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

public class NotHasAttributeElements implements IDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact addTarget;

	private String nothasKey;
	private String nothasValue;

	private String message;

	private CheckBox checkbox;

	public NotHasAttributeElements(String nothasKey,String nothasValue,ClassArtifact component)
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
		String[] splitDiffAttributeClassname= nothasKey.split("!");
		String classname = splitDiffAttributeClassname[0];
		message = classname + "に" + nothasValue + "を追加しますか？";
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(nothasKey.contains("!"))
		{
			char visibility = nothasValue.charAt(0);

			int indexname = nothasValue.indexOf(":");
			String name = nothasValue.substring(1, indexname);
			String type = nothasValue.substring(indexname + 1, nothasValue.length());

			addTarget.addAttribute(new UMLClassAttribute(UMLVisibility.getVisibilityFromToken(visibility), type, name));
			//addTarget.addAttribute(UMLClassAttribute.parseAttribute(nothasValue));
			addTarget.rebuildGfxObject();
			//addTarget.getClassPartAttributesArtifact().rebuildGfxObject();
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
