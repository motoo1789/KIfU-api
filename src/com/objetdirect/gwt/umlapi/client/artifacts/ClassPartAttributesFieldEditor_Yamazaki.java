package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.editors.FieldEditor;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLAttributeName_Yamazaki;

public class ClassPartAttributesFieldEditor_Yamazaki extends FieldEditor{

	UMLAttributeName_Yamazaki attributeToChange_yamazaki;

	public ClassPartAttributesFieldEditor_Yamazaki(final UMLCanvas canvas, final ClassPartAttributesNameArtifact_Yamazaki classPartAttributesArtifact_Yamazaki,
			final UMLAttributeName_Yamazaki attributeToChange)
	{
		super(canvas,classPartAttributesArtifact_Yamazaki);
		this.attributeToChange_yamazaki = attributeToChange;
	}

	@Override
	protected void next() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected boolean updateUMLArtifact(String newContent) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
